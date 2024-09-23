package paqueteDTO;

import utilidades.GestorEntradaConsola;

public class ActividadDTO {

    protected int idActividad;
    protected FechaDTO fechaInicioActividad;
    protected FechaDTO fechaFinActividad;
    protected PeriodoActividad periodoActividad;
    protected TipoActividad tipoActividad;

    public enum PeriodoActividad {
        CUATRIMESTRAL,
        ANUAL,
        TEMPORAL;

        public static PeriodoActividad cargarPeriodoActividad() {
            int opcion;

            do {
                System.out.println("Seleccione el periodo de actividad:");
                System.out.println("1. CUATRIMESTRAL");
                System.out.println("2. ANUAL");
                System.out.println("3. TEMPORAL");

                opcion = GestorEntradaConsola.leerEntero();
                switch (opcion) {
                    case 1 -> {
                        return CUATRIMESTRAL;
                    }
                    case 2 -> {
                        return ANUAL;
                    }
                    case 3 -> {
                        return TEMPORAL;
                    }
                    default ->
                        System.out.println("Opción inválida. Intente nuevamente.");
                }
            } while (true);
        }
    }

    public enum TipoActividad {
        CLASE,
        EVENTO;

        public static TipoActividad cargarTipoActividad() {
            int opcion;

            do {
                System.out.println("Seleccione el tipo de actividad:");
                System.out.println("1. CLASE");
                System.out.println("2. EVENTO");

                opcion = GestorEntradaConsola.leerEntero();
                switch (opcion) {
                    case 1 -> {
                        return CLASE;
                    }
                    case 2 -> {
                        return EVENTO;
                    }
                    default ->
                        System.out.println("Opción inválida. Intente nuevamente.");
                }
            } while (true);
        }
    }

    public ActividadDTO(int idActividad, FechaDTO fechaInicioActividad, FechaDTO fechaFinActividad, PeriodoActividad periodoActividad, TipoActividad tipoActividad) {
        this.idActividad = idActividad;
        this.fechaInicioActividad = fechaInicioActividad;
        this.fechaFinActividad = fechaFinActividad;
        this.periodoActividad = periodoActividad;
        this.tipoActividad = tipoActividad;
    }

    public int getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
    }

    public FechaDTO getFechaInicioActividad() {
        return fechaInicioActividad;
    }

    public void setFechaInicioActividad(FechaDTO fechaInicioActividad) {
        this.fechaInicioActividad = fechaInicioActividad;
    }

    public FechaDTO getFechaFinActividad() {
        return fechaFinActividad;
    }

    public void setFechaFinActividad(FechaDTO fechaFinActividad) {
        this.fechaFinActividad = fechaFinActividad;
    }

    public PeriodoActividad getPeriodoActividad() {
        return periodoActividad;
    }

    public void setPeriodoActividad(PeriodoActividad periodoActividad) {
        this.periodoActividad = periodoActividad;
    }

    public TipoActividad getTipoActividad() {
        return tipoActividad;
    }

    public void setTipoActividad(TipoActividad tipoActividad) {
        this.tipoActividad = tipoActividad;
    }

}
