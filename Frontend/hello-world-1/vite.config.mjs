import {defineConfig} from "vite";

export default defineConfig({
    base: "/deploy-17874566-de03-424c-a14b-2fbac442e28c/frontend-barua/",
    plugins: [react()],
    server: {
        port: 3001,
    },
    build: {
        outDir: 'dist',
    },
});