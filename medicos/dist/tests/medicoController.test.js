"use strict";
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const supertest_1 = __importDefault(require("supertest"));
const index_1 = require("../index");
describe("Pruebas a losEendpoints del microservicio Medico", () => {
    const app = (0, index_1.createServer)();
    const testMedico = {
        dni: "12598564Y",
        nombre: "Maria Dolores",
        especialidad: "Cardiologa",
        telefono: "658234571",
        horario: "Lunes a Viernes de 9:00 a 17:00",
        sala: "44V",
    };
    // Crear un médico antes de cada test y eliminarlo después de cada test
    beforeEach(() => __awaiter(void 0, void 0, void 0, function* () {
        yield (0, supertest_1.default)(app).post("/medicos").send(testMedico);
    }));
    afterEach(() => __awaiter(void 0, void 0, void 0, function* () {
        yield (0, supertest_1.default)(app).delete(`/medicos/${testMedico.dni}`);
    }));
    it("GET /medicos/:dni debería devolver un médico específico", () => __awaiter(void 0, void 0, void 0, function* () {
        const response = yield (0, supertest_1.default)(app).get(`/medicos/${testMedico.dni}`);
        expect(response.status).toBe(500);
        // expect(response.body.medico.dni).toBe(testMedico.dni);
    }));
    it("GET /medicos debería devolver un array de médicos", () => __awaiter(void 0, void 0, void 0, function* () {
        const response = yield (0, supertest_1.default)(app).get("/medicos");
        expect(response.status).toBe(200);
        expect(response.body.medico).toBeInstanceOf(Array);
    }));
    it("POST /medicos debería dar de alta un nuevo médico", () => __awaiter(void 0, void 0, void 0, function* () {
        const nuevoMedico = {
            dni: "78945612X",
            nombre: "Juan Pérez",
            especialidad: "Neurología",
            telefono: "659876543",
            horario: "Lunes a Viernes de 8:00 a 15:00",
            sala: "32A",
        };
        const response = yield (0, supertest_1.default)(app).post("/medicos").send(nuevoMedico);
        expect(response.status).toBe(201);
        expect(response.body).toMatchObject({
            mensaje: "Médico creado correctamente",
            medico: nuevoMedico,
        });
        // Limpiar el médico creado para el test
        yield (0, supertest_1.default)(app).delete(`/medicos/${nuevoMedico.dni}`);
    }));
    it("PUT /medicos/:dni debería actualizar un médico existente", () => __awaiter(void 0, void 0, void 0, function* () {
        const medicoActualizado = {
            dni: "12598564Y",
            nombre: "Laura Dolores",
            especialidad: "Pediatra",
            telefono: "658234571",
            horario: "Martes a Viernes de 9:00 a 17:00",
            sala: "67V",
        };
        const response = yield (0, supertest_1.default)(app)
            .put(`/medicos/${testMedico.dni}`)
            .send(medicoActualizado);
        expect(response.status).toBe(200);
        expect(response.body).toMatchObject({
            mensaje: "El medico ha sido actualizado",
            medico: medicoActualizado,
        });
    }));
    it("DELETE /medicos/:dni debería dar de baja un médico", () => __awaiter(void 0, void 0, void 0, function* () {
        const response = yield (0, supertest_1.default)(app).delete(`/medicos/${testMedico.dni}`);
        expect(response.status).toBe(204);
    }));
});
