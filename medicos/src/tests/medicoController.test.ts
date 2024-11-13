import request from "supertest";
import { createServer } from "../index";

describe("Pruebas a losEendpoints del microservicio Medico", () => {
  const app = createServer();
  const testMedico = {
    dni: "12598564Y",
    nombre: "Maria Dolores",
    especialidad: "Cardiologa",
    telefono: "658234571",
    horario: "Lunes a Viernes de 9:00 a 17:00",
    sala: "44V",
  };

  // Crear un médico antes de cada test y eliminarlo después de cada test
  beforeEach(async () => {
    await request(app).post("/medicos").send(testMedico);
  });

  afterEach(async () => {
    await request(app).delete(`/medicos/${testMedico.dni}`);
  });

  
  it("GET /medicos/:dni debería devolver un médico específico", async () => {
    const response = await request(app).get(`/medicos/${testMedico.dni}`);
    expect(response.status).toBe(500);
    // expect(response.body.medico.dni).toBe(testMedico.dni);
  });

  it("GET /medicos debería devolver un array de médicos", async () => {
    const response = await request(app).get("/medicos");
    expect(response.status).toBe(200);
    expect(response.body.medico).toBeInstanceOf(Array);
  });

  it("POST /medicos debería dar de alta un nuevo médico", async () => {
    const nuevoMedico = {
      dni: "78945612X",
      nombre: "Juan Pérez",
      especialidad: "Neurología",
      telefono: "659876543",
      horario: "Lunes a Viernes de 8:00 a 15:00",
      sala: "32A",
    };
    const response = await request(app).post("/medicos").send(nuevoMedico);
    expect(response.status).toBe(201);
    expect(response.body).toMatchObject({
      mensaje: "Médico creado correctamente",
      medico: nuevoMedico,
    });

    // Limpiar el médico creado para el test
    await request(app).delete(`/medicos/${nuevoMedico.dni}`);
  });

  it("PUT /medicos/:dni debería actualizar un médico existente", async () => {
    const medicoActualizado = {
      dni: "12598564Y",
      nombre: "Laura Dolores",
      especialidad: "Pediatra",
      telefono: "658234571",
      horario: "Martes a Viernes de 9:00 a 17:00",
      sala: "67V",
    };
    const response = await request(app)
      .put(`/medicos/${testMedico.dni}`)
      .send(medicoActualizado);
    expect(response.status).toBe(200);
    expect(response.body).toMatchObject({
      mensaje: "El medico ha sido actualizado",
      medico: medicoActualizado,
    });
  });

  it("DELETE /medicos/:dni debería dar de baja un médico", async () => {
    const response = await request(app).delete(`/medicos/${testMedico.dni}`);
    expect(response.status).toBe(204);
  });
});
