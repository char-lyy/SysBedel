package paqueteDAO;

import paqueteDTO.ReservaDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAO {

    private Connection connection;

    public ReservaDAO(Connection connection) {
        this.connection = connection;
    }

    // Método para insertar una nueva reserva
    public boolean insertarReserva(ReservaDTO nuevaReserva) {
        String sql = "INSERT INTO Reserva (numeroAula, horaInicio, horaFin, fechaHoraReserva, fechaActividad, descripcion, responsable) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, nuevaReserva.getNumeroAula());
            statement.setTime(2, nuevaReserva.getHoraInicio().toSqlTime());
            statement.setTime(3, nuevaReserva.getHoraFin().toSqlTime());
            statement.setTimestamp(4, nuevaReserva.getFechaHoraReserva().toSqlDateTime());
            statement.setDate(5, nuevaReserva.getFechaActividad().toSqlDate());
            statement.setString(6, nuevaReserva.getDescripcion());
            statement.setString(7, nuevaReserva.getResponsable());

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0; // Retorna true si se inserta correctamente
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Retorna false si hubo un error en la inserción
        }
    }

    // Método para buscar una reserva por ID
    public ReservaDTO buscarReservaPorId(int id) {
        String sql = "SELECT * FROM Reserva WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapResultSetToReservaDTO(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Retorna null si no se encuentra la reserva
    }

    // Método para obtener todas las reservas
    public List<ReservaDTO> obtenerReservas() {
        List<ReservaDTO> reservas = new ArrayList<>();
        String sql = "SELECT * FROM Reserva";
        try (PreparedStatement statement = connection.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                reservas.add(mapResultSetToReservaDTO(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservas; // Retorna la lista de reservas
    }

    // Método para actualizar una reserva
    public boolean actualizarReserva(ReservaDTO nuevaReserva) {
        String sql = "UPDATE Reserva SET numeroAula = ?, horaInicio = ?, horaFin = ?, fechaHoraReserva = ?, fechaActividad = ?, descripcion = ?, responsable = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, nuevaReserva.getNumeroAula());
            statement.setTime(2, nuevaReserva.getHoraInicio().toSqlTime());
            statement.setTime(3, nuevaReserva.getHoraFin().toSqlTime());
            statement.setTimestamp(4, nuevaReserva.getFechaHoraReserva().toSqlDateTime());
            statement.setDate(5, nuevaReserva.getFechaActividad().toSqlDate());
            statement.setString(6, nuevaReserva.getDescripcion());
            statement.setString(7, nuevaReserva.getResponsable());
            statement.setInt(8, nuevaReserva.getId()); // ID de la reserva a actualizar

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0; // Retorna true si se actualiza correctamente
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Retorna false si hubo un error en la actualización
        }
    }

    // Método para eliminar una reserva
    public boolean eliminarReserva(int id) {
        String sql = "DELETE FROM Reserva WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0; // Retorna true si se elimina correctamente
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Retorna false si hubo un error en la eliminación
        }
    }

    // Método auxiliar para mapear ResultSet a NuevaReservaDTO
    private ReservaDTO mapResultSetToReservaDTO(ResultSet resultSet) throws SQLException {
        ReservaDTO nuevaReservaDTO = new ReservaDTO();
        return nuevaReservaDTO.fromResultSet(resultSet);
    }
}
