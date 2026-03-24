package modelo.hospital;

import modelo.personas.Medico;
import java.time.LocalDate;

public class Diagnostico {
    private String id;
    private String descripcion;
    private String receta;
    private LocalDate fecha;
    private Medico medico;

    public Diagnostico(String id, String descripcion, String receta, LocalDate fecha, Medico medico) {
        setId(id);
        setDescripcion(descripcion);
        setReceta(receta);
        setFecha(fecha);
        setMedico(medico);
    }

    public String getId() { return id; }
    public void setId(String id) { 
        if (id == null || id.trim().isEmpty()) throw new IllegalArgumentException("El ID no puede ser nulo o vacio.");
        this.id = id; 
    }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { 
        if (descripcion == null || descripcion.trim().isEmpty()) throw new IllegalArgumentException("La descripcion no puede ser nula o vacia.");
        this.descripcion = descripcion; 
    }

    public String getReceta() { return receta; }
    public void setReceta(String receta) { 
        if (receta == null || receta.trim().isEmpty()) throw new IllegalArgumentException("La receta no puede ser nula o vacia.");
        this.receta = receta; 
    }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { 
        if (fecha == null) throw new IllegalArgumentException("La fecha no puede ser nula.");
        this.fecha = fecha; 
    }

    public Medico getMedico() { return medico; }
    public void setMedico(Medico medico) { 
        if (medico == null) throw new IllegalArgumentException("El medico no puede ser nulo.");
        this.medico = medico; 
    }
}
