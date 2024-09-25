package paqueteDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import paqueteDTO.ReservaDTO;
import paqueteDTO.TiempoDTO;

public class ReservaDAO {

    private Connection connection;

    public ReservaDAO(Connection connection) {
        this.connection = connection;
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
    public List<Integer> obtenerAulasDisponibles(java.sql.Date fecha, TiempoDTO horaInicio, TiempoDTO horaFin) throws SQLException {
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

    // Método para insertar una nueva reserva solo si hay aulas disponibles
    public boolean insertarReserva(ReservaDTO reserva) throws SQLException {

        // 1. Obtener aulas disponibles para la fecha y hora de la reserva
        List<Integer> aulasDisponibles = obtenerAulasDisponibles(
                reserva.getFechaActividad().toSqlDate(),
                reserva.getHoraInicio(),
                reserva.getHoraFin()
        );

        // Verificar si el aula específica está disponible en la lista de aulas disponibles
        if (!aulasDisponibles.contains(reserva.getNumeroAula())) {
            System.out.println("No hay aulas disponibles o el aula solicitada ya está reservada.");
            return false;
        }

        // 2. Si todo está bien, realizar la inserción en la base de datos
        String query = "INSERT INTO Reserva (idReserva, idActividad, numeroAula, confirmada, horaInicio, horaFin, fechaReserva, fechaActividad) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, reserva.getIdReserva());
            stmt.setInt(2, reserva.getIdActividad());
            stmt.setInt(3, reserva.getNumeroAula());
            stmt.setBoolean(4, reserva.isConfirmada());
            stmt.setTime(5, reserva.getHoraInicio().toSqlTime());
            stmt.setTime(6, reserva.getHoraFin().toSqlTime());
            stmt.setDate(7, reserva.getFechaReserva().toSqlDate());
            stmt.setDate(8, reserva.getFechaActividad().toSqlDate());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        }
    }

    // Método para verificar si una aula existe en la base de datos
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

    // Método para verificar si una actividad existe en la base de datos
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

    // Método para verificar si hay un conflicto de horario para un aula específica
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
}
