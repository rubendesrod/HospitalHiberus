import exppress from "express";
import bodyParser from "body-parser";
import { MedicoController } from "./controller/MedicoController";
import swaggerUi from "swagger-ui-express";
import swaggerJSDoc from "swagger-jsdoc";
import swaggerDefinition from "./swagger";

// Configuración para que swagger use la configuracion de la API definition
const swaggerOptions = {
  swaggerDefinition,
  apis: ["./src/*.ts"],
};

export const createServer = () => {
  const app = exppress();

  const medicoController = new MedicoController();

  const swaggerSpec = swaggerJSDoc(swaggerOptions);
  app.use("/docs", swaggerUi.serve, swaggerUi.setup(swaggerSpec));

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
  return app;
}


if (require.main === module) {
  const PORT = 8082;
  const app = createServer();
  app.listen(PORT, () => {
    console.log(`Servidor en el puerto ${PORT}, URL SWAGGER: http://localhost:${PORT}/docs/`);
  });
}

