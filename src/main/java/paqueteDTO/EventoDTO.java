package paqueteDTO;

public class EventoDTO extends ActividadDTO {

    private String responsableEvento;
    private String descripcionEvento;
    private int nroNotaEvento;

    public EventoDTO(int idActividad, FechaDTO fechaInicioActividad, FechaDTO fechaFinActividad, PeriodoActividad periodoActividad, TipoActividad tipoActividad) {
        super(idActividad, fechaInicioActividad, fechaFinActividad, periodoActividad, tipoActividad);
    }

}
