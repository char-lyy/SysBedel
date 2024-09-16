package paqueteDTO;
public class ObservacionDTO {

    private int codigoObservacion;
    private int numeroAula;
    private String descripcion;
    private Fecha fechaObservacion;

    // Constructor vacío
    public ObservacionDTO() {
    }

    // Constructor con parámetros
    public ObservacionDTO(int codigoObservacion, int numeroAula, String descripcion, Fecha fechaObservacion) {
        this.codigoObservacion = codigoObservacion;
        this.numeroAula = numeroAula;
        this.descripcion = descripcion;
        this.fechaObservacion = fechaObservacion;
    }

    // Getters y Setters
    public int getCodigoObservacion() {
        return codigoObservacion;
    }

    public void setCodigoObservacion(int codigoObservacion) {
        this.codigoObservacion = codigoObservacion;
    }

    public int getNumeroAula() {
        return numeroAula;
    }

    public void setNumeroAula(int numeroAula) {
        this.numeroAula = numeroAula;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Fecha getFechaObservacion() {
        return fechaObservacion;
    }

    public void setFechaObservacion(Fecha fechaObservacion) {
        this.fechaObservacion = fechaObservacion;
    }

    // Sobrescribir toString() para mostrar la información
    @Override
    public String toString() {
        return "ObservacionDTO{" +
                "codigoObservacion=" + codigoObservacion +
                ", numeroAula=" + numeroAula +
                ", descripcion='" + descripcion + '\'' +
                ", fechaObservacion=" + fechaObservacion +
                '}';
    }
}
