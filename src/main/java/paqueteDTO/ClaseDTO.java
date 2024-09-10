package paqueteDTO;

public class ClaseDTO {

    public ClaseDTO(int codigoActividad, int legajoDocente, String asignatura) {
        this.codigoActividad = codigoActividad;
        this.legajoDocente = legajoDocente;
        this.asignatura = asignatura;
    }

    public int getCodigoActividad() {
        return codigoActividad;
    }

    public void setCodigoActividad(int codigoActividad) {
        this.codigoActividad = codigoActividad;
    }

    public int getLegajoDocente() {
        return legajoDocente;
    }

    public void setLegajoDocente(int legajoDocente) {
        this.legajoDocente = legajoDocente;
    }

    public String getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(String asignatura) {
        this.asignatura = asignatura;
    }

    private int codigoActividad;
    private int legajoDocente;
    private String asignatura;

}