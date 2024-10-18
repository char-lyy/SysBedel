package utilidades;

import java.sql.Time;
import java.time.LocalTime;

public class Tiempo implements Comparable {

    private int horas;
    private int minutos;

    public Tiempo() {
    }
    
    /**
     * Este metodo verifica que las horas sean validas.
     * @param horas
     * @param minutos 
     */

    public Tiempo(int horas, int minutos) {
        if (horas < 0 || horas > 23 || minutos < 0 || minutos > 59) {
            throw new IllegalArgumentException("Hora o minutos inválidos");
        }
        this.horas = horas;
        this.minutos = minutos;
    }

    public int getHoras() {
        return horas;
    }
    /**
     * Se verifica que las horas sean validas.
     * @param horas 
     */

    public void setHoras(int horas) {
        if (horas < 0 || horas > 23) {
            throw new IllegalArgumentException("Horas inválidas");
        }
        this.horas = horas;
    }

    public int getMinutos() {
        return minutos;
    }
    /**
     * Se verifica que los minutos sean validos.
     * @param minutos 
     */

    public void setMinutos(int minutos) {
        if (minutos < 0 || minutos > 59) {
            throw new IllegalArgumentException("Minutos inválidos");
        }
        this.minutos = minutos;
    }

    @Override
    public String toString() {
        LocalTime localTime = LocalTime.of(horas, minutos);
        return localTime.toString();
    }
    /**
     * Este metodo valida las horas.
     * @param o
     * @return 0
     */

    @Override
    public int compareTo(Object o) {
        Tiempo horaDTO = (Tiempo) o;

        if (horas != horaDTO.horas) {
            return horas - horaDTO.horas;
        }

        if (minutos != horaDTO.horas) {
            return minutos - horaDTO.minutos;
        }

        return 0;
    }

    /**
     * Convierte la instancia de TiempoDTO a un objeto java.sql.Time.
     *
     * @return Un objeto java.sql.Time que representa la misma hora.
     */
    public Time toSqlTime() {
        LocalTime localTime = LocalTime.of(this.horas, this.minutos);
        return Time.valueOf(localTime);
    }

    /**
     * Convierte una instancia de java.sql.Time en una instancia de TiempoDTO.
     *
     * @param sqlTime Un objeto java.sql.Time.
     * @return Una instancia de TiempoDTO que representa la misma hora.
     */
    public static Tiempo fromSqlTime(Time sqlTime) {
        // Convertimos el java.sql.Time a LocalTime
        LocalTime localTime = sqlTime.toLocalTime();
        // Creamos una instancia de TiempoDTO usando las horas y minutos de LocalTime
        return new Tiempo(localTime.getHour(), localTime.getMinute());
    }
    
    
}
