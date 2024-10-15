package paqueteDTO;

import java.sql.Date;
import java.time.DayOfWeek;
import utilidades.Tiempo;
import utilidades.Fecha;

public class ReservaDTO {

    private int idReserva;
    private int idActividad;
    private int numeroAula;
    private boolean confirmada;
    private Tiempo horaInicio;
    private Tiempo horaFin;
    private Fecha fechaReserva;
    private Fecha fechaActividad;
    private DayOfWeek diaActividadPeriodica;
    private String descripcion;
    private String responsable;

    public ReservaDTO(int idReserva, int idActividad, int numeroAula, boolean confirmada, Tiempo horaInicio, Tiempo horaFin, Fecha fechaReserva, Fecha fechaActividad, DayOfWeek diaActividadPeriodica, String descripcion, String responsable) {
        this.idReserva = idReserva;
        this.idActividad = idActividad;
        this.numeroAula = numeroAula;
        this.confirmada = confirmada;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.fechaReserva = fechaReserva;
        this.fechaActividad = fechaActividad;
        this.diaActividadPeriodica = diaActividadPeriodica;
        this.descripcion = descripcion;
        this.responsable = responsable;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }
    
    public ReservaDTO(int idReserva, int idActividad, int numeroAula, boolean confirmada, Tiempo horaInicio, Tiempo horaFin, Fecha fechaReserva, Fecha fechaActividad, String descripcion) {
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

    public ReservaDTO(int idActividad, int numeroAula, Fecha fechaReserva) {
        this.idActividad = idActividad;
        this.numeroAula = numeroAula;
        this.fechaReserva = fechaReserva;
    }

    public ReservaDTO(int idActividad, int numeroAula, Tiempo horaInicio, Tiempo horaFin, DayOfWeek diaSemana, String descripcion) {
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

    public Tiempo getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Tiempo horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Tiempo getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Tiempo horaFin) {
        this.horaFin = horaFin;
    }

    public Fecha getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(Fecha fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public Fecha getFechaActividad() {
        return fechaActividad;
    }

    public void setFechaActividad(Fecha fechaActividad) {
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
