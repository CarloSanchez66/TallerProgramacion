package modelo.hospital;

import modelo.abstractas.Empleado;
import modelo.personas.Paciente;

import java.util.ArrayList;
import java.util.List;

public class Hospital {
    private String nombre;
    private String direccion;
    private List<Empleado> empleados;
    private List<Paciente> pacientes;
    private List<CitaMedica> citas;

    public Hospital(String nombre, String direccion) {
        setNombre(nombre);
        setDireccion(direccion);
        this.empleados = new ArrayList<>();
        this.pacientes = new ArrayList<>();
        this.citas = new ArrayList<>();
    }

    public void contratarEmpleado(Empleado empleado) {
        if (empleado == null) throw new IllegalArgumentException("El empleado no puede ser nulo.");
        this.empleados.add(empleado);
    }
    
    public void registrarPaciente(Paciente paciente) {
        if (paciente == null) throw new IllegalArgumentException("El paciente no puede ser nulo.");
        this.pacientes.add(paciente);
    }

    public void agendarCita(CitaMedica cita) {
        if (cita == null) throw new IllegalArgumentException("La cita no puede ser nula.");
        this.citas.add(cita);
        cita.getPaciente().agregarCita(cita);
    }

    public double calcularNominaTotal() {
        double nomina = 0.0;
        for (Empleado empleado : empleados) {
            if (empleado.isActivo()) {
                nomina += empleado.calcularSalario();
            }
        }
        return nomina;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { 
        if (nombre == null || nombre.trim().isEmpty()) throw new IllegalArgumentException("El nombre no puede ser nulo o vacio.");
        this.nombre = nombre; 
    }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { 
        if (direccion == null || direccion.trim().isEmpty()) throw new IllegalArgumentException("La direccion no puede ser nula o vacia.");
        this.direccion = direccion; 
    }
    
    public List<Empleado> getEmpleados() { return new ArrayList<>(empleados); }
    public void setEmpleados(List<Empleado> empleados) { this.empleados = new ArrayList<>(empleados); }
    
    public List<Paciente> getPacientes() { return new ArrayList<>(pacientes); }
    public void setPacientes(List<Paciente> pacientes) { this.pacientes = new ArrayList<>(pacientes); }
    
    public List<CitaMedica> getCitas() { return new ArrayList<>(citas); }
    public void setCitas(List<CitaMedica> citas) { this.citas = new ArrayList<>(citas); }
}
