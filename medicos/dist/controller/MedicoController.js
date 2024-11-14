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
exports.MedicoController = void 0;
const MedicoService_1 = require("../service/MedicoService");
const medicoService = new MedicoService_1.MedicoService();
class MedicoController {
    obtenerMedicos(req, res) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                const medicos = yield medicoService.obtenerMedicos();
                res.json({
                    mensaje: "Los medicos han sido encontrados",
                    medico: medicos,
                });
            }
            catch (error) {
                res.status(500).send("Ha ocurrido un error");
            }
        });
    }
    obtenerMedico(req, res) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                const medicoDni = req.params.dni;
                if (!medicoDni || medicoDni.trim().length === 0) {
                    res.status(400).send("El Dni proporcionado no es válido.");
                    return;
                }
                const medico = yield medicoService.obtenerMedico(medicoDni);
                if (!medico || medico.dni.trim().length === 0) {
                    res.status(404).send("El medico solicitado no existe");
                }
                else {
                    res.json({
                        mensaje: "El medico solicitado ha sido encontrado",
                        medico: medico,
                    });
                }
            }
            catch (error) {
                res.status(500).send("Ha ocurrido un error");
            }
        });
    }
    darAltaMedico(req, res) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                const nuevoMedico = req.body;
                const medicoCreado = yield medicoService.darAltaMedico(nuevoMedico);
                if (!medicoCreado) {
                    res.status(400).send("El medico no ha podido darse de alta");
                    return;
                }
                res.status(201).json({
                    mensaje: "Médico creado correctamente",
                    medico: medicoCreado,
                });
            }
            catch (error) {
                res.status(500).send("Ha courrido un error");
            }
        });
    }
    actualizarMedico(req, res) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                const medicoDni = req.params.dni;
                if (!medicoDni) {
                    res.status(404).send("El medico no esta dado alta");
                }
                const medicoActualizado = req.body;
                const medico = yield medicoService.actualizarMedico(medicoDni, medicoActualizado);
                if (!medico) {
                    res.status(400).send("El medico no ha podido ser actualizado");
                }
                else {
                    res.json({
                        mensaje: "El medico ha sido actualizado",
                        medico: medico,
                    });
                }
            }
            catch (error) {
                res.status(500).send("Ha ocurrido un error");
            }
        });
    }
    darBajaMedico(req, res) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                const medicoDni = req.params.dni;
                yield medicoService.darBajaMedico(medicoDni);
                if (!medicoDni) {
                    res.status(404).send("El medico no esta dado de alta");
                }
                res.status(204).send("El medico ha sido dado de baja");
            }
            catch (error) {
                res.status(500).send("Ha ocurrido un error");
            }
        });
    }
}
exports.MedicoController = MedicoController;
