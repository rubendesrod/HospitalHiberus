# Usa la imagen de Node.js como base
FROM node:18

# Establece el directorio de trabajo en el contenedor
WORKDIR /app

# Copia los archivos de configuración a la imagen
COPY package*.json ./
COPY tsconfig.json ./

# Instala las dependencias
RUN npm install

# Copia el resto del código fuente de la aplicación
COPY . .

# Compila el código TypeScript a JavaScript
RUN npm run build

# Expone el puerto en el que corre la aplicación (ajusta si es necesario)
EXPOSE 8082

# Comando para correr la aplicación
CMD ["npm", "start"]
