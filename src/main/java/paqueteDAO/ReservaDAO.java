package paqueteDAO;

import java.awt.BorderLayout;
import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import paqueteDTO.NuevaReservaDTO;
import utilidades.Fecha;
import paqueteDTO.ReservaDTO;
import utilidades.Tiempo;

public class ReservaDAO {

    private Connection connection;

    /**
     * Constructor de la clase que permite la conexion con la Base de Datos.
     *
     * @param connection
     */
    public ReservaDAO(Connection connection) {
        this.connection = connection;
    }

    public void mostrarTablaAulasDisponibles(Date fecha, Tiempo horaInicio, Tiempo horaFin) {
        try {
            List<Map<String, Object>> aulasDisponibles = obtenerTablaAulasDisponibles(fecha, horaInicio, horaFin);

            // Crear el marco de la ventana
            JFrame frame = new JFrame("Aulas Disponibles");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(400, 300);

            // Crear el modelo de tabla
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Número de Aula");
            model.addColumn("Capacidad");

            // Verificar si se encontraron aulas
            if (aulasDisponibles.isEmpty()) {
                // Mostrar mensaje si no se encontraron aulas
                JOptionPane.showMessageDialog(frame, "No se encontraron aulas disponibles.", "Resultado", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Rellenar el modelo de la tabla con los datos
                for (Map<String, Object> aula : aulasDisponibles) {
                    model.addRow(new Object[]{
                        aula.get("numeroAula"),
                        aula.get("capacidadAula")
                    });
                }

                // Crear el JTable con el modelo
                JTable table = new JTable(model);
                table.setFillsViewportHeight(true);

                // Agregar la tabla a un JScrollPane para permitir el desplazamiento
                JScrollPane scrollPane = new JScrollPane(table);
                frame.add(scrollPane, BorderLayout.CENTER);
            }

            // Hacer visible la ventana
            frame.setVisible(true);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al obtener aulas disponibles: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Devuelve una tabla con las aulas disponibles.
     *
     * @param fecha La fecha de la actividad.
     * @param horaInicio La hora de inicio.
     * @param horaFin La hora de fin.
     * @return Tabla con las aulas disponibles, mostrando número de aula y
     * capacidad.
     * @throws SQLException Si ocurre algún error en la consulta.
     */
    public List<Map<String, Object>> obtenerTablaAulasDisponibles(java.sql.Date fecha, Tiempo horaInicio, Tiempo horaFin) throws SQLException {

        List<Map<String, Object>> tablaResultado;

        // Consulta SQL para obtener aulas no reservadas en el rango de tiempo especificado
        String query = """
        SELECT numeroAula, capacidadAula 
        FROM Aula 
        WHERE numeroAula NOT IN (
            SELECT numeroAula FROM Reserva 
            WHERE fechaActividad = ? 
            AND ((horaInicio < ? AND horaFin > ?) 
                OR (horaInicio < ? AND horaFin > ?))
        )
        """;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDate(1, fecha);
            stmt.setTime(2, horaFin.toSqlTime());
            stmt.setTime(3, horaInicio.toSqlTime());
            stmt.setTime(4, horaInicio.toSqlTime());
            stmt.setTime(5, horaFin.toSqlTime());

            // Ejecuta la consulta y transforma el ResultSet en una lista de mapas con DbUtils
            ResultSetHandler<List<Map<String, Object>>> handler = new MapListHandler();
            tablaResultado = handler.handle(stmt.executeQuery());
        }

        return tablaResultado;
    }

    /**
     * Devuelve una lista con las aulas disponibles
     *
     * @param fecha
     * @param horaInicio
     * @param horaFin
     * @return
     * @throws SQLException
     */
    public List<Integer> obtenerAulasDisponibles(java.sql.Date fecha, Tiempo horaInicio, Tiempo horaFin) throws SQLException {
        List<Integer> aulasDisponibles = new ArrayList<>();

        String queryAulas = "SELECT numeroAula FROM Aula";
        try (PreparedStatement stmtAulas = connection.prepareStatement(queryAulas); ResultSet rsAulas = stmtAulas.executeQuery()) {

            // Almacenar todas las aulas en una lista temporal
            List<Integer> todasLasAulas = new ArrayList<>();
            while (rsAulas.next()) {
                todasLasAulas.add(rsAulas.getInt("numeroAula"));
            }

            // 2. Verificar qué aulas ya están reservadas en la fecha y horario dada
            String queryReservas = "SELECT numeroAula FROM Reserva WHERE fechaActividad = ? "
                    + "AND ((horaInicio < ? AND horaFin > ?) OR (horaInicio < ? AND horaFin > ?))";
            try (PreparedStatement stmtReservas = connection.prepareStatement(queryReservas)) {
                stmtReservas.setDate(1, fecha);
                stmtReservas.setTime(2, horaFin.toSqlTime());
                stmtReservas.setTime(3, horaInicio.toSqlTime());
                stmtReservas.setTime(4, horaInicio.toSqlTime());
                stmtReservas.setTime(5, horaFin.toSqlTime());
                ResultSet rsReservas = stmtReservas.executeQuery();

                // Almacenar las aulas que ya están reservadas en la fecha y hora
                List<Integer> aulasReservadas = new ArrayList<>();
                while (rsReservas.next()) {
                    aulasReservadas.add(rsReservas.getInt("numeroAula"));
                }

                // 3. Retornar las aulas que no estén reservadas
                for (Integer aula : todasLasAulas) {
                    if (!aulasReservadas.contains(aula)) {
                        aulasDisponibles.add(aula);
                    }
                }
            }
        }
        return aulasDisponibles;
    }

    /**
     * Método para insertar una nueva reserva solo si hay aulas disponibles y no
     * hay choque de horarios para una misma aula
     *
     * @param reserva
     * @return verdadero si se inserto adecuadamente un aula.
     * @throws SQLException
     */
    public boolean insertarReserva(ReservaDTO reserva) throws SQLException {

        // 1. Obtener aulas disponibles para la fecha y hora de la reserva
        List<Integer> aulasDisponibles = obtenerAulasDisponibles(
                reserva.getFechaActividad().toSqlDate(),
                reserva.getHoraInicio(),
                reserva.getHoraFin()
        );

        if (!aulasDisponibles.contains(reserva.getNumeroAula())) {
            System.out.println("No hay aulas disponibles o el aula solicitada ya está reservada.");
            return false;
        }

//        if (hayConflictoDeHorario(reserva)) {
        if (hayReservaEnRango(reserva.getFechaActividad().toSqlDate(),
                reserva.getHoraInicio(), reserva.getHoraFin(), String.valueOf(reserva.getNumeroAula()))) {
            return false;
//            throw new SQLException("No se puede realizar la reserva: ya existe una reserva en "
//                    + "el aula " + reserva.getNumeroAula() + " el " + reserva.getFechaActividad()
//                    + " entre " + reserva.getHoraInicio() + " y " + reserva.getHoraFin());
//            JOptionPane.showMessageDialog(null, "", "Error", JOptionPane.ERROR_MESSAGE);
//            return false;

        }

//        }
        String query = "INSERT INTO Reserva (numeroAula, horaInicio, horaFin, fechaReserva, fechaActividad, descripcion, responsable) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
//            stmt.setInt(1, reserva.getIdReserva());
            stmt.setInt(1, 1);
            stmt.setInt(2, reserva.getNumeroAula());
            stmt.setBoolean(3, reserva.isConfirmada());
            stmt.setTime(4, reserva.getHoraInicio().toSqlTime());
            stmt.setTime(5, reserva.getHoraFin().toSqlTime());
            stmt.setDate(6, reserva.getFechaReserva().toSqlDate());
            stmt.setDate(7, reserva.getFechaActividad().toSqlDate());
            stmt.setString(8, reserva.getDescripcion());
            
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        }
    }

    /**
     * Se guarda la reserva en base a los horarios establecidos, en caso de que
     * no haya ningun conflicto
     *
     * @param codigoActividad
     * @param aula
     * @param horaInicio
     * @param horaFin
     * @param diaSemana
     * @param fechaActividadDTO
     * @param descripcion
     * @return
     * @throws SQLException
     */

    public boolean guardarReserva(String aula, Tiempo horaInicio, Tiempo horaFin, String diaSemana, Fecha fechaActividadDTO, String descripcion, String responsable) throws SQLException {

        // Verificar si hay un conflicto de horario antes de proceder
        if (hayReservaEnRango(fechaActividadDTO.toSqlDate(), horaInicio, horaFin, aula)) {
            System.out.println("Conflicto de horario: la reserva no se puede realizar.");
            return false; // Salir del método si hay un conflicto
        }

        // Consulta SQL para insertar la reserva
        String sql = "INSERT INTO Reserva (numeroAula, horaInicio, horaFin, fechaReserva, fechaActividad, descripcion, responsable) VALUES (?, ?, ?, ?, ?, ?, ?)";

        // Fecha de la reserva (fecha actual)
        Date fechaReserva = new Date(System.currentTimeMillis());

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            // Configuración de parámetros para la consulta
            preparedStatement.setInt(1, Integer.parseInt(aula)); // Número de aula como entero

            // Establecer las horas de inicio y fin de la reserva
            preparedStatement.setTime(2, java.sql.Time.valueOf(horaInicio.getHoras() + ":" + horaInicio.getMinutos() + ":00"));
            preparedStatement.setTime(3, java.sql.Time.valueOf(horaFin.getHoras() + ":" + horaFin.getMinutos() + ":00"));

            // Establecer la fecha de la reserva (fecha actual) y la fecha de la actividad
            preparedStatement.setDate(4, fechaReserva);
            preparedStatement.setDate(5, fechaActividadDTO.toSqlDate());

            // Descripción y responsable de la reserva
            preparedStatement.setString(6, descripcion);
            preparedStatement.setString(7, responsable);

            // Ejecutar la actualización en la base de datos
            preparedStatement.executeUpdate();
            return true; // Retornar verdadero si la inserción fue exitosa
        }
    }


    /**
     * Método para verificar si una aula existe en la base de datos
     *
     * @param numeroAula
     * @return verdadero si existe el aula.
     * @throws SQLException
     */
    public boolean existeAula(int numeroAula) throws SQLException {
        String query = "SELECT COUNT(*) FROM Aula WHERE numeroAula = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, numeroAula);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    /**
     * Método para verificar si una actividad existe en la base de datos
     *
     * @param idActividad
     * @return verdadero si existe una actividad.
     * @throws SQLException
     */
    public boolean existeActividad(int idActividad) throws SQLException {
        String query = "SELECT COUNT(*) FROM Actividad WHERE idActividad = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idActividad);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    /**
     * Método para verificar si hay un conflicto de horario para un aula
     * específica
     *
     * @param reserva
     * @return verdadero si existe un conflicto de horarios para un aula.
     * @throws SQLException
     */
    public boolean hayConflictoDeHorario(ReservaDTO reserva) throws SQLException {
        String query = "SELECT COUNT(*) FROM Reserva WHERE numeroAula = ? AND fechaActividad = ? "
                + "AND ((horaInicio < ? AND horaFin > ?) OR (horaInicio < ? AND horaFin > ?))";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, reserva.getNumeroAula());
            stmt.setDate(2, Date.valueOf(reserva.getFechaActividad().toString()));
            stmt.setTime(3, Time.valueOf(reserva.getHoraFin().toString()));
            stmt.setTime(4, Time.valueOf(reserva.getHoraInicio().toString()));
            stmt.setTime(5, Time.valueOf(reserva.getHoraInicio().toString()));
            stmt.setTime(6, Time.valueOf(reserva.getHoraFin().toString()));

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    /**
     * Este metodo realiza la reserva en base a una fecha y horario determinado.
     *
     * @param tipoReserva
     * @param diaSeleccionado
     * @param horaInicio
     * @param horaFin
     * @param reserva
     * @return
     * @throws SQLException
     */

    public boolean realizarReserva(String tipoReserva, String diaSeleccionado, Tiempo horaInicio, Tiempo horaFin, ReservaDTO reserva) throws SQLException {
        if (tipoReserva.equals("Anual") || tipoReserva.equals("Cuatrimestral")) {
            // Convertir el día seleccionado a una fecha concreta.
            Date fechaActividad = calcularFechaActividad(diaSeleccionado);

            reserva.setFechaActividad(Fecha.fromSqlDate(fechaActividad));

            // 1. Verificar si hay un conflicto de horarios para la reserva.
            if (hayConflictoDeHorario(reserva)) {
                System.out.println("Hay un conflicto de horarios para la reserva.");
                return false;
            }

            // 2. Insertar la reserva
            boolean reservaInsertada = insertarReserva(reserva);
            if (reservaInsertada) {
                System.out.println("Reserva realizada exitosamente.");
            } else {
                System.out.println("No se pudo realizar la reserva.");
            }
            return reservaInsertada;
        } else {
            System.out.println("Tipo de reserva no válido.");
            return false;
        }
    }

    /**
     * Método para calcular la fecha de la actividad basado en el día
     * seleccionado.
     *
     * @param diaSeleccionado El día de la semana (ej. "Lunes", "Martes", etc.).
     * @return La fecha correspondiente al día de la semana más cercano.
     */
    private Date calcularFechaActividad(String diaSeleccionado) {
        // Obtener la fecha actual
        LocalDate fechaActual = LocalDate.now();
        // Obtener el día de la semana actual
        DayOfWeek diaActual = fechaActual.getDayOfWeek();
        // Convertir el día seleccionado a DayOfWeek
        DayOfWeek diaDeseado = DayOfWeek.valueOf(diaSeleccionado.toUpperCase());

        // Calcular la diferencia de días entre el día actual y el deseado
        int diasHastaElDeseado = (diaDeseado.getValue() - diaActual.getValue() + 7) % 7;
        if (diasHastaElDeseado == 0) { // Si es hoy, se agrega una semana
            diasHastaElDeseado = 7;
        }

        // Calcular la fecha de la actividad
        LocalDate fechaActividad = fechaActual.plusDays(diasHastaElDeseado);
        return Date.valueOf(fechaActividad);
    }

    /**
     * Guarda una reserva cuatrimestral cada semana en el día seleccionado.
     *
     * @param reserva ReservaDTO con la información de la reserva.
     * @param diaSeleccionado Día de la semana en el que se desea realizar la
     * reserva (ej. "LUNES").
     * @param cantidadSemanas Cantidad de semanas en el cuatrimestre.
     * @throws SQLException Si ocurre algún error en la consulta SQL.
     */
    public void guardarReservaCuatrimestral(ReservaDTO reserva, DayOfWeek diaSeleccionado, int cantidadSemanas) throws SQLException {
        Date fechaInicio = reserva.getFechaActividad() != null
                ? reserva.getFechaActividad().toSqlDate()
                : obtenerFechaInicioCuatrimestre();

        for (int i = 0; i < cantidadSemanas; i++) {
            Date fechaActividad = calcularFechaProxima(fechaInicio, diaSeleccionado, i * 7);
            reserva.setFechaActividad(Fecha.fromSqlDate(fechaActividad));

            if (hayReservaEnRango(fechaActividad, reserva.getHoraInicio(), reserva.getHoraFin(), String.valueOf(reserva.getNumeroAula()))) {
                throw new SQLException("No se puede realizar la reserva: ya existe una reserva en "
                        + "el aula " + reserva.getNumeroAula() + " el " + fechaActividad
                        + " entre " + reserva.getHoraInicio() + " y " + reserva.getHoraFin());
            }
            
            NuevaReservaDAO nuevaReservaDAO = new NuevaReservaDAO(connection);
            
            nuevaReservaDAO.insertarReserva(new NuevaReservaDTO(reserva.getNumeroAula(), reserva.getHoraInicio(), reserva.getHoraFin(), reserva.getFechaActividad(), reserva.getDescripcion(), reserva.getResponsable()));
        }
    }

    /**
     * Método para calcular la próxima fecha de una reserva semanal
     *
     * @param fechaInicio
     * @param diaSeleccionado
     * @param diasIncremento
     * @return
     */
    private Date calcularFechaProxima(Date fechaInicio, DayOfWeek diaSeleccionado, int diasIncremento) {
        LocalDate fecha = fechaInicio.toLocalDate().plusDays(diasIncremento);
        return Date.valueOf(fecha.with(TemporalAdjusters.nextOrSame(diaSeleccionado)));
    }

    /**
     * Guarda una reserva anual cada semana en el día seleccionado.
     *
     * @param reserva ReservaDTO con la información de la reserva.
     * @param diaSeleccionado Día de la semana en el que se desea realizar la
     * reserva (ej. "LUNES").
     * @param cantidadSemanas Cantidad de semanas en el año (normalmente 52).
     * @throws SQLException Si ocurre algún error en la consulta SQL.
     */
    public void guardarReservaAnual(ReservaDTO reserva, DayOfWeek diaSeleccionado, int cantidadSemanas) throws SQLException {
        Date fechaInicio = reserva.getFechaActividad().toSqlDate();

        for (int i = 0; i < cantidadSemanas; i++) {
            java.sql.Date fechaActividad = calcularFechaProxima(fechaInicio, diaSeleccionado, i * 7);
            reserva.setFechaActividad(Fecha.fromSqlDate(fechaActividad));
            insertarReserva(reserva);
        }
    }

    /**
     * Este metodo obtiene la fecha de inicio del cuatrimestre
     *
     * @return verdadero si se pudo obtener la fecha.
     */
    private Date obtenerFechaInicioCuatrimestre() {
        LocalDate hoy = LocalDate.now();
        LocalDate inicioPrimerCuatrimestre = LocalDate.of(hoy.getYear(), 3, 1)
                .with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY)).plusDays(7);
        LocalDate inicioSegundoCuatrimestre = LocalDate.of(hoy.getYear(), 8, 1)
                .with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY)).plusDays(7);

        if (hoy.isBefore(inicioSegundoCuatrimestre)) {
            return Date.valueOf(inicioPrimerCuatrimestre);
        } else {
            return Date.valueOf(inicioSegundoCuatrimestre);
        }
    }

    /**
     * Este metodo verfica si hay reservas que chocan.
     *
     * @param fechaActividad
     * @param horaInicio
     * @param horaFin
     * @param aula
     * @return verdadero si hay reservas que chocan.
     * @throws SQLException
     */

    public boolean hayReservaEnRango(Date fechaActividad, Tiempo horaInicio, Tiempo horaFin, String aula) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Reserva WHERE numeroAula = ? AND fechaActividad = ? "
                + "AND (horaInicio < ? AND horaFin > ?)"; // Verifica si hay un solapamiento

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, Integer.parseInt(aula));
            preparedStatement.setDate(2, fechaActividad);
            preparedStatement.setTime(3, java.sql.Time.valueOf(horaFin.getHoras() + ":" + horaFin.getMinutos() + ":00"));
            preparedStatement.setTime(4, java.sql.Time.valueOf(horaInicio.getHoras() + ":" + horaInicio.getMinutos() + ":00"));

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0; // Retorna true si hay reservas que chocan
            }
        }
        return false; // Retorna false si no hay reservas que chocan
    }

    public void mostrarTablaReservasPorFecha(Date fecha) {
        try {
            // Crear consulta SQL para obtener reservas en la fecha especificada
            String query = "SELECT numeroAula, horaInicio, horaFin, descripcion FROM Reserva WHERE fechaActividad = ?";

            // Preparar la sentencia SQL
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setDate(1, fecha);
                ResultSet rs = stmt.executeQuery();

                // Crear el modelo de la tabla
                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("Número de Aula");
                model.addColumn("Hora Inicio");
                model.addColumn("Hora Fin");
                model.addColumn("Descripción");

                // Verificar si se encontraron reservas
                boolean hayReservas = false;
                while (rs.next()) {
                    hayReservas = true;
                    model.addRow(new Object[]{
                        rs.getInt("numeroAula"),
                        rs.getTime("horaInicio"),
                        rs.getTime("horaFin"),
                        rs.getString("descripcion")
                    });
                }

                // Crear el marco de la ventana
                JFrame frame = new JFrame("Reservas para " + fecha);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setSize(500, 300);

                if (!hayReservas) {
                    // Mostrar mensaje si no se encontraron reservas
                    JOptionPane.showMessageDialog(frame, "No se encontraron reservas para la fecha especificada.", "Resultado", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    // Crear el JTable con el modelo y agregarlo al frame
                    JTable table = new JTable(model);
                    table.setFillsViewportHeight(true);
                    JScrollPane scrollPane = new JScrollPane(table);
                    frame.add(scrollPane, BorderLayout.CENTER);
                }

                // Hacer visible la ventana
                frame.setVisible(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al obtener reservas: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
