package modelo.abstractas;

import java.time.LocalDate;
import java.time.Period;

public abstract class Empleado extends Persona {
    private String legajo;
    private LocalDate fechaContratacion;
    private double salarioBase;
    private boolean activo;

    public Empleado(String id, String nombre, String apellido, LocalDate fechaNacimiento, String email,
                    String legajo, LocalDate fechaContratacion, double salarioBase, boolean activo) {
        super(id, nombre, apellido, fechaNacimiento, email);
        setLegajo(legajo);
        setFechaContratacion(fechaContratacion);
        setSalarioBase(salarioBase);
        setActivo(activo);
    }

    public abstract double calcularSalario();

    @Override
    public int calcularEdad() {
        if (getFechaNacimiento() == null) return 0;
        return Period.between(getFechaNacimiento(), LocalDate.now()).getYears();
    }

    public int antiguedad() {
        if (fechaContratacion == null) return 0;
        return Period.between(fechaContratacion, LocalDate.now()).getYears();
    }

    public String getLegajo() {
        return legajo;
    }

    public void setLegajo(String legajo) {
        if (legajo == null || legajo.trim().isEmpty()) throw new IllegalArgumentException("Legajo no puede ser nulo o vacio.");
        this.legajo = legajo;
    }

    public LocalDate getFechaContratacion() {
        return fechaContratacion;
    }

    public void setFechaContratacion(LocalDate fechaContratacion) {
        if (fechaContratacion == null) throw new IllegalArgumentException("La fecha de contratacion no puede ser nula.");
        if (fechaContratacion.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de contratacion no puede ser futura.");
        }
        this.fechaContratacion = fechaContratacion;
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(double salarioBase) {
        if (salarioBase < 0) {
            throw new IllegalArgumentException("El salario base no puede ser negativo.");
        }
        this.salarioBase = salarioBase;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
