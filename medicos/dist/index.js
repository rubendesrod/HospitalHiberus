"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.createServer = void 0;
const express_1 = __importDefault(require("express"));
const body_parser_1 = __importDefault(require("body-parser"));
const MedicoController_1 = require("./controller/MedicoController");
const swagger_ui_express_1 = __importDefault(require("swagger-ui-express"));
const swagger_jsdoc_1 = __importDefault(require("swagger-jsdoc"));
const swagger_1 = __importDefault(require("./swagger"));
const eureka_client_1 = __importDefault(require("./eureka-client"));
// Configuración para que swagger use la configuracion de la API definition
const swaggerOptions = {
    swaggerDefinition: swagger_1.default,
    apis: ["./src/*.ts"],
};
const createServer = () => {
    const app = (0, express_1.default)();
    const medicoController = new MedicoController_1.MedicoController();
    const swaggerSpec = (0, swagger_jsdoc_1.default)(swaggerOptions);
    app.use("/docs", swagger_ui_express_1.default.serve, swagger_ui_express_1.default.setup(swaggerSpec));
    app.use(body_parser_1.default.json());
    // Endpoint para cuando se conecte a eureka server
    app.get("/info", (req, res) => {
        res.json({ status: "microservicio de medicos esta activo" });
    });
    app.get("/", (req, res) => {
        res.send("API Rest de medicos");
    });
    app.get("/medicos", medicoController.obtenerMedicos.bind(medicoController));
    app.get("/medicos/:dni", medicoController.obtenerMedico.bind(medicoController));
    app.post("/medicos", medicoController.darAltaMedico.bind(medicoController));
    app.put("/medicos/:dni", medicoController.actualizarMedico.bind(medicoController));
    app.delete("/medicos/:dni", medicoController.darBajaMedico.bind(medicoController));
    return app;
};
exports.createServer = createServer;
if (require.main === module) {
    const PORT = 8082;
    const app = (0, exports.createServer)();
    app.listen(PORT, () => {
        console.log(`Servidor en el puerto ${PORT}, URL SWAGGER: http://localhost:${PORT}/docs/`);
        // inicie el servicio de registro en eureka server
        eureka_client_1.default.start();
        // Evento que captura el proceso de apagado para dersgistrar el proceso en eureka antes de que se cierre
        process.on("SIGINT", () => {
            eureka_client_1.default.stop();
        });
    });
}
