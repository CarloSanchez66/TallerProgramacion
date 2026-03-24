package modelo.hospital;

public class Especialidad {
    private String codigo;
    private String nombre;
    private String descripcion;
    private double costoConsulta;

    public Especialidad(String codigo, String nombre, String descripcion, double costoConsulta) {
        setCodigo(codigo);
        setNombre(nombre);
        setDescripcion(descripcion);
        setCostoConsulta(costoConsulta);
    }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { 
        if (codigo == null || codigo.trim().isEmpty()) throw new IllegalArgumentException("El codigo no puede ser nulo o vacio.");
        this.codigo = codigo; 
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { 
        if (nombre == null || nombre.trim().isEmpty()) throw new IllegalArgumentException("El nombre no puede ser nulo o vacio.");
        this.nombre = nombre; 
    }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { 
        if (descripcion == null || descripcion.trim().isEmpty()) throw new IllegalArgumentException("La descripcion no puede ser nula o vacia.");
        this.descripcion = descripcion; 
    }

    public double getCostoConsulta() { return costoConsulta; }
    public void setCostoConsulta(double costoConsulta) {
        if(costoConsulta < 0) throw new IllegalArgumentException("El costo de la consulta no puede ser negativo.");
        this.costoConsulta = costoConsulta;
    }
}
