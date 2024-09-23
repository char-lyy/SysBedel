package paqueteDAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import paqueteDTO.ReservaDTO;
import paqueteDTO.FechaDTO;
import paqueteDTO.TiempoDTO;
import principal.ConnectionManager;

public class ReservaDAO {

    private static final String INSERT_RESERVA_SQL = "INSERT INTO reservas (id_reserva, id_actividad, numero_aula, confirmada, hora_inicio, hora_fin, fecha_reserva, fecha_actividad) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_RESERVA_SQL = "UPDATE reservas SET id_actividad = ?, numero_aula = ?, confirmada = ?, hora_inicio = ?, hora_fin = ?, fecha_reserva = ?, fecha_actividad = ? WHERE id_reserva = ?";
    private static final String DELETE_RESERVA_SQL = "DELETE FROM reservas WHERE id_reserva = ?";
    private static final String SELECT_RESERVA_BY_ID = "SELECT * FROM reservas WHERE id_reserva = ?";
    private static final String SELECT_ALL_RESERVAS = "SELECT * FROM reservas";
    private final ConnectionManager cm = new ConnectionManager();

    // Método para verificar si hay un choque de horarios
    public boolean hayConflictoDeHorario(ReservaDTO reserva) throws SQLException {
        String query = "SELECT * FROM reserva WHERE numeroAula = ? AND fechaActividad = ? AND confirmada = true "
                + "AND (horaInicio < ? AND horaFin > ?)"; // Verificamos que los horarios no se solapen.

        try (PreparedStatement ps = cm.getConnection().prepareStatement(query)) {
            ps.setInt(1, reserva.getNumeroAula());
            ps.setDate(2, reserva.getFechaActividad().toSqlDate());
            ps.setTime(3, reserva.getHoraFin().toSqlTime()); // Se usa la hora de fin para la validación.
            ps.setTime(4, reserva.getHoraInicio().toSqlTime()); // Se usa la hora de inicio para la validación.

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // Si hay un resultado, significa que existe un conflicto de horarios.
            }
        }
    }

    // Método para insertar la reserva si no hay conflicto
    public boolean insertarReserva(ReservaDTO reserva) throws SQLException {
        if (hayConflictoDeHorario(reserva)) {
            System.out.println("No se puede realizar la reserva. Existe un conflicto de horarios.");
            return false; // Hay conflicto de horarios.
        }

//        String query = "INSERT INTO reserva (idActividad, numeroAula, confirmada, horaInicio, horaFin, fechaReserva, fechaActividad) "
//                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = cm.getConnection().prepareStatement(INSERT_RESERVA_SQL)) {
            ps.setInt(1, reserva.getIdActividad());
            ps.setInt(2, reserva.getNumeroAula());
            ps.setBoolean(3, reserva.isConfirmada());
            ps.setTime(4, reserva.getHoraInicio().toSqlTime());
            ps.setTime(5, reserva.getHoraFin().toSqlTime());
            ps.setDate(6, reserva.getFechaReserva().toSqlDate());
            ps.setDate(7, reserva.getFechaActividad().toSqlDate());

            ps.executeUpdate();
            return true; // La reserva se insertó exitosamente.
        }
    }

    public void actualizarReserva(ReservaDTO reserva) throws SQLException {
        try (Connection connection = cm.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_RESERVA_SQL)) {
            preparedStatement.setInt(1, reserva.getIdActividad());
            preparedStatement.setInt(2, reserva.getNumeroAula());
            preparedStatement.setBoolean(3, reserva.isConfirmada());
            preparedStatement.setTime(4, Time.valueOf(reserva.getHoraInicio().toString()));
            preparedStatement.setTime(5, Time.valueOf(reserva.getHoraFin().toString()));
            preparedStatement.setDate(6, reserva.getFechaReserva().toSqlDate());
            preparedStatement.setDate(7, reserva.getFechaActividad().toSqlDate());
            preparedStatement.setInt(8, reserva.getIdReserva());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public boolean eliminarReserva(int idReserva) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = cm.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(DELETE_RESERVA_SQL)) {
            preparedStatement.setInt(1, idReserva);
            rowDeleted = preparedStatement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    public ReservaDTO seleccionarReserva(int idReserva) {
        ReservaDTO reserva = null;
        try (Connection connection = cm.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_RESERVA_BY_ID)) {
            preparedStatement.setInt(1, idReserva);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                int idActividad = rs.getInt("id_actividad");
                int numeroAula = rs.getInt("numero_aula");
                boolean confirmada = rs.getBoolean("confirmada");
                TiempoDTO horaInicio = TiempoDTO.fromSqlTime(rs.getTime("hora_inicio"));
                TiempoDTO horaFin = TiempoDTO.fromSqlTime(rs.getTime("hora_fin"));
                FechaDTO fechaReserva = FechaDTO.fromSqlDate(rs.getDate("fecha_reserva"));
                FechaDTO fechaActividad = FechaDTO.fromSqlDate(rs.getDate("fecha_actividad"));

                reserva = new ReservaDTO(idReserva, idActividad, numeroAula, confirmada, horaInicio, horaFin, fechaReserva, fechaActividad);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return reserva;
    }

    public List<ReservaDTO> seleccionarTodasLasReservas() {
        List<ReservaDTO> reservas = new ArrayList<>();
        try (Connection connection = cm.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_RESERVAS)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int idReserva = rs.getInt("id_reserva");
                int idActividad = rs.getInt("id_actividad");
                int numeroAula = rs.getInt("numero_aula");
                boolean confirmada = rs.getBoolean("confirmada");
                TiempoDTO horaInicio = TiempoDTO.fromSqlTime(rs.getTime("hora_inicio"));
                TiempoDTO horaFin = TiempoDTO.fromSqlTime(rs.getTime("hora_fin"));
                FechaDTO fechaReserva = FechaDTO.fromSqlDate(rs.getDate("fecha_reserva"));
                FechaDTO fechaActividad = FechaDTO.fromSqlDate(rs.getDate("fecha_actividad"));

                reservas.add(new ReservaDTO(idReserva, idActividad, numeroAula, confirmada, horaInicio, horaFin, fechaReserva, fechaActividad));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return reservas;
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
