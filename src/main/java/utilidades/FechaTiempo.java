package utilidades;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class FechaTiempo implements Comparable<FechaTiempo> {

    private Fecha fecha;
    private Tiempo tiempo;

    public FechaTiempo(Fecha fecha, Tiempo tiempo) {
        this.fecha = fecha;
        this.tiempo = tiempo;
    }

    public Fecha getFecha() {
        return fecha;
    }

    public void setFecha(Fecha fecha) {
        this.fecha = fecha;
    }

    public Tiempo getTiempo() {
        return tiempo;
    }

    public void setTiempo(Tiempo tiempo) {
        this.tiempo = tiempo;
    }

    @Override
    public int compareTo(FechaTiempo otro) {
        int fechaComparison = this.fecha.compareTo(otro.getFecha());
        if (fechaComparison != 0) {
            return fechaComparison;
        }
        return this.tiempo.compareTo(otro.getTiempo());
    }

    /**
     * Convierte la instancia de FechaTiempo a un objeto java.sql.Timestamp que
     * puede ser almacenado en una base de datos SQL.
     *
     * @return Un objeto java.sql.Timestamp que representa la fecha y hora.
     */
    public Timestamp toSqlDateTime() {
        LocalDateTime localDateTime = LocalDateTime.of(
                this.fecha.getAño(),
                this.fecha.getMes(),
                this.fecha.getDia(),
                this.tiempo.getHoras(),
                this.tiempo.getMinutos()
        );
        return Timestamp.valueOf(localDateTime);
    }

    /**
     * Convierte un objeto java.sql.Timestamp a una instancia de FechaTiempo.
     *
     * @param sqlTimestamp Un objeto java.sql.Timestamp.
     * @return Una instancia de FechaTiempo que representa la misma fecha y
     * hora.
     */
    public static FechaTiempo fromSqlDateTime(Timestamp sqlTimestamp) {
        LocalDateTime localDateTime = sqlTimestamp.toLocalDateTime();
        Fecha fecha = new Fecha(localDateTime.getDayOfMonth(), localDateTime.getMonthValue(), localDateTime.getYear());
        Tiempo tiempo = new Tiempo(localDateTime.getHour(), localDateTime.getMinute());
        return new FechaTiempo(fecha, tiempo);
    }

    /**
     * Convierte la instancia de FechaTiempo a un objeto
     * java.time.LocalDateTime.
     *
     * @return Un objeto LocalDateTime que representa la fecha y hora.
     */
    public LocalDateTime toLocalDateTime() {
        return LocalDateTime.of(
                this.fecha.getAño(),
                this.fecha.getMes(),
                this.fecha.getDia(),
                this.tiempo.getHoras(),
                this.tiempo.getMinutos(),
                0
        );
    }

    /**
     * Crea una instancia de FechaTiempo a partir de un objeto
     * java.time.LocalDateTime.
     *
     * @param localDateTime Un objeto LocalDateTime.
     * @return Una instancia de FechaTiempo que representa la misma fecha y
     * hora.
     */
    public static FechaTiempo fromLocalDateTime(LocalDateTime localDateTime) {
        Fecha fecha = new Fecha(localDateTime.getDayOfMonth(), localDateTime.getMonthValue(), localDateTime.getYear());
        Tiempo tiempo = new Tiempo(localDateTime.getHour(), localDateTime.getMinute());
        return new FechaTiempo(fecha, tiempo);
    }

    /**
     * Devuelve una instancia de FechaTiempo con la fecha y hora actuales del
     * sistema.
     *
     * @return Una instancia de FechaTiempo con la fecha y hora actuales.
     */
    public static FechaTiempo ahora() {
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();

        Fecha fechaActual = new Fecha(localDate.getDayOfMonth(), localDate.getMonthValue(), localDate.getYear());
        Tiempo tiempoActual = new Tiempo(localTime.getHour(), localTime.getMinute());

        return new FechaTiempo(fechaActual, tiempoActual);
    }
}
