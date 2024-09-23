package paqueteDTO;

public class ClaseDTO extends ActividadDTO {

    private String legajoDocente;
    private String asignatura;

    public ClaseDTO(int idActividad, FechaDTO fechaInicioActividad, FechaDTO fechaFinActividad, PeriodoActividad periodoActividad, TipoActividad tipoActividad) {
        super(idActividad, fechaInicioActividad, fechaFinActividad, periodoActividad, tipoActividad);
    }



}
