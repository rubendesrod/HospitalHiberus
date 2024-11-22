import { createConnection, Connection, ResultSetHeader } from "mysql2/promise";
import { Medico } from "../interface/MedicoInterface";

export class MedicoService {
  private conexion!: Connection;

  constructor() {
    // inicializa la conexión al ser creado
    this.inicializarConexion();
  }

  // Función para inicializar la conexion a la base de datos al instanciarla
  async inicializarConexion(): Promise<void> {
    this.conexion = await createConnection({
      host: "db_medicos",
      port: 3306,
      user: "medicos_user",
      password: "medicos_password",
      database: "medicos_db",
    });
  }

  // Funcion para obtener todos los medicos
  async obtenerMedicos(): Promise<Medico[]> {
    const [filas] = await this.conexion.execute("SELECT * FROM medicos");
    return filas as Medico[];
  }

  // Funcion para obtener un medico mediante su id (dni)
  async obtenerMedico(medicoDni: string): Promise<Medico | null> {
    const [resultado] = await this.conexion.execute<any>(
      "SELECT * FROM medicos WHERE dni = ?",
      [medicoDni]
    );
    if (resultado.length === 0) {
      return null;
    }
    return resultado[0] as Medico;
  }

  // funcion para dar de alta a un médico
  async darAltaMedico(medico: Medico): Promise<Medico> {
    const [resultado] = await this.conexion.execute<ResultSetHeader>(
      "INSERT INTO medicos (dni, nombre, especialidad, telefono, horario, sala) VALUES (?,?,?,?,?,?)",
      [
        medico.dni,
        medico.nombre,
        medico.especialidad,
        medico.telefono,
        medico.horario,
        medico.sala,
      ]
    );
    return medico;
  }

  // funcion para actualizar algún dato de un médico o todos
  async actualizarMedico(
    medicoDni: string,
    medico: Medico
  ): Promise<Medico | null> {
    const [resultado] = await this.conexion.execute<ResultSetHeader>(
      "UPDATE medicos SET nombre = ?, especialidad = ?, telefono = ?, horario = ?, sala = ? WHERE dni = ?",
      [
        medico.nombre,
        medico.especialidad,
        medico.telefono,
        medico.horario,
        medico.sala,
        medicoDni,
      ]
    );
    if(resultado.affectedRows === 0){
        return null;
    }
    return medico;
  }

  // funcion para eliminar un médico
  async darBajaMedico(medicoDni: string): Promise<void> {
    await this.conexion.execute<ResultSetHeader>("DELETE FROM medicos WHERE dni = ?", [medicoDni])
  }

}
