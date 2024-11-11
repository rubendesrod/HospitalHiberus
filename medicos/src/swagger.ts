import paths from "path";
import { SwaggerDefinition } from "swagger-jsdoc";

const swaggerDefinition: SwaggerDefinition = {
  openapi: "3.0.0",
  info: {
    title: "API de Medicos",
    description: "API Rest para manejar los medicos del hospital",
    version: "1.0.0",
  },
  servers: [
    {
      url: "http://localhost:3000",
      description: "Servidor local",
    },
  ],
  paths: {
    "/medicos": {
      get: {
        tags: ["Medicos"],
        summary: "Obtener todos los medicos",
        responses: {
          "200": {
            description: "Los medicos han sido encontrados",
            content: {
              "application/json": {
                schema: {
                  type: "array",
                  items: {
                    type: "object",
                    properties: {
                      dni: {
                        type: "string",
                      },
                      nombre: {
                        type: "string",
                      },
                      especialidad: {
                        type: "string",
                      },
                      telefono: {
                        type: "string",
                      },
                      horario: {
                        type: "string",
                      },
                      sala: {
                        type: "string",
                      },
                    },
                  },
                },
              },
            },
          },
          "500": {
            description: "Error interno del servidor",
          },
        },
      },
      post: {
        tags: ["Medicos"],
        summary: "Crear un medico",
        requestBody: {
          content: {
            "application/json": {
              schema: {
                type: "object",
                properties: {
                  dni: {
                    type: "string",
                  },
                  nombre: {
                    type: "string",
                  },
                  especialidad: {
                    type: "string",
                  },
                  telefono: {
                    type: "string",
                  },
                  horario: {
                    type: "string",
                  },
                  sala: {
                    type: "string",
                  },
                },
              },
            },
          },
        },
        responses: {
          "201": {
            description: "Médico creado correctamente",
            content: {
              "application/json": {
                mensaje: "Médico creado correctamente",
                type: "object",
                properties: {
                  dni: {
                    type: "string",
                  },
                  nombre: {
                    type: "string",
                  },
                  especialidad: {
                    type: "string",
                  },
                  telefono: {
                    type: "string",
                  },
                  horario: {
                    type: "string",
                  },
                  sala: {
                    type: "string",
                  },
                },
              },
            },
          },
          "400": {
            description: "El medico no ha podido darse de alta",
          },
          "500": {
            description: "Error interno del servidor",
          },
        },
      },
    },
    "/medicos/{dni}": {
      get: {
        tags: ["Medicos"],
        summary: "Obtener medico por su Dni",
        parameters: [
          {
            name: "dni",
            in: "path",
            description: "Dni del medico",
            required: true,
            schema: {
              type: "string",
            },
          },
        ],
        responses: {
          "200": {
            description: "El medico ha sido encontrado",
            content: {
              "application/json": {
                mensaje: "El medico ha sido actualizado",
                type: "object",
                properties: {
                  dni: {
                    type: "string",
                  },
                  nombre: {
                    type: "string",
                  },
                  especialidad: {
                    type: "string",
                  },
                  telefono: {
                    type: "string",
                  },
                  horario: {
                    type: "string",
                  },
                  sala: {
                    type: "string",
                  },
                },
              },
            },
          },
          "400": {
            description: "El dni proporcionado no es válido",
          },
          "404": {
            description: "El medico solicitado no existe",
          },
          "500": {
            description: "Error interno del servidor",
          },
        },
      },
      put: {
        tags: ["Medicos"],
        summary: "Actualizar medico por su Dni",
        parameters: [
          {
            name: "dni",
            in: "path",
            description: "Dni del medico",
            required: true,
            schema: {
              type: "string",
            },
          },
        ],
        responses: {
          "200": {
            description: "El medico ha sido actualizado",
            content: {
              "application/json": {
                mensaje: "El medico ha sido actualizado",
                type: "object",
                properties: {
                  dni: {
                    type: "string",
                  },
                  nombre: {
                    type: "string",
                  },
                  especialidad: {
                    type: "string",
                  },
                  telefono: {
                    type: "string",
                  },
                  horario: {
                    type: "string",
                  },
                  sala: {
                    type: "string",
                  },
                },
              },
            },
          },
          "400": {
            description: "El medico no ha podido ser actualizado",
          },
          "404": {
            description: "El medico no esta dado alta",
          },
          "500": {
            description: "Error interno del servidor",
          },
        },
      },
      delete: {
        tags: ["Medicos"],
        summary: "Dar de baja un medico por su Dni",
        parameters: [
          {
            name: "dni",
            in: "path",
            description: "DNI del medico",
            required: true,
            schema: {
              type: "string",
            },
          },
        ],
        responses: {
          "200": {
            description: "El medico ha sido dado de baja",
          },
          "404": {
            description: "El medico no esta dado de alta",
          },
          "500": {
            description: "Error interno del servidor",
          },
        },
      },
    },
  },
};

export default swaggerDefinition;
