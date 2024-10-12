package paqueteDTO;

public class AulaDTO {
    private int nroAula;
    private int capacidad;
    private boolean ocupada;
    private String observaciones;
    private String responsable; // Nuevo atributo
    private String fecha; // Nuevo atributo
    private String hora; // Nuevo atributo

    // Constructor actualizado
    public AulaDTO(int nroAula, int capacidad, boolean ocupada, String observaciones, String responsable, String fecha, String hora) {
        this.nroAula = nroAula;
        this.capacidad = capacidad;
        this.ocupada = ocupada;
        this.observaciones = observaciones;
        this.responsable = responsable;
        this.fecha = fecha;
        this.hora = hora;
    }

    // Getters y Setters
    public int getNroAula() {
        return nroAula;
    }

    public void setNroAula(int nroAula) {
        this.nroAula = nroAula;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public boolean isOcupada() {
        return ocupada;
    }

    public void setOcupada(boolean ocupada) {
        this.ocupada = ocupada;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
