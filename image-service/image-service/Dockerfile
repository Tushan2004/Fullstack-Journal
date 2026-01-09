# Vi börjar med en lätt version av Linux med Node.js installerat
FROM node:18-alpine

# Installera systemverktyg som 'canvas' behöver (viktigt!)
# Detta löser problem om canvas krånglar på din Windows-dator
RUN apk add --no-cache \
    build-base \
    g++ \
    cairo-dev \
    jpeg-dev \
    pango-dev \
    giflib-dev

# Sätt arbetskatalog i containern
WORKDIR /app

# Kopiera först package-filerna (för att cacha installationen)
COPY package*.json ./

# Installera biblioteken INUTI containern
RUN npm install

# Kopiera resten av din kod (index.js)
COPY . .

# Skapa mappen uploads inuti containern
RUN mkdir -p uploads

# Öppna port 3000
EXPOSE 3000

# Kommandot som startar din app
CMD ["node", "index.js"]