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
Object.defineProperty(exports, "__esModule", { value: true });
exports.MedicoService = void 0;
const promise_1 = require("mysql2/promise");
class MedicoService {
    constructor() {
        // inicializa la conexión al ser creado
        this.inicializarConexion();
    }
    // Función para inicializar la conexion a la base de datos al instanciarla
    inicializarConexion() {
        return __awaiter(this, void 0, void 0, function* () {
            this.conexion = yield (0, promise_1.createConnection)({
                host: "localhost",
                port: 3307,
                user: "medicos_user",
                password: "medicos_password",
                database: "medicos_db",
            });
        });
    }
    // Funcion para obtener todos los medicos
    obtenerMedicos() {
        return __awaiter(this, void 0, void 0, function* () {
            const [filas] = yield this.conexion.execute("SELECT * FROM medicos");
            return filas;
        });
    }
    // Funcion para obtener un medico mediante su id (dni)
    obtenerMedico(medicoDni) {
        return __awaiter(this, void 0, void 0, function* () {
            const [resultado] = yield this.conexion.execute("SELECT * FROM medicos WHERE dni = ?", [medicoDni]);
            if (resultado.length === 0) {
                return null;
            }
            return resultado[0];
        });
    }
    // funcion para dar de alta a un médico
    darAltaMedico(medico) {
        return __awaiter(this, void 0, void 0, function* () {
            const [resultado] = yield this.conexion.execute("INSERT INTO medicos (dni, nombre, especialidad, telefono, horario, sala) VALUES (?,?,?,?,?,?)", [
                medico.dni,
                medico.nombre,
                medico.especialidad,
                medico.telefono,
                medico.horario,
                medico.sala,
            ]);
            return medico;
        });
    }
    // funcion para actualizar algún dato de un médico o todos
    actualizarMedico(medicoDni, medico) {
        return __awaiter(this, void 0, void 0, function* () {
            const [resultado] = yield this.conexion.execute("UPDATE medicos SET nombre = ?, especialidad = ?, telefono = ?, horario = ?, sala = ? WHERE dni = ?", [
                medico.nombre,
                medico.especialidad,
                medico.telefono,
                medico.horario,
                medico.sala,
                medicoDni,
            ]);
            if (resultado.affectedRows === 0) {
                return null;
            }
            return medico;
        });
    }
    // funcion para eliminar un médico
    darBajaMedico(medicoDni) {
        return __awaiter(this, void 0, void 0, function* () {
            yield this.conexion.execute("DELETE FROM medicos WHERE dni = ?", [medicoDni]);
        });
    }
}
exports.MedicoService = MedicoService;
