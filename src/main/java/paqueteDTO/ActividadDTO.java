package paqueteDTO;

import java.util.Date;

public class ActividadDTO {

    protected String codigoActividad;
    protected int numeroAula;
    protected String codigoHorario;
    protected Date fechaInicioActividad;
    protected Date fechaFinActividad;
    protected String periodoActividad;
    protected String tipoActividad;

    // Constructor
    public ActividadDTO(String codigoActividad, int numeroAula, String codigoHorario, Date fechaInicioActividad,
            Date fechaFinActividad, String periodoActividad, String tipoActividad) {
        this.codigoActividad = codigoActividad;
        this.numeroAula = numeroAula;
        this.codigoHorario = codigoHorario;
        this.fechaInicioActividad = fechaInicioActividad;
        this.fechaFinActividad = fechaFinActividad;
        this.periodoActividad = periodoActividad;
        this.tipoActividad = tipoActividad;
    }

    public ActividadDTO(String codigoActividad) {
        this.codigoActividad = codigoActividad;
    }

    public ActividadDTO() {
    }

    // Getters y Setters
    public String getCodigoActividad() {
        return codigoActividad;
    }

    public void setCodigoActividad(String codigoActividad) {
        this.codigoActividad = codigoActividad;
    }

    public int getNumeroAula() {
        return numeroAula;
    }

    public void setNumeroAula(int numeroAula) {
        this.numeroAula = numeroAula;
    }

    public String getCodigoHorario() {
        return codigoHorario;
    }

    public void setCodigoHorario(String codigoHorario) {
        this.codigoHorario = codigoHorario;
    }

    public Date getFechaInicioActividad() {
        return fechaInicioActividad;
    }

    public void setFechaInicioActividad(Date fechaInicioActividad) {
        this.fechaInicioActividad = fechaInicioActividad;
    }

    public Date getFechaFinActividad() {
        return fechaFinActividad;
    }

    public void setFechaFinActividad(Date fechaFinActividad) {
        this.fechaFinActividad = fechaFinActividad;
    }

    public String getPeriodoActividad() {
        return periodoActividad;
    }

    public void setPeriodoActividad(String periodoActividad) {
        this.periodoActividad = periodoActividad;
    }

    public String getTipoActividad() {
        return tipoActividad;
    }

    public void setTipoActividad(String tipoActividad) {
        this.tipoActividad = tipoActividad;
    }
}
