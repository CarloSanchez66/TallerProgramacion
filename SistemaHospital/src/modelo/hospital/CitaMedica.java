package modelo.hospital;

import modelo.personas.Paciente;
import modelo.personas.Medico;
import modelo.enums.EstadoCita;

import java.time.LocalDateTime;

public class CitaMedica {
    private String id;
    private Paciente paciente;
    private Medico medico;
    private LocalDateTime fechaHora;
    private String motivo;
    private EstadoCita estado;
    private double costo;
    private Diagnostico diagnostico;

    public CitaMedica(String id, Paciente paciente, Medico medico, LocalDateTime fechaHora, String motivo) {
        setId(id);
        setPaciente(paciente);
        setMedico(medico);
        setFechaHora(fechaHora);
        setMotivo(motivo);
        setEstado(EstadoCita.PENDIENTE);
    }

    public double calcularCosto() {
        if (medico != null && medico.getEspecialidad() != null) {
            return medico.getEspecialidad().getCostoConsulta();
        }
        return 0.0;
    }

    public void completar(Diagnostico diagnostico) {
        setEstado(EstadoCita.COMPLETADA);
        setDiagnostico(diagnostico);
        this.medico.atenderPaciente(this.paciente);
    }

    public void cancelar() {
        setEstado(EstadoCita.CANCELADA);
    }

    public String getId() { return id; }
    public void setId(String id) { 
        if (id == null || id.trim().isEmpty()) throw new IllegalArgumentException("El ID no puede ser nulo o vacio.");
        this.id = id; 
    }

    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { 
        if (paciente == null) throw new IllegalArgumentException("El paciente no puede ser nulo.");
        this.paciente = paciente; 
    }

    public Medico getMedico() { return medico; }
    public void setMedico(Medico medico) { 
        if (medico == null) throw new IllegalArgumentException("El medico no puede ser nulo.");
        this.medico = medico; 
        this.costo = calcularCosto(); 
    }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) {
        if (fechaHora == null) throw new IllegalArgumentException("La fecha u hora no puede ser nula.");
        if (fechaHora.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("La cita no puede ser programada en el pasado.");
        }
        this.fechaHora = fechaHora;
    }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { 
        if (motivo == null || motivo.trim().isEmpty()) throw new IllegalArgumentException("El motivo no puede ser nulo o vacio.");
        this.motivo = motivo; 
    }

    public EstadoCita getEstado() { return estado; }
    public void setEstado(EstadoCita estado) { 
        if (estado == null) throw new IllegalArgumentException("El estado no puede ser nulo.");
        this.estado = estado; 
    }

    public double getCosto() { return costo; }
    public void setCosto(double costo) { 
        if (costo < 0) throw new IllegalArgumentException("El costo no puede ser negativo.");
        this.costo = costo; 
    }

    public Diagnostico getDiagnostico() { return diagnostico; }
    public void setDiagnostico(Diagnostico diagnostico) { 
        if (diagnostico == null) throw new IllegalArgumentException("El diagnostico no puede ser nulo.");
        this.diagnostico = diagnostico; 
    }
}
