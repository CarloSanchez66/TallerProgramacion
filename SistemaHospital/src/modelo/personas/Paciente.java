package modelo.personas;

import modelo.abstractas.Persona;
import modelo.hospital.CitaMedica;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class Paciente extends Persona {
    private String historiaClinicaId;
    private String grupoSanguineo;
    private List<String> alergias;
    private List<CitaMedica> citas;

    public Paciente(String id, String nombre, String apellido, LocalDate fechaNacimiento, String email,
                    String historiaClinicaId, String grupoSanguineo) {
        super(id, nombre, apellido, fechaNacimiento, email);
        setHistoriaClinicaId(historiaClinicaId);
        setGrupoSanguineo(grupoSanguineo);
        this.alergias = new ArrayList<>();
        this.citas = new ArrayList<>();
    }

    public void agregarAlergia(String alergia) {
        if (alergia == null || alergia.trim().isEmpty()) throw new IllegalArgumentException("La alergia no puede ser nula o vacia.");
        this.alergias.add(alergia);
    }

    public List<CitaMedica> obtenerHistorial() {
        return new ArrayList<>(citas); // Copia defensiva
    }

    public void agregarCita(CitaMedica cita) {
        if(cita == null) throw new IllegalArgumentException("La cita no puede ser nula.");
        this.citas.add(cita);
    }

    @Override
    public int calcularEdad() {
        if (getFechaNacimiento() == null) return 0;
        return Period.between(getFechaNacimiento(), LocalDate.now()).getYears();
    }

    @Override
    public String obtenerTipo() {
        return "Paciente";
    }

    // Getters y Setters
    public String getHistoriaClinicaId() { return historiaClinicaId; }
    public void setHistoriaClinicaId(String historiaClinicaId) { 
        if (historiaClinicaId == null || historiaClinicaId.trim().isEmpty()) throw new IllegalArgumentException("ID de historia clinica no valido.");
        this.historiaClinicaId = historiaClinicaId; 
    }
    public String getGrupoSanguineo() { return grupoSanguineo; }
    public void setGrupoSanguineo(String grupoSanguineo) { 
        if (grupoSanguineo == null || grupoSanguineo.trim().isEmpty()) throw new IllegalArgumentException("Grupo sanguineo no valido.");
        this.grupoSanguineo = grupoSanguineo; 
    }
    public List<String> getAlergias() { return new ArrayList<>(alergias); }
    public void setAlergias(List<String> alergias) { this.alergias = new ArrayList<>(alergias); }
    public List<CitaMedica> getCitas() { return new ArrayList<>(citas); }
    public void setCitas(List<CitaMedica> citas) { this.citas = new ArrayList<>(citas); }
}
