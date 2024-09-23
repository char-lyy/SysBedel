package paqueteDTO;

import java.sql.Time;
import java.time.LocalTime;
import utilidades.GestorEntradaConsola;

public class TiempoDTO implements Comparable {

    private int horas;
    private int minutos;

    public TiempoDTO() {
    }

    public TiempoDTO(int horas, int minutos) {
        if (horas < 0 || horas > 23 || minutos < 0 || minutos > 59) {
            throw new IllegalArgumentException("Hora o minutos inválidos");
        }
        this.horas = horas;
        this.minutos = minutos;
    }

    public int getHoras() {
        return horas;
    }

    public void setHoras(int horas) {
        if (horas < 0 || horas > 23) {
            throw new IllegalArgumentException("Horas inválidas");
        }
        this.horas = horas;
    }

    public int getMinutos() {
        return minutos;
    }

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

    @Override
    public int compareTo(Object o) {
        TiempoDTO horaDTO = (TiempoDTO) o;

        if (horas != horaDTO.horas) {
            return horas - horaDTO.horas;
        }

        if (minutos != horaDTO.horas) {
            return minutos - horaDTO.minutos;
        }

        return 0;
    }

    public void cargarDatos() {
        horas = cargarHoras();
        minutos = cargarMinutos();
    }

    private int cargarHoras() {
        int horasEntrantes;
        boolean esValido;

        do {
            System.out.print("Horas (0-24): ");
            horasEntrantes = GestorEntradaConsola.leerEntero();
            esValido = horasEntrantes >= 0 && horasEntrantes <= 24;
        } while (!esValido);
        return horasEntrantes;
    }

    private int cargarMinutos() {
        int minutosEntrantes;
        boolean esValido;
        do {
            System.out.print("Minutos (0-59): ");
            minutosEntrantes = GestorEntradaConsola.leerEntero();
            esValido = minutosEntrantes >= 0 && minutosEntrantes <= 59;
        } while (!esValido);
        return minutosEntrantes;
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
    public static TiempoDTO fromSqlTime(Time sqlTime) {
        // Convertimos el java.sql.Time a LocalTime
        LocalTime localTime = sqlTime.toLocalTime();
        // Creamos una instancia de TiempoDTO usando las horas y minutos de LocalTime
        return new TiempoDTO(localTime.getHour(), localTime.getMinute());
    }
}
