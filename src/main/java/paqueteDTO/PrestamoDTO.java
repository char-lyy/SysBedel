package paqueteDTO;

public class PrestamoDTO {
    private String legajoDocente;
    private String codigoRecurso;
    private boolean estaPrestado;
    private Fecha fechaPrestamo;
    private Fecha horaPrestamo;
    private String observacionPrestamo;

    // Constructor vacío
    public PrestamoDTO() {}

    // Constructor con parámetros
    public PrestamoDTO(String legajoDocente, String codigoRecurso, boolean estaPrestado, 
                       Fecha fechaPrestamo, Fecha horaPrestamo, String observacionPrestamo) {
        this.legajoDocente = legajoDocente;
        this.codigoRecurso = codigoRecurso;
        this.estaPrestado = estaPrestado;
        this.fechaPrestamo = fechaPrestamo;
        this.horaPrestamo = horaPrestamo;
        this.observacionPrestamo = observacionPrestamo;
    }

    // Getters y Setters
    public String getLegajoDocente() {
        return legajoDocente;
    }

    public void setLegajoDocente(String legajoDocente) {
        this.legajoDocente = legajoDocente;
    }

    public String getCodigoRecurso() {
        return codigoRecurso;
    }

    public void setCodigoRecurso(String codigoRecurso) {
        this.codigoRecurso = codigoRecurso;
    }

    public boolean isEstaPrestado() {
        return estaPrestado;
    }

    public void setEstaPrestado(boolean estaPrestado) {
        this.estaPrestado = estaPrestado;
    }

    public Fecha getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(Fecha fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public Fecha getHoraPrestamo() {
        return horaPrestamo;
    }

    public void setHoraPrestamo(Fecha horaPrestamo) {
        this.horaPrestamo = horaPrestamo;
    }

    public String getObservacionPrestamo() {
        return observacionPrestamo;
    }

    public void setObservacionPrestamo(String observacionPrestamo) {
        this.observacionPrestamo = observacionPrestamo;
    }
}

