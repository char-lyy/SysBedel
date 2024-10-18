package services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import paqueteDAO.ReservaDAO;
import paqueteDTO.ReservaDTO;
import utilidades.ConnectionManager;
import utilidades.Fecha;
import utilidades.Tiempo;

/**
 *
 * @author carlos
 */
public class ServicioReserva {

    private static final int DIA_INICIO_PRIMER_CUATRIMESTRE = 25;
    private static final int MES_INICIO_PRIMER_CUATRIMESTRE = 3;

    private static final int DIA_FIN_PRIMER_CUATRIMESTRE = 6;
    private static final int MES_FIN_PRIMER_CUATRIMESTRE = 7;

    private static final int DIA_INICIO_SEGUNDO_CUATRIMESTRE = 12;
    private static final int MES_INICIO_SEGUNDO_CUATRIMESTRE = 8;

    private static final int DIA_FIN_SEGUNDO_CUATRIMESTRE = 23;
    private static final int MES_FIN_SEGUNDO_CUATRIMESTRE = 11;

    public ServicioReserva() {
    }

    public boolean hayReservaEnRango(java.sql.Date fechaActividad, Tiempo horaInicio, Tiempo horaFin, String aula) throws SQLException {
        String sql = """
        SELECT COUNT(*) FROM Reserva WHERE numeroAula = ? AND fechaActividad = ? 
        AND (
            horaInicio < ? AND horaFin > ?
        )
        """;

        try (PreparedStatement preparedStatement = ConnectionManager.getConnection().prepareStatement(sql)) {
            preparedStatement.setInt(1, Integer.parseInt(aula));
            preparedStatement.setDate(2, fechaActividad);
            preparedStatement.setTime(3, java.sql.Time.valueOf(horaFin.getHoras() + ":" + horaFin.getMinutos() + ":00"));  // Fin de la nueva reserva
            preparedStatement.setTime(4, java.sql.Time.valueOf(horaInicio.getHoras() + ":" + horaInicio.getMinutos() + ":00"));  // Inicio de la nueva reserva

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0; // Retorna true si hay reservas que chocan
            }
        }
        return false; // Retorna false si no hay reservas que chocan
    }

    public String[][] obtenerReservasPorDia(Fecha fecha) {

        try {

            LocalTime HORA_INICIO = LocalTime.of(6, 0);
            LocalTime HORA_FIN = LocalTime.of(22, 0);
            int INTERVALO_MINUTOS = 30;

            // Obtener todas las aulas para determinar el número de columnas
            List<Integer> aulas = obtenerNumerosAula();
            int numAulas = aulas.size();
            int numIntervalos = (int) ((HORA_FIN.toSecondOfDay() - HORA_INICIO.toSecondOfDay()) / (INTERVALO_MINUTOS * 60)) + 1;

            // Inicializar la matriz con filas para cada intervalo y columnas para cada aula
            String[][] matrizReservas = new String[numIntervalos][numAulas];

            // Inicializar todas las celdas como "Disponible"
            for (int i = 0; i < numIntervalos; i++) {
                for (int j = 0; j < numAulas; j++) {
                    matrizReservas[i][j] = "";
                }
            }

            String query = """
                       SELECT numeroAula, horaInicio, horaFin, descripcion
                       FROM Reserva
                       WHERE fechaActividad = ?;
                       """;

            Connection conn = ConnectionManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, fecha.toSqlDate().toString());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int numeroAula = rs.getInt("numeroAula");
                Time horaInicio = rs.getTime("horaInicio");
                Time horaFin = rs.getTime("horaFin");
                String descripcion = rs.getString("descripcion");

                int col = aulas.indexOf(numeroAula); // Índice de la columna según el aula
                if (col == -1) {
                    continue;
                }

                // Convertir las horas de inicio y fin en LocalTime
                LocalTime inicioReserva = horaInicio.toLocalTime();
                LocalTime finReserva = horaFin.toLocalTime();

                // Llenar la matriz para cada intervalo de 30 minutos
                LocalTime horaActual = HORA_INICIO;
                for (int fila = 0; fila < numIntervalos; fila++) {
                    if (!horaActual.isBefore(inicioReserva) && horaActual.isBefore(finReserva)) {
                        matrizReservas[fila][col] = (descripcion == null || descripcion.trim().isEmpty()) ? "" : descripcion;
                    }
                    horaActual = horaActual.plusMinutes(INTERVALO_MINUTOS);
                }
            }

            return matrizReservas;

        } catch (SQLException ex) {
            Logger.getLogger(ServicioReserva.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private List<Integer> obtenerNumerosAula() {
        try {
            List<Integer> aulas = new ArrayList<>();
            String query = "SELECT numeroAula FROM Aula ORDER BY numeroAula";

            Connection conn = ConnectionManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                aulas.add(rs.getInt("numeroAula"));
            }

            return aulas;
        } catch (SQLException ex) {
            Logger.getLogger(ServicioReserva.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public void guardarReservaUnica(ReservaDTO reserva) throws SQLException {
        ReservaDAO reservaDAO = new ReservaDAO(ConnectionManager.getConnection());

        if (hayReservaEnRango(reserva.getFechaActividad().toSqlDate(), reserva.getHoraInicio(), reserva.getHoraFin(), String.valueOf(reserva.getNumeroAula()))) {
            throw new SQLException("No se puede realizar la reserva: ya existe una reserva en "
                    + "el aula " + reserva.getNumeroAula() + " el " + reserva.getFechaActividad()
                    + " entre " + reserva.getHoraInicio() + " y " + reserva.getHoraFin());
        }

        reservaDAO.insertarReserva(reserva);

    }

    public void guardarReservaCuatrimestral(ReservaDTO reserva, DayOfWeek diaSemana) throws SQLException {

        ReservaDAO nuevaReservaDAO = new ReservaDAO(ConnectionManager.getConnection());

        Fecha fechaInicioCuatrimestre = reserva.getFechaActividad() != null
                ? reserva.getFechaActividad()
                : obtenerFechaInicioCuatrimestre();

        Fecha fechaFinCuatrimestre = reserva.getFechaActividad() != null
                ? reserva.getFechaActividad()
                : obtenerFechaFinCuatrimestre();

        System.out.println("Fecha Fin: " + fechaFinCuatrimestre.toString());
        long cantidadSemanas = calcularSemanasEntreFechas(fechaInicioCuatrimestre.toLocalDate(), fechaFinCuatrimestre.toLocalDate());

        for (int i = 0; i < cantidadSemanas; i++) {
            java.util.Date fechaActividad = calcularFechaProxima(fechaInicioCuatrimestre.toSqlDate(), diaSemana, i * 7);
            reserva.setFechaActividad(Fecha.fromUtilDate(fechaActividad));

            if (hayReservaEnRango(Fecha.fromUtilDate(fechaActividad).toSqlDate(), reserva.getHoraInicio(), reserva.getHoraFin(), String.valueOf(reserva.getNumeroAula()))) {
                throw new SQLException("No se puede realizar la reserva: ya existe una reserva en "
                        + "el aula " + reserva.getNumeroAula() + " el " + fechaActividad
                        + " entre " + reserva.getHoraInicio() + " y " + reserva.getHoraFin());
            } else {
                reserva.setFechaActividad(Fecha.fromUtilDate(fechaActividad));

                nuevaReservaDAO.insertarReserva(reserva);
            }

        }
    }

    public void guardarReservaAnual(ReservaDTO reserva, DayOfWeek diaSemana) throws SQLException {
        guardarReservasPrimerCuatrimestre(reserva, diaSemana);
        guardarReservasSegundoCuatrimestre(reserva, diaSemana);
    }

    public void guardarReservasPrimerCuatrimestre(ReservaDTO reserva, DayOfWeek diaSemana) throws SQLException {
        ReservaDAO nuevaReservaDAO = new ReservaDAO(ConnectionManager.getConnection());

        LocalDate now = LocalDate.now();

        LocalDate localDateInicio = LocalDate.of(now.getYear(), MES_INICIO_PRIMER_CUATRIMESTRE, DIA_INICIO_PRIMER_CUATRIMESTRE);
        LocalDate localDateFin = LocalDate.of(now.getYear(), MES_FIN_PRIMER_CUATRIMESTRE, DIA_FIN_PRIMER_CUATRIMESTRE);
        long cantidadSemanas = calcularSemanasEntreFechas(localDateInicio, localDateFin);

        Fecha fechaInicioCuatrimestre = Fecha.fromLocalDate(localDateInicio);

        for (int i = 0; i < cantidadSemanas; i++) {
            java.util.Date fechaActividad = calcularFechaProxima(fechaInicioCuatrimestre.toSqlDate(), diaSemana, i * 7);
            reserva.setFechaActividad(Fecha.fromUtilDate(fechaActividad));

            if (hayReservaEnRango(Fecha.fromUtilDate(fechaActividad).toSqlDate(), reserva.getHoraInicio(), reserva.getHoraFin(), String.valueOf(reserva.getNumeroAula()))) {
                throw new SQLException("No se puede realizar la reserva: ya existe una reserva en "
                        + "el aula " + reserva.getNumeroAula() + " el " + fechaActividad
                        + " entre " + reserva.getHoraInicio() + " y " + reserva.getHoraFin());
            } else {
                reserva.setFechaActividad(Fecha.fromUtilDate(fechaActividad));

                nuevaReservaDAO.insertarReserva(reserva);
            }

        }
    }

    public void guardarReservasSegundoCuatrimestre(ReservaDTO reserva, DayOfWeek diaSemana) throws SQLException {
        ReservaDAO nuevaReservaDAO = new ReservaDAO(ConnectionManager.getConnection());

        LocalDate now = LocalDate.now();

        LocalDate localDateInicio = LocalDate.of(now.getYear(), MES_INICIO_SEGUNDO_CUATRIMESTRE, DIA_INICIO_SEGUNDO_CUATRIMESTRE);
        LocalDate localDateFin = LocalDate.of(now.getYear(), MES_FIN_SEGUNDO_CUATRIMESTRE, DIA_FIN_SEGUNDO_CUATRIMESTRE);
        long cantidadSemanas = calcularSemanasEntreFechas(localDateInicio, localDateFin);

        Fecha fechaInicioCuatrimestre = Fecha.fromLocalDate(localDateInicio);

        for (int i = 0; i < cantidadSemanas; i++) {
            java.util.Date fechaActividad = calcularFechaProxima(fechaInicioCuatrimestre.toSqlDate(), diaSemana, i * 7);
            reserva.setFechaActividad(Fecha.fromUtilDate(fechaActividad));

            if (hayReservaEnRango(Fecha.fromUtilDate(fechaActividad).toSqlDate(), reserva.getHoraInicio(), reserva.getHoraFin(), String.valueOf(reserva.getNumeroAula()))) {
                throw new SQLException("No se puede realizar la reserva: ya existe una reserva en "
                        + "el aula " + reserva.getNumeroAula() + " el " + fechaActividad
                        + " entre " + reserva.getHoraInicio() + " y " + reserva.getHoraFin());
            } else {
                reserva.setFechaActividad(Fecha.fromUtilDate(fechaActividad));

                nuevaReservaDAO.insertarReserva(reserva);
            }

        }
    }

    /**
     * Este método obtiene la fecha de inicio del cuatrimestre y la devuelve
     * como una instancia de Fecha.
     *
     * @return Una instancia de Fecha que representa la fecha de inicio del
     * cuatrimestre.
     */
    private Fecha obtenerFechaInicioCuatrimestre() {
        LocalDate hoy = LocalDate.now();

        // Calcular el inicio del primer cuatrimestre
        LocalDate inicioPrimerCuatrimestre = LocalDate.of(hoy.getYear(), MES_INICIO_PRIMER_CUATRIMESTRE, DIA_INICIO_PRIMER_CUATRIMESTRE)
                .with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY)).plusDays(7);

        // Calcular el inicio del segundo cuatrimestre
        LocalDate inicioSegundoCuatrimestre = LocalDate.of(hoy.getYear(), MES_INICIO_SEGUNDO_CUATRIMESTRE, DIA_INICIO_SEGUNDO_CUATRIMESTRE)
                .with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY)).plusDays(7);

        // Determinar cuál de los dos cuatrimestres está en curso y devolver la fecha correspondiente
        LocalDate fechaInicio = hoy.isBefore(inicioSegundoCuatrimestre) ? inicioPrimerCuatrimestre : inicioSegundoCuatrimestre;

        // Crear y devolver la instancia de Fecha
        return new Fecha(fechaInicio.getDayOfMonth(), fechaInicio.getMonthValue(), fechaInicio.getYear());
    }

    /**
     * Este método obtiene la fecha de fin del cuatrimestre y la devuelve como
     * una instancia de Fecha.
     *
     * @return Una instancia de Fecha que representa la fecha de fin del
     * cuatrimestre.
     */
    private Fecha obtenerFechaFinCuatrimestre() {
        LocalDate hoy = LocalDate.now();

        // Calcular la fecha de fin del primer cuatrimestre
        LocalDate finPrimerCuatrimestre = LocalDate.of(hoy.getYear(), MES_FIN_PRIMER_CUATRIMESTRE, DIA_FIN_PRIMER_CUATRIMESTRE);

        // Calcular la fecha de fin del segundo cuatrimestre
        LocalDate finSegundoCuatrimestre = LocalDate.of(hoy.getYear(), MES_FIN_SEGUNDO_CUATRIMESTRE, DIA_FIN_SEGUNDO_CUATRIMESTRE);

        // Si hoy es antes de la fecha de fin del primer cuatrimestre, se devuelve el fin del primer cuatrimestre
        // Si hoy es entre el fin del primer cuatrimestre y el fin del segundo cuatrimestre, se devuelve el fin del segundo cuatrimestre
        // Si hoy es después del fin del segundo cuatrimestre, también se puede devolver el fin del segundo cuatrimestre
        LocalDate fechaFin;
        if (hoy.isBefore(finPrimerCuatrimestre)) {
            // Antes del primer cuatrimestre
            fechaFin = finPrimerCuatrimestre;
        } else if (hoy.isBefore(finSegundoCuatrimestre)) {
            // Entre el fin del primer cuatrimestre y el fin del segundo cuatrimestre
            fechaFin = finSegundoCuatrimestre;
        } else {
            // Después del fin del segundo cuatrimestre
            fechaFin = finSegundoCuatrimestre;
        }

        // Crear y devolver la instancia de Fecha
        return new Fecha(fechaFin.getDayOfMonth(), fechaFin.getMonthValue(), fechaFin.getYear());
    }

    /**
     * Método para calcular la próxima fecha de una reserva semanal
     *
     * @param fechaInicio
     * @param diaSeleccionado
     * @param diasIncremento
     * @return
     */
    private Date calcularFechaProxima(java.sql.Date fechaInicio, DayOfWeek diaSeleccionado, int diasIncremento) {
        LocalDate fecha = fechaInicio.toLocalDate().plusDays(diasIncremento);
        return Date.valueOf(fecha.with(TemporalAdjusters.nextOrSame(diaSeleccionado)));
    }

    public static long calcularSemanasEntreFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        return ChronoUnit.WEEKS.between(fechaInicio, fechaFin);
    }

}
