package paqueteDTO;

public class EventoDTO {

    private int codigoActividad;
    private String responsableEvento;
    private String descripcionEvento;
    private int nroNotaEvento;

    // Constructor
    public EventoDTO(int codigoActividad, String responsableEvento, String descripcionEvento, int nroNotaEvento) {
        this.codigoActividad = codigoActividad;
        this.responsableEvento = responsableEvento;
        this.descripcionEvento = descripcionEvento;
        this.nroNotaEvento = nroNotaEvento;
    }

    // Getters y Setters
    public int getCodigoActividad() {
        return codigoActividad;
    }

    public void setCodigoActividad(int codigoActividad) {
        this.codigoActividad = codigoActividad;
    }

    public String getResponsableEvento() {
        return responsableEvento;
    }

    public void setResponsableEvento(String responsableEvento) {
        this.responsableEvento = responsableEvento;
    }

    public String getDescripcionEvento() {
        return descripcionEvento;
    }

    public void setDescripcionEvento(String descripcionEvento) {
        this.descripcionEvento = descripcionEvento;
    }

    public int getNroNotaEvento() {
        return nroNotaEvento;
    }

    public void setNroNotaEvento(int nroNotaEvento) {
        this.nroNotaEvento = nroNotaEvento;
    }
}
