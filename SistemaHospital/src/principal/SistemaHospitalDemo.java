package principal;

import modelo.abstractas.Empleado;
import modelo.enums.EstadoCita;
import modelo.enums.Turno;
import modelo.hospital.CitaMedica;
import modelo.hospital.Diagnostico;
import modelo.hospital.Especialidad;
import modelo.hospital.Hospital;
import modelo.personas.Cirujano;
import modelo.personas.Enfermero;
import modelo.personas.Medico;
import modelo.personas.Paciente;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class SistemaHospitalDemo {
    private static Hospital hospital;
    private static Scanner scanner;
    private static List<Especialidad> especialidadesGlobales = new ArrayList<>();

    public static void main(String[] args) {
        hospital = new Hospital("Hospital Comfenalco", "Av. Principal 123");
        scanner = new Scanner(System.in);

        System.out.println("Iniciando sistema con base de datos en blanco...");

        boolean salir = false;
        while (!salir) {
            System.out.println("\n==================================");
            System.out.println(" SISTEMA DE GESTION HOSPITALARIA");
            System.out.println("==================================");
            System.out.println("Seleccione su rol/menu:");
            System.out.println("1. Menu Recepcionista");
            System.out.println("2. Menu Administrador");
            System.out.println("3. Menu Medico y Cirujano");
            System.out.println("4. Menu Enfermero");
            System.out.println("5. Menu Paciente");
            System.out.println("0. Salir del Sistema");

            String opcion = leerCadenaNoVacia("Opcion: ");

            try {
                switch (opcion) {
                    case "1":
                        menuRecepcionista();
                        break;
                    case "2":
                        menuAdministrador();
                        break;
                    case "3":
                        menuMedicoCirujano();
                        break;
                    case "4":
                        menuEnfermero();
                        break;
                    case "5":
                        menuPaciente();
                        break;
                    case "0":
                        salir = true;
                        System.out.println("Saliendo del sistema...");
                        break;
                    default:
                        System.out.println("Opcion no valida.");
                }
            } catch (Exception e) {
                System.out.println(" Error inesperado: " + e.getMessage());
            }
        }

        scanner.close();
    }

    // MENU RECEPCIONISTA
    private static void menuRecepcionista() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- MENU RECEPCIONISTA ---");
            System.out.println("1. Registrar Paciente");
            System.out.println("2. Registrar Alergia a Paciente");
            System.out.println("3. Agendar Cita Medica");
            System.out.println("4. Cancelar Cita Pendiente");
            System.out.println("0. Volver al menu principal");
            String opt = leerCadenaNoVacia("Opcion: ");
            try {
                switch (opt) {
                    case "1":
                        registrarPaciente();
                        break;
                    case "2":
                        registrarAlergia();
                        break;
                    case "3":
                        agendarCita();
                        break;
                    case "4":
                        cancelarCita();
                        break;
                    case "0":
                        volver = true;
                        break;
                    default:
                        System.out.println("Opcion invalida.");
                }
            } catch (Exception e) {
                System.out.println(" Error: " + e.getMessage());
            }
        }
    }

    private static void registrarPaciente() {
        System.out.println("\n[Registrar Paciente]");
        String id = leerCadenaNoVacia("Documento (ID): ");
        if (buscarPaciente(id) != null) {
            System.out.println(" Error: Ya existe un paciente con este documento.");
            return;
        }
        String nombre = leerCadenaNoVacia("Nombre: ");
        String apellido = leerCadenaNoVacia("Apellido: ");
        LocalDate fecha = leerFechaSegura("Fecha Nacimiento (YYYY-MM-DD): ");
        String email = leerCadenaNoVacia("Email: ");
        String hcId = leerCadenaNoVacia("ID Historia Clinica: ");
        String grupo = leerCadenaNoVacia("Grupo Sanguineo: ");

        Paciente paciente = new Paciente(id, nombre, apellido, fecha, email, hcId, grupo);
        hospital.registrarPaciente(paciente);
        System.out.println(" Paciente registrado exitosamente.");
    }

    private static void registrarAlergia() {
        System.out.println("\n[Registrar Alergia]");
        String id = leerCadenaNoVacia("ID del Paciente: ");
        Paciente paciente = buscarPaciente(id);
        if (paciente == null) {
            System.out.println(" Paciente no encontrado.");
            return;
        }

        String alergia = leerCadenaNoVacia("Nombre de la alergia: ");
        paciente.agregarAlergia(alergia);
        System.out.println(" Alergia registrada.");
    }

    private static void agendarCita() {
        System.out.println("\n[Agendar Cita Medica]");
        String idPac = leerCadenaNoVacia("ID del Paciente: ");
        Paciente paciente = buscarPaciente(idPac);
        if (paciente == null) {
            System.out.println(" Paciente no encontrado.");
            return;
        }

        String idMed = leerCadenaNoVacia("ID del Medico: ");
        Medico medico = buscarMedico(idMed);
        if (medico == null) {
            System.out.println(" Medico no encontrado.");
            return;
        }

        LocalDateTime fechaHora = leerFechaHoraSegura("Fecha y hora (YYYY-MM-DD HH:MM): ");
        String motivo = leerCadenaNoVacia("Motivo: ");

        String citaId = "C-" + UUID.randomUUID().toString().substring(0, 5).toUpperCase();
        CitaMedica cita = new CitaMedica(citaId, paciente, medico, fechaHora, motivo);
        hospital.agendarCita(cita);
        System.out.println(" Cita agendada exitosamente con costo de: $" + cita.calcularCosto());
    }

    private static void cancelarCita() {
        System.out.println("\n[Cancelar Cita]");
        String idCita = leerCadenaNoVacia("ID de la cita: ");
        CitaMedica cita = buscarCita(idCita);
        if (cita == null) {
            System.out.println(" Cita no encontrada.");
            return;
        }

        if (cita.getEstado() == EstadoCita.PENDIENTE || cita.getEstado() == EstadoCita.CONFIRMADA) {
            cita.cancelar();
            System.out.println(" Cita cancelada.");
        } else {
            System.out.println(" No se puede cancelar en estado: " + cita.getEstado());
        }
    }

    // MENU ADMINISTRADOR
    private static void menuAdministrador() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- MENU ADMINISTRADOR ---");
            System.out.println("1. Contratar Empleado");
            System.out.println("2. Calcular Nomina Total");
            System.out.println("3. Registrar Especialidad");
            System.out.println("4. Asignar Turno a Enfermero");
            System.out.println("0. Volver al menu principal");
            String opt = leerCadenaNoVacia("Opcion: ");
            try {
                switch (opt) {
                    case "1":
                        contratarEmpleado();
                        break;
                    case "2":
                        calcularNomina();
                        break;
                    case "3":
                        registrarEspecialidad();
                        break;
                    case "4":
                        asignarTurnoEnfermero();
                        break;
                    case "0":
                        volver = true;
                        break;
                    default:
                        System.out.println("Opcion invalida.");
                }
            } catch (Exception e) {
                System.out.println(" Error: " + e.getMessage());
            }
        }
    }

    private static void contratarEmpleado() {
        System.out.println("\n[Contratar Empleado]");
        String tipo = leerCadenaNoVacia("Tipo (1=Medico, 2=Cirujano, 3=Enfermero): ");
        if (!tipo.equals("1") && !tipo.equals("2") && !tipo.equals("3")) {
            System.out.println(" Tipo invalido.");
            return;
        }

        String id = leerCadenaNoVacia("ID/Cedula: ");
        for (Empleado e : hospital.getEmpleados()) {
            if (e.getId().equals(id)) {
                System.out.println(" Error: Ya existe un empleado con este ID.");
                return;
            }
        }
        String nombre = leerCadenaNoVacia("Nombre: ");
        String apellido = leerCadenaNoVacia("Apellido: ");
        LocalDate fechaNac = leerFechaSegura("Fecha Nacimiento (YYYY-MM-DD): ");
        String email = leerCadenaNoVacia("Email: ");
        String legajo = leerCadenaNoVacia("Legajo: ");
        double salario = leerDoubleSeguro("Salario Base: ");

        if (tipo.equals("1") || tipo.equals("2")) {
            String licencia = leerCadenaNoVacia("Numero de Licencia: ");
            String codEsp = leerCadenaNoVacia("Codigo Especialidad (ej. ESP-01): ");
            Especialidad esp = buscarEspecialidad(codEsp);
            if (esp == null) {
                System.out.println(" Especialidad no existe.");
                return;
            }

            if (tipo.equals("1")) {
                hospital.contratarEmpleado(new Medico(id, nombre, apellido, fechaNac, email, legajo, LocalDate.now(),
                        salario, true, licencia, esp));
                System.out.println(" Medico contratado.");
            } else {
                String emerg = leerCadenaNoVacia("Disponible emergencias? (S/N): ");
                boolean disponible = emerg.equalsIgnoreCase("S");
                hospital.contratarEmpleado(new Cirujano(id, nombre, apellido, fechaNac, email, legajo, LocalDate.now(),
                        salario, true, licencia, esp, disponible));
                System.out.println(" Cirujano contratado.");
            }
        } else if (tipo.equals("3")) {
            String t = leerCadenaNoVacia("Turno (1=MANANA, 2=TARDE, 3=NOCHE): ");
            if (!t.equals("1") && !t.equals("2") && !t.equals("3")) {
                System.out.println(" Turno invalido.");
                return;
            }
            Turno turno = t.equals("1") ? Turno.MANANA : (t.equals("2") ? Turno.TARDE : Turno.NOCHE);
            String area = leerCadenaNoVacia("Area Asignada: ");
            hospital.contratarEmpleado(new Enfermero(id, nombre, apellido, fechaNac, email, legajo, LocalDate.now(),
                    salario, true, turno, area));
            System.out.println(" Enfermero contratado.");
        }
    }

    private static void calcularNomina() {
        System.out.println("\n[Calcular Nomina Total]");
        double nomina = hospital.calcularNominaTotal();
        System.out.println("La nomina total del hospital es: $" + nomina);
    }

    private static void registrarEspecialidad() {
        System.out.println("\n[Registrar Especialidad]");
        String codigo = leerCadenaNoVacia("Codigo: ");
        if (buscarEspecialidad(codigo) != null) {
            System.out.println(" Error: Ya existe una especialidad con este codigo.");
            return;
        }
        String nombre = leerCadenaNoVacia("Nombre: ");
        String desc = leerCadenaNoVacia("Descripcion: ");
        double costo = leerDoubleSeguro("Costo de Consulta: ");

        Especialidad esp = new Especialidad(codigo, nombre, desc, costo);
        especialidadesGlobales.add(esp);
        System.out.println(" Especialidad registrada exitosamente.");
    }

    private static void asignarTurnoEnfermero() {
        System.out.println("\n[Asignar Turno a Enfermero]");
        String id = leerCadenaNoVacia("ID del Enfermero: ");
        Enfermero enf = null;
        for (Empleado e : hospital.getEmpleados()) {
            if (e instanceof Enfermero && e.getId().equals(id)) {
                enf = (Enfermero) e;
                break;
            }
        }
        if (enf == null) {
            System.out.println(" Enfermero no encontrado.");
            return;
        }

        String t = leerCadenaNoVacia("Nuevo Turno (1=MANANA, 2=TARDE, 3=NOCHE): ");
        if (!t.equals("1") && !t.equals("2") && !t.equals("3")) {
            System.out.println(" Turno invalido.");
            return;
        }
        Turno turno = t.equals("1") ? Turno.MANANA : (t.equals("2") ? Turno.TARDE : Turno.NOCHE);
        enf.setTurno(turno);
        System.out.println(" Turno actualizado.");
    }

    // MENU MEDICO / CIRUJANO
    private static void menuMedicoCirujano() {
        String id = leerCadenaNoVacia("\nIngrese su ID de Medico/Cirujano: ");
        Medico medico = buscarMedico(id);
        if (medico == null) {
            System.out.println(" Acceso denegado. Medico no encontrado.");
            return;
        }

        boolean esCirujano = medico instanceof Cirujano;

        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- MENU MEDICO (" + medico.getNombre() + ") ---");
            System.out.println("1. Registrar Diagnostico/Receta de una Cita");
            System.out.println("2. Consultar Historial Clinico de Paciente");
            System.out.println("3. Calcular Costo de Consulta");
            if (esCirujano) {
                System.out.println("4. Registrar Cirugia Realizada");
                System.out.println("5. Cambiar Disponibilidad Emergencias");
            }
            System.out.println("0. Volver");
            String opt = leerCadenaNoVacia("Opcion: ");
            try {
                switch (opt) {
                    case "1":
                        registrarDiagnostico(medico);
                        break;
                    case "2":
                        consultarHistorial(medico);
                        break;
                    case "3":
                        System.out.println("Costo de especialidad: $" + medico.getEspecialidad().getCostoConsulta());
                        break;
                    case "4":
                        if (esCirujano) {
                            ((Cirujano) medico).realizarCirugia();
                            System.out.println("Cirugia registrada (+1).");
                        }
                        break;
                    case "5":
                        if (esCirujano) {
                            String resp = leerCadenaNoVacia("Disponible? (S/N): ");
                            ((Cirujano) medico).setDisponibleEmergencias(resp.equalsIgnoreCase("S"));
                            System.out.println("Disponibilidad actualizada.");
                        }
                        break;
                    case "0":
                        volver = true;
                        break;
                    default:
                        System.out.println("Opcion invalida.");
                }
            } catch (Exception e) {
                System.out.println(" Error: " + e.getMessage());
            }
        }
    }

    private static void registrarDiagnostico(Medico medico) {
        System.out.println("\n[Registrar Diagnostico]");
        String idCita = leerCadenaNoVacia("ID de la Cita: ");
        CitaMedica cita = buscarCita(idCita);
        if (cita == null || cita.getMedico() != medico) {
            System.out.println(" No se encontro la cita o no le pertenece.");
            return;
        }

        String idDiag = leerCadenaNoVacia("ID Diagnostico: ");
        String desc = leerCadenaNoVacia("Descripcion: ");
        String receta = leerCadenaNoVacia("Receta: ");

        Diagnostico diag = new Diagnostico(idDiag, desc, receta, LocalDate.now(), medico);
        cita.completar(diag);
        System.out.println(" Diagnostico guardado y cita completada.");
    }

    private static void consultarHistorial(Medico medico) {
        String idPac = leerCadenaNoVacia("\nID del Paciente: ");
        Paciente p = buscarPaciente(idPac);
        if (p == null) {
            System.out.println(" Paciente no encontrado.");
            return;
        }

        System.out.println("Historial de: " + p.getNombre() + " (Sangre: " + p.getGrupoSanguineo() + ")");
        System.out.println("Alergias: " + p.getAlergias());

        List<CitaMedica> historial = p.obtenerHistorial();
        if (historial.isEmpty()) {
            System.out.println(" No hay citas registradas para este paciente.");
        } else {
            for (CitaMedica c : historial) {
                System.out.println(" - Cita: " + c.getFechaHora() + " | Estado: " + c.getEstado());
                if (c.getDiagnostico() != null) {
                    System.out.println("   Diagnostico: " + c.getDiagnostico().getDescripcion() + " (Receta: "
                            + c.getDiagnostico().getReceta() + ")");
                }
            }
        }
    }

    // MENU ENFERMERO
    private static void menuEnfermero() {
        String id = leerCadenaNoVacia("\nIngrese su ID de Enfermero: ");
        Enfermero enf = null;
        for (Empleado e : hospital.getEmpleados()) {
            if (e instanceof Enfermero && e.getId().equals(id)) {
                enf = (Enfermero) e;
                break;
            }
        }
        if (enf == null) {
            System.out.println(" Acceso denegado. Enfermero no encontrado.");
            return;
        }

        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- MENU ENFERMERO (" + enf.getNombre() + ") ---");
            System.out.println("1. Asistir en Cirugia");
            System.out.println("2. Consultar Pacientes a Cargo");
            System.out.println("0. Volver");
            String opt = leerCadenaNoVacia("Opcion: ");
            try {
                switch (opt) {
                    case "1":
                        enf.asistirCirugia();
                        break;
                    case "2":
                        List<Paciente> cargo = enf.getPacientesACargo();
                        if (cargo.isEmpty())
                            System.out.println("No hay pacientes asignados a ti por el momento.");
                        for (Paciente p : cargo) {
                            System.out.println("- " + p.getNombre() + " " + p.getApellido() + " (Cama/Area: "
                                    + enf.getAreaAsignada() + ")");
                        }
                        break;
                    case "0":
                        volver = true;
                        break;
                    default:
                        System.out.println("Opcion invalida.");
                }
            } catch (Exception e) {
                System.out.println(" Error: " + e.getMessage());
            }
        }
    }

    // MENU PACIENTE
    private static void menuPaciente() {
        String idStr = leerCadenaNoVacia("\nIngrese su ID de Paciente: ");
        Paciente p = buscarPaciente(idStr);
        if (p == null) {
            System.out.println(" Acceso denegado. Paciente no encontrado.");
            return;
        }

        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- MENU PACIENTE (" + p.getNombre() + ") ---");
            System.out.println("1. Consultar mi Historial de Citas");
            System.out.println("0. Volver");
            String opt = leerCadenaNoVacia("Opcion: ");
            try {
                if (opt.equals("1")) {
                    List<CitaMedica> historial = p.obtenerHistorial();
                    if (historial.isEmpty()) {
                        System.out.println(" No tiene citas registradas en su historial.");
                    } else {
                        for (CitaMedica c : historial) {
                            System.out.println(" - " + c.getFechaHora() + " con Dr. " + c.getMedico().getNombre() + " ("
                                    + c.getEstado() + ")");
                        }
                    }
                } else if (opt.equals("0")) {
                    volver = true;
                } else {
                    System.out.println("Opcion invalida.");
                }
            } catch (Exception e) {
                System.out.println(" Error: " + e.getMessage());
            }
        }
    }

    // FUNCIONES AUXILIARES DE ENTRADA SEGURA
    private static String leerCadena() {
        if (!scanner.hasNextLine()) {
            System.out.println("\nEntrada cerrada abruptamente. Saliendo del sistema...");
            System.exit(0);
        }
        return scanner.nextLine();
    }

    private static String leerCadenaNoVacia(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String input = leerCadena();
            if (!input.trim().isEmpty()) {
                return input;
            }
            System.out.println(" Este campo es obligatorio y no puede estar vacio.");
        }
    }

    private static double leerDoubleSeguro(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Double.parseDouble(leerCadena());
            } catch (NumberFormatException e) {
                System.out.println(" Valor numerico invalido. Intentelo de nuevo (Ej: 1500.50).");
            }
        }
    }

    private static LocalDate leerFechaSegura(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return LocalDate.parse(leerCadena());
            } catch (DateTimeParseException e) {
                System.out.println(" Formato de fecha invalido. Intente usar YYYY-MM-DD (Ej: 1990-12-31).");
            }
        }
    }

    private static LocalDateTime leerFechaHoraSegura(String mensaje) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        while (true) {
            System.out.print(mensaje);
            try {
                return LocalDateTime.parse(leerCadena(), formatter);
            } catch (DateTimeParseException e) {
                System.out.println(" Formato de fecha y hora invalido. Ejemplo (2026-10-25 14:30).");
            }
        }
    }

    // BUSQUEDAS Y DATOS DE PRUEBA
    private static Paciente buscarPaciente(String id) {
        for (Paciente p : hospital.getPacientes()) {
            if (p.getId().equals(id))
                return p;
        }
        return null;
    }

    private static Medico buscarMedico(String id) {
        for (Empleado e : hospital.getEmpleados()) {
            if (e instanceof Medico && e.getId().equals(id))
                return (Medico) e;
        }
        return null;
    }

    private static CitaMedica buscarCita(String id) {
        for (CitaMedica c : hospital.getCitas()) {
            if (c.getId().equals(id))
                return c;
        }
        return null;
    }

    private static Especialidad buscarEspecialidad(String cod) {
        for (Especialidad es : especialidadesGlobales) {
            if (es.getCodigo().equals(cod))
                return es;
        }
        return null;
    }

}
