import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";

export default defineConfig({
    base: "/deploy-17874566-de03-424c-a14b-2fbac442e28c/frontend-barua/",
    plugins: [react()],
    build: {
        outDir: "dist",
    },
});