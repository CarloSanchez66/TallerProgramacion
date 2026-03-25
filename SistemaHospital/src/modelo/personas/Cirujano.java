package modelo.personas;

import modelo.hospital.Especialidad;

import java.time.LocalDate;

public class Cirujano extends Medico {
    private int cirugiasRealizadas;
    private boolean disponibleEmergencias;

    public Cirujano(String id, String nombre, String apellido, LocalDate fechaNacimiento, String email,
                    String legajo, LocalDate fechaContratacion, double salarioBase, boolean activo,
                    String numeroLicencia, Especialidad especialidad, boolean disponibleEmergencias) {
        super(id, nombre, apellido, fechaNacimiento, email, legajo, fechaContratacion, salarioBase, activo, numeroLicencia, especialidad);
        setCirugiasRealizadas(0);
        setDisponibleEmergencias(disponibleEmergencias);
    }

    public void realizarCirugia() {
        cirugiasRealizadas++;
    }

    public double calcularBono() {
        return cirugiasRealizadas * 100.0; 
    }

    @Override
    public double calcularSalario() {
        double salarioMedico = super.calcularSalario();
        double extraEmergencias = disponibleEmergencias ? 500.0 : 0.0;
        return salarioMedico + calcularBono() + extraEmergencias;
    }

    @Override
    public String obtenerTipo() {
        return "Cirujano";
    }

    public int getCirugiasRealizadas() { return cirugiasRealizadas; }
    public void setCirugiasRealizadas(int cirugiasRealizadas) { 
        if (cirugiasRealizadas < 0) throw new IllegalArgumentException("Las cirugias realizadas no pueden ser negativas.");
        this.cirugiasRealizadas = cirugiasRealizadas; 
    }
    public boolean isDisponibleEmergencias() { return disponibleEmergencias; }
    public void setDisponibleEmergencias(boolean disponibleEmergencias) { this.disponibleEmergencias = disponibleEmergencias; }
}
