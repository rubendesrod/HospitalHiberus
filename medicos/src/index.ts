import exppress from 'express';
import bodyParser from 'body-parser';

const app = exppress();
const PORT = 3000;

app.use(bodyParser.json());

app.get("/medicos", (req, res) => {
    res.send("API Rest de medicos")
})

app.listen(PORT, () => {
    console.log(`Servidor en el puerto ${PORT}. Para acceder ir a http:/localhost:${PORT}`);
})