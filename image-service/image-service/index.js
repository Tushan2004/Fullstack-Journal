const express = require('express');
const multer = require('multer');
const { createCanvas, loadImage } = require('canvas');
const fs = require('fs');
const path = require('path');
const cors = require('cors');

const app = express();

// CORS för din frontend
app.use(cors({
    origin: 'http://localhost:3001',  // eller app.use(cors()) i dev
}));

app.use(express.json()); // För att kunna läsa JSON-data

// Skapa uploads-mapp om den inte finns
const uploadDir = path.join(__dirname, 'uploads');
if (!fs.existsSync(uploadDir)) {
    fs.mkdirSync(uploadDir);
}

// Konfigurera var bilder ska sparas
const storage = multer.diskStorage({
    destination: (req, file, cb) => {
        cb(null, uploadDir);
    },
    filename: (req, file, cb) => {
        const uniqueSuffix = Date.now() + '-' + Math.round(Math.random() * 1E9);
        cb(null, file.fieldname + '-' + uniqueSuffix + path.extname(file.originalname));
    }
});
const upload = multer({ storage });

// 1. Ladda upp bild
app.post('/images', upload.single('image'), (req, res) => {
    if (!req.file) return res.status(400).send('Ingen bild skickad');

    res.json({
        message: 'Bild uppladdad',
        id: req.file.filename,
        url: `/images/${req.file.filename}`,    // frontend: `http://localhost:3000` + url
    });
});

// 2. Hämta bild
app.get('/images/:id', (req, res) => {
    const filePath = path.join(uploadDir, req.params.id);
    if (fs.existsSync(filePath)) {
        res.sendFile(filePath);
    } else {
        res.status(404).send('Bild saknas');
    }
});

// 3. Redigera bild (rita text)
app.post('/images/:id/edit', async (req, res) => {
    const filePath = path.join(uploadDir, req.params.id);
    if (!fs.existsSync(filePath)) return res.status(404).send('Bild saknas');

    try {
        const image = await loadImage(filePath);
        const canvas = createCanvas(image.width, image.height);
        const ctx = canvas.getContext('2d');

        // Rita originalbild
        ctx.drawImage(image, 0, 0);

        const { text, x, y } = req.body;

        if (text) {
            ctx.font = 'bold 30px Arial';
            ctx.fillStyle = 'red';
            ctx.fillText(text, x || 50, y || 50);
        }

        const buffer = canvas.toBuffer('image/jpeg');
        fs.writeFileSync(filePath, buffer);

        res.json({
            message: 'Bild uppdaterad',
            url: `/images/${req.params.id}`,
        });

    } catch (e) {
        console.error(e);
        res.status(500).send('Fel vid redigering');
    }
});

app.listen(3000, () => {
    console.log('Image Service körs på port 3000 🚀');
});