package paqueteDTO;

import java.util.Date;

public class ClaseDTO extends ActividadDTO {

    private int legajoDocente;
    private String asignatura;

    public ClaseDTO() {
//        super();
    }

    public ClaseDTO(int legajoDocente, String asignatura, String codigoActividad, int numeroAula, String codigoHorario, Date fechaInicioActividad, Date fechaFinActividad, String periodoActividad, String tipoActividad) {
        super(codigoActividad, numeroAula, codigoHorario, fechaInicioActividad, fechaFinActividad, periodoActividad, tipoActividad);
        this.legajoDocente = legajoDocente;
        this.asignatura = asignatura;
    }

    public ClaseDTO(String codigoActividad, int legajoDocente, String asignatura) {
        super(codigoActividad);
        this.legajoDocente = legajoDocente;
        this.asignatura = asignatura;
    }
    
    public ClaseDTO cargarDatos(){
        
        
        return this;
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

}
