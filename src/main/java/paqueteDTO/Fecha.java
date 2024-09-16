package paqueteDTO;

public class Fecha {

    private int dia;
    private int mes;
    private int año;

    // Arreglo con los días de cada mes para validación
    private static final int[] DIAS_POR_MES = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    // Constructor vacío (opcional, para inicialización predeterminada)
    public Fecha() {
        this.dia = 1;
        this.mes = 1;
        this.año = 1970;
    }

    // Constructor con parámetros
    public Fecha(int dia, int mes, int año) {
        if (esFechaValida(dia, mes, año)) {
            this.dia = dia;
            this.mes = mes;
            this.año = año;
        } else {
            throw new IllegalArgumentException("Fecha inválida.");
        }
    }

    // Método para validar la fecha
    public static boolean esFechaValida(int dia, int mes, int año) {
        if (mes < 1 || mes > 12) {
            return false;
        }
        int diasMaximos = DIAS_POR_MES[mes - 1];
        
        // Ajustar febrero para años bisiestos
        if (mes == 2 && esBisiesto(año)) {
            diasMaximos = 29;
        }

        return dia >= 1 && dia <= diasMaximos;
    }

    // Método para comprobar si el año es bisiesto
    public static boolean esBisiesto(int año) {
        return (año % 4 == 0 && año % 100 != 0) || (año % 400 == 0);
    }

    // Getters y Setters
    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        if (esFechaValida(dia, this.mes, this.año)) {
            this.dia = dia;
        } else {
            throw new IllegalArgumentException("Día inválido.");
        }
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        if (esFechaValida(this.dia, mes, this.año)) {
            this.mes = mes;
        } else {
            throw new IllegalArgumentException("Mes inválido.");
        }
    }

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        if (esFechaValida(this.dia, this.mes, año)) {
            this.año = año;
        } else {
            throw new IllegalArgumentException("Año inválido.");
        }
    }

    // Método para comparar dos fechas
    public int comparar(Fecha otraFecha) {
        if (this.año != otraFecha.año) {
            return this.año - otraFecha.año;
        } else if (this.mes != otraFecha.mes) {
            return this.mes - otraFecha.mes;
        } else {
            return this.dia - otraFecha.dia;
        }
    }

    // Método para sumar días a la fecha
    public void sumarDias(int dias) {
        while (dias > 0) {
            int diasRestantesMes = obtenerDiasRestantesDelMes();
            if (dias > diasRestantesMes) {
                dias -= diasRestantesMes + 1;
                avanzarAlProximoMes();
            } else {
                this.dia += dias;
                dias = 0;
            }
        }
    }

    // Obtener los días restantes en el mes actual
    private int obtenerDiasRestantesDelMes() {
        int diasMaximos = DIAS_POR_MES[this.mes - 1];
        if (this.mes == 2 && esBisiesto(this.año)) {
            diasMaximos = 29;
        }
        return diasMaximos - this.dia;
    }

    // Avanzar al próximo mes (maneja cambio de año)
    private void avanzarAlProximoMes() {
        if (this.mes == 12) {
            this.mes = 1;
            this.año++;
        } else {
            this.mes++;
        }
        this.dia = 1;
    }

    // Método para formatear la fecha como cadena
    @Override
    public String toString() {
        return String.format("%02d/%02d/%d", dia, mes, año);
    }
}
