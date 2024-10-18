package paqueteDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import utilidades.Fecha;
import utilidades.Tiempo;
import utilidades.FechaTiempo;

public class ReservaDTO {

    private int id;
    private int numeroAula;
    private Tiempo horaInicio;
    private Tiempo horaFin;
    private FechaTiempo fechaHoraReserva; // Representa la fecha y hora en que se realizó la reserva
    private Fecha fechaActividad; // Fecha de la actividad
    private String descripcion;
    private String responsable;

    public ReservaDTO() {
    }

    public ReservaDTO(int id, int numeroAula, Tiempo horaInicio, Tiempo horaFin,
            FechaTiempo fechaHoraReserva, Fecha fechaActividad,
            String descripcion, String responsable) {
        this.id = id;
        this.numeroAula = numeroAula;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.fechaHoraReserva = fechaHoraReserva;
        this.fechaActividad = fechaActividad;
        this.descripcion = descripcion;
        this.responsable = responsable;
    }

    public ReservaDTO(int numeroAula, Tiempo horaInicio, Tiempo horaFin,
            Fecha fechaActividad,
            String descripcion, String responsable) {
        this.numeroAula = numeroAula;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.fechaHoraReserva = FechaTiempo.ahora();
        this.fechaActividad = fechaActividad;
        this.descripcion = descripcion;
        this.responsable = responsable;
    }

    public ReservaDTO(String numeroAula, Tiempo horaInicio, Tiempo horaFin, FechaTiempo fechaHoraReserva, String descripcion, String responsable) {
        this.numeroAula = Integer.parseInt(numeroAula);
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.fechaHoraReserva = fechaHoraReserva;
        this.descripcion = descripcion;
        this.responsable = responsable;
    }

    public ReservaDTO fromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int numeroAula = resultSet.getInt("numeroAula");

        // Extraer hora de inicio y fin como Time
        Tiempo horaInicio = Tiempo.fromSqlTime(resultSet.getTime("horaInicio"));
        Tiempo horaFin = Tiempo.fromSqlTime(resultSet.getTime("horaFin"));

        // Extraer fecha y hora de reserva como Timestamp
        Timestamp fechaHoraReservaTs = resultSet.getTimestamp("fechaHoraReserva");
        LocalDate fechaReservaLocalDate = fechaHoraReservaTs.toLocalDateTime().toLocalDate();
        LocalTime horaReservaLocalTime = fechaHoraReservaTs.toLocalDateTime().toLocalTime();

        // Crear objetos Fecha y Tiempo para la fechaHoraReserva
        Fecha fechaHoraReservaFecha = new Fecha(fechaReservaLocalDate.getDayOfMonth(),
                fechaReservaLocalDate.getMonthValue(),
                fechaReservaLocalDate.getYear());
        Tiempo tiempoReserva = new Tiempo(horaReservaLocalTime.getHour(),
                horaReservaLocalTime.getMinute());

        FechaTiempo fechaHoraReserva = new FechaTiempo(fechaHoraReservaFecha, tiempoReserva);

        Fecha fechaActividad = Fecha.fromSqlDate(resultSet.getDate("fechaActividad"));
        String descripcion = resultSet.getString("descripcion");
        String responsable = resultSet.getString("responsable");

        return new ReservaDTO(id, numeroAula, horaInicio, horaFin, fechaHoraReserva, fechaActividad, descripcion, responsable);
    }

    @Override
    public String toString() {
        return "ReservaDTO{" + "numeroAula=" + numeroAula + ", horaInicio=" + horaInicio.toString() + ", horaFin=" + horaFin.toString() + ", fechaHoraReserva=" + fechaHoraReserva + ", fechaActividad=" + fechaActividad + ", descripcion=" + descripcion + ", responsable=" + responsable + '}';
    }
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumeroAula() {
        return numeroAula;
    }

    public void setNumeroAula(int numeroAula) {
        this.numeroAula = numeroAula;
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

    public FechaTiempo getFechaHoraReserva() {
        return fechaHoraReserva;
    }

    public void setFechaHoraReserva(FechaTiempo fechaHoraReserva) {
        this.fechaHoraReserva = fechaHoraReserva;
    }

    public Fecha getFechaActividad() {
        return fechaActividad;
    }

    public void setFechaActividad(Fecha fechaActividad) {
        this.fechaActividad = fechaActividad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }
}
