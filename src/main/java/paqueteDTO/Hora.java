package paqueteDTO;

public class Hora {
    private int horas;
    private int minutos;

    // Constructor
    public Hora(int horas, int minutos) {
        if (horas < 0 || horas > 23 || minutos < 0 || minutos > 59) {
            throw new IllegalArgumentException("Hora o minutos inválidos");
        }
        this.horas = horas;
        this.minutos = minutos;
    }

    // Getter para horas
    public int getHoras() {
        return horas;
    }

    // Setter para horas
    public void setHoras(int horas) {
        if (horas < 0 || horas > 23) {
            throw new IllegalArgumentException("Horas inválidas");
        }
        this.horas = horas;
    }

    // Getter para minutos
    public int getMinutos() {
        return minutos;
    }

    // Setter para minutos
    public void setMinutos(int minutos) {
        if (minutos < 0 || minutos > 59) {
            throw new IllegalArgumentException("Minutos inválidos");
        }
        this.minutos = minutos;
    }

    // Método para representar la hora en formato HH:MM
    @Override
    public String toString() {
        return String.format("%02d:%02d", horas, minutos);
    }

    // Método para comparar si una hora es anterior a otra
    public boolean esAnterior(Hora otra) {
        if (this.horas < otra.horas) {
            return true;
        } else if (this.horas == otra.horas) {
            return this.minutos < otra.minutos;
        }
        return false;
    }
}

