import exppress from "express";
import bodyParser from "body-parser";
import { MedicoController } from "./controller/MedicoController";

const app = exppress();
const PORT = 3000;
const medicoController = new MedicoController();

app.use(bodyParser.json());

app.get("/", (req, res) => {
  res.send("API Rest de medicos");
});

app.get("/medicos", medicoController.obtenerMedicos.bind(medicoController));
app.get("/medicos/:dni", medicoController.obtenerMedico.bind(medicoController));
app.post("/medicos", medicoController.darAltaMedico.bind(medicoController));
app.put(
  "/medicos/:dni",
  medicoController.actualizarMedico.bind(medicoController)
);
app.delete(
  "/medicos/:dni",
  medicoController.darBajaMedico.bind(medicoController)
);

app.listen(PORT, () => {
  console.log(
    `Servidor en el puerto ${PORT}. Para acceder ir a http:/localhost:${PORT}/medicos`
  );
});
