package paqueteDTO;

import java.util.Date;

public class EventoDTO extends ActividadDTO {

    private String responsableEvento;
    private String descripcionEvento;
    private int nroNotaEvento;

    public EventoDTO(String responsableEvento, String descripcionEvento, int nroNotaEvento, String codigoActividad, int numeroAula, String codigoHorario, Date fechaInicioActividad, Date fechaFinActividad, String periodoActividad, String tipoActividad) {
        super(codigoActividad, numeroAula, codigoHorario, fechaInicioActividad, fechaFinActividad, periodoActividad, tipoActividad);
        this.responsableEvento = responsableEvento;
        this.descripcionEvento = descripcionEvento;
        this.nroNotaEvento = nroNotaEvento;
    }

    public EventoDTO(String codigoActividad, String responsableEvento, String descripcionEvento, int nroNotaEvento) {
        super(codigoActividad);
        this.responsableEvento = responsableEvento;
        this.nroNotaEvento = nroNotaEvento;

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
