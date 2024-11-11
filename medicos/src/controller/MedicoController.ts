import { Request, Response } from "express";
import { MedicoService } from "../service/MedicoService";

const medicoService = new MedicoService();

export class MedicoController {
  async obtenerMedicos(req: Request, res: Response): Promise<void> {
    try {
      const medicos = await medicoService.obtenerMedicos();
      res.json({
        mensaje: "Los medicos han sido encontrados",
        medico: medicos,
      });
    } catch (error) {
      res.status(500).send("Ha ocurrido un error");
    }
  }

  async obtenerMedico(req: Request, res: Response): Promise<void> {
    try {
      const medicoDni = req.params.dni;

      if (!medicoDni || medicoDni.trim().length === 0) {
        res.status(400).send("El DNI proporcionado no es válido.");
        return;
      }

      const medico = await medicoService.obtenerMedico(medicoDni);

      if (!medico || medico.dni.trim().length === 0) {
        res.status(404).send("El medico solicitado no existe");
      } else {
        res.json({
          mensaje: "El medico solicitado ha sido encontrado",
          medico: medico,
        });
      }
    } catch (error) {
      res.status(500).send("Ha ocurrido un error");
    }
  }

  async darAltaMedico(req: Request, res: Response): Promise<void> {
    try {
      const nuevoMedico = req.body;
      const medicoCreado = await medicoService.darAltaMedico(nuevoMedico);
      if (!medicoCreado) {
        res.status(400).send("El medico no ha podido darse de alta");
      }
      res.status(201).json({
        mensaje: "Médico creado correctamente",
        medico: medicoCreado,
      });
    } catch (error) {
      res.status(500).send("Ha courrido un error");
    }
  }

  async actualizarMedico(req: Request, res: Response): Promise<void> {
    try {
      const medicoDni = req.params.dni;
      if (!medicoDni) {
        res.status(400).send("El medico no esta dado alta");
      }
      const medicoActualizado = req.body;
      const medico = await medicoService.actualizarMedico(
        medicoDni,
        medicoActualizado
      );
      if (!medico) {
        res.status(400).send("El medico no ha podido ser actualizado");
      } else {
        res.json({
          mensaje: "El medico ha sido actualizado",
          medico: medico,
        });
      }
    } catch (error) {
      res.status(500).send("Ha ocurrido un error");
    }
  }

  async darBajaMedico(req: Request, res: Response): Promise<void> {
    try {
      const medicoDni = req.params.dni;
      await medicoService.darBajaMedico(medicoDni);
      if (!medicoDni) {
        res.status(404).send("El medico no esta dado de alta");
      }
      res.status(200).send("El medico ha sido dado de baja");
    } catch (error) {
      res.status(500).send("Ha ocurrido un error");
    }
  }
}
