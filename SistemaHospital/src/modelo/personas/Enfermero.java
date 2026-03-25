package modelo.personas;

import modelo.abstractas.Empleado;
import modelo.enums.Turno;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Enfermero extends Empleado {
    private Turno turno;
    private String areaAsignada;
    private List<Paciente> pacientesACargo;

    public Enfermero(String id, String nombre, String apellido, LocalDate fechaNacimiento, String email,
                     String legajo, LocalDate fechaContratacion, double salarioBase, boolean activo,
                     Turno turno, String areaAsignada) {
        super(id, nombre, apellido, fechaNacimiento, email, legajo, fechaContratacion, salarioBase, activo);
        setTurno(turno);
        setAreaAsignada(areaAsignada);
        this.pacientesACargo = new ArrayList<>();
    }

    public void asistirCirugia() {
        System.out.println("Enfermero " + getNombre() + " asistiendo en cirugia.");
    }

    @Override
    public double calcularSalario() {
        double bonoTurno = 0.0;
        if (turno == Turno.NOCHE) {
            bonoTurno = getSalarioBase() * 0.20;
        } else if (turno == Turno.TARDE) {
            bonoTurno = getSalarioBase() * 0.10;
        }
        return getSalarioBase() + bonoTurno + (getSalarioBase() * 0.05 * antiguedad());
    }

    @Override
    public String obtenerTipo() {
        return "Enfermero";
    }

    public Turno getTurno() { return turno; }
    public void setTurno(Turno turno) { 
        if (turno == null) throw new IllegalArgumentException("El turno no puede ser nulo.");
        this.turno = turno; 
    }
    public String getAreaAsignada() { return areaAsignada; }
    public void setAreaAsignada(String areaAsignada) { 
        if (areaAsignada == null || areaAsignada.trim().isEmpty()) throw new IllegalArgumentException("El area asignada no puede ser nula o vacia.");
        this.areaAsignada = areaAsignada; 
    }
    public List<Paciente> getPacientesACargo() { return new ArrayList<>(pacientesACargo); }
    public void setPacientesACargo(List<Paciente> pacientesACargo) { this.pacientesACargo = new ArrayList<>(pacientesACargo); }
    public void agregarPaciente(Paciente paciente) { this.pacientesACargo.add(paciente); }
}
