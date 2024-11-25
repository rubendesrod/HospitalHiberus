# Usar una imagen base oficial de Node.js
FROM node:16-alpine

# Instalar Git (necesario para dependencias git en npm)
RUN apk add --no-cache git

# Establecer el directorio de trabajo en el contenedor
WORKDIR /app

# Copiar el archivo de dependencias (package.json y package-lock.json)
COPY package*.json ./

# Instalar las dependencias
RUN npm install

# Copiar el resto del código de la aplicación
COPY . .

# Instalar las dependencias de TypeScript globalmente (para compilar)
RUN npm install -g typescript

# Compilar el código TypeScript a JavaScript
RUN npm run build

# Exponer el puerto en el que corre la aplicación
EXPOSE 8082

# Comando para ejecutar la aplicación
CMD ["npm", "start"]

