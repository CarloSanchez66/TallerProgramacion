package modelo.personas;

import modelo.abstractas.Empleado;
import modelo.hospital.Especialidad;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Medico extends Empleado {
    private String numeroLicencia;
    private Especialidad especialidad;
    private List<Paciente> pacientesAsignados;
    private int citasAtendidas;

    public Medico(String id, String nombre, String apellido, LocalDate fechaNacimiento, String email,
                  String legajo, LocalDate fechaContratacion, double salarioBase, boolean activo,
                  String numeroLicencia, Especialidad especialidad) {
        super(id, nombre, apellido, fechaNacimiento, email, legajo, fechaContratacion, salarioBase, activo);
        setNumeroLicencia(numeroLicencia);
        setEspecialidad(especialidad);
        this.pacientesAsignados = new ArrayList<>();
        setCitasAtendidas(0);
    }

    public void atenderPaciente(Paciente paciente) {
        if (!pacientesAsignados.contains(paciente)) {
            pacientesAsignados.add(paciente);
        }
        citasAtendidas++;
    }

    @Override
    public double calcularSalario() {
        double bonoAntiguedad = getSalarioBase() * 0.05 * antiguedad();
        double bonoCitas = citasAtendidas * 10.0;
        return getSalarioBase() + bonoAntiguedad + bonoCitas;
    }

    @Override
    public String obtenerTipo() {
        return "Medico";
    }

    // Getters y setters
    public String getNumeroLicencia() { return numeroLicencia; }
    public void setNumeroLicencia(String numeroLicencia) { 
        if (numeroLicencia == null || numeroLicencia.trim().isEmpty()) throw new IllegalArgumentException("Numero de licencia no valido.");
        this.numeroLicencia = numeroLicencia; 
    }
    public Especialidad getEspecialidad() { return especialidad; }
    public void setEspecialidad(Especialidad especialidad) { 
        if (especialidad == null) throw new IllegalArgumentException("La especialidad no puede ser nula.");
        this.especialidad = especialidad; 
    }
    public List<Paciente> getPacientesAsignados() { return new ArrayList<>(pacientesAsignados); }
    public void setPacientesAsignados(List<Paciente> pacientesAsignados) { this.pacientesAsignados = new ArrayList<>(pacientesAsignados); }
    public int getCitasAtendidas() { return citasAtendidas; }
    public void setCitasAtendidas(int citasAtendidas) { 
        if (citasAtendidas < 0) throw new IllegalArgumentException("Las citas atendidas no pueden ser negativas.");
        this.citasAtendidas = citasAtendidas; 
    }
}
