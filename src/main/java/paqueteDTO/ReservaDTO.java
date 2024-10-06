package paqueteDTO;

import java.sql.Date;
import java.time.DayOfWeek;

public class ReservaDTO {

    private int idReserva;
    private int idActividad;
    private int numeroAula;
    private boolean confirmada;
    private TiempoDTO horaInicio;
    private TiempoDTO horaFin;
    private FechaDTO fechaReserva;
    private FechaDTO fechaActividad;
    private DayOfWeek diaActividadPeriodica;
    private String descripcion;
    
    public ReservaDTO(int idActividad, int numeroAula, TiempoDTO horaInicio, TiempoDTO horaFin, FechaDTO fechaActividad, String descripcion) {
        this.idActividad = idActividad;
        this.numeroAula = numeroAula;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.fechaActividad = fechaActividad;
        this.descripcion = descripcion;
    }
    
    
    public ReservaDTO(int idReserva, int idActividad, int numeroAula, boolean confirmada, TiempoDTO horaInicio, TiempoDTO horaFin, FechaDTO fechaReserva, FechaDTO fechaActividad, String descripcion) {
        this.idReserva = idReserva;
        this.idActividad = idActividad;
        this.numeroAula = numeroAula;
        this.confirmada = confirmada;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.descripcion = descripcion;
        this.fechaReserva = fechaReserva;
        this.fechaActividad = fechaActividad;
    }

    public ReservaDTO(int idActividad, int numeroAula, FechaDTO fechaReserva) {
        this.idActividad = idActividad;
        this.numeroAula = numeroAula;
        this.fechaReserva = fechaReserva;
    }

    public ReservaDTO(int idActividad, int numeroAula, TiempoDTO horaInicio, TiempoDTO horaFin, DayOfWeek diaSemana, String descripcion) {
        this.idActividad = idActividad;
        this.numeroAula = numeroAula;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.diaActividadPeriodica = diaSemana;
        this.descripcion = descripcion;
    }
    
    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public int getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
    }

    public int getNumeroAula() {
        return numeroAula;
    }

    public void setNumeroAula(int numeroAula) {
        this.numeroAula = numeroAula;
    }

    public boolean isConfirmada() {
        return confirmada;
    }

    public void setConfirmada(boolean confirmada) {
        this.confirmada = confirmada;
    }

    public TiempoDTO getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(TiempoDTO horaInicio) {
        this.horaInicio = horaInicio;
    }

    public TiempoDTO getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(TiempoDTO horaFin) {
        this.horaFin = horaFin;
    }

    public FechaDTO getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(FechaDTO fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public FechaDTO getFechaActividad() {
        return fechaActividad;
    }

    public void setFechaActividad(FechaDTO fechaActividad) {
        this.fechaActividad = fechaActividad;
    }

    public DayOfWeek getDiaActividadPeriodica() {
        return diaActividadPeriodica;
    }

    public void setDiaActividadPeriodica(DayOfWeek diaActividadPeriodica) {
        this.diaActividadPeriodica = diaActividadPeriodica;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
}
