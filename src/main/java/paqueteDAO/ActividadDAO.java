package paqueteDAO;

import paqueteDTO.ActividadDTO;
import java.util.Date;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActividadDAO {

    private Connection connection;

    public ActividadDAO() {
    }

    // Constructor que recibe una conexión a la base de datos
    public ActividadDAO(Connection connection) {
        this.connection = connection;
    }

    // Método para agregar una nueva actividad
    public boolean agregarActividad(ActividadDTO actividad) throws SQLException {
        String query = "INSERT INTO Actividad (codigoActividad, numeroAula, codigoHorario, fechaInicioActividad, fechaFinActividad, periodoActividad, tipoActividad) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, actividad.getCodigoActividad());
            statement.setInt(2, actividad.getNumeroAula());
            statement.setString(3, actividad.getCodigoHorario());
            statement.setTimestamp(4, new Timestamp(actividad.getFechaInicioActividad().getTime()));
            statement.setTimestamp(5, new Timestamp(actividad.getFechaFinActividad().getTime()));
            statement.setString(6, actividad.getPeriodoActividad());
            statement.setString(7, actividad.getTipoActividad());

            int filasInsertadas = statement.executeUpdate();
            return filasInsertadas > 0;
        }
    }

    // Método para obtener una actividad por su código
    public ActividadDTO obtenerActividadPorCodigo(String codigoActividad) throws SQLException {
        String query = "SELECT * FROM Actividad WHERE codigoActividad = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, codigoActividad);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int numeroAula = resultSet.getInt("numeroAula");
                String codigoHorario = resultSet.getString("codigoHorario");
                Date fechaInicioActividad = resultSet.getTimestamp("fechaInicioActividad");
                Date fechaFinActividad = resultSet.getTimestamp("fechaFinActividad");
                String periodoActividad = resultSet.getString("periodoActividad");
                String tipoActividad = resultSet.getString("tipoActividad");

                return new ActividadDTO(codigoActividad, numeroAula, codigoHorario, fechaInicioActividad, fechaFinActividad, periodoActividad, tipoActividad);
            }
        }
        return null;
    }

    // Método para actualizar una actividad
    public boolean actualizarActividad(ActividadDTO actividad) throws SQLException {
        String query = "UPDATE Actividad SET numeroAula = ?, codigoHorario = ?, fechaInicioActividad = ?, fechaFinActividad = ?, periodoActividad = ?, tipoActividad = ? WHERE codigoActividad = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, actividad.getNumeroAula());
            statement.setString(2, actividad.getCodigoHorario());
            statement.setTimestamp(3, new Timestamp(actividad.getFechaInicioActividad().getTime()));
            statement.setTimestamp(4, new Timestamp(actividad.getFechaFinActividad().getTime()));
            statement.setString(5, actividad.getPeriodoActividad());
            statement.setString(6, actividad.getTipoActividad());
            statement.setString(7, actividad.getCodigoActividad());

            int filasActualizadas = statement.executeUpdate();
            return filasActualizadas > 0;
        }
    }

    // Método para eliminar una actividad por su código
    public boolean eliminarActividad(String codigoActividad) throws SQLException {
        String query = "DELETE FROM Actividad WHERE codigoActividad = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, codigoActividad);

            int filasEliminadas = statement.executeUpdate();
            return filasEliminadas > 0;
        }
    }

    // Método para obtener todas las actividades
    public List<ActividadDTO> obtenerTodasLasActividades() throws SQLException {
        List<ActividadDTO> actividades = new ArrayList<>();
        String query = "SELECT * FROM Actividad";
        try (PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String codigoActividad = resultSet.getString("codigoActividad");
                int numeroAula = resultSet.getInt("numeroAula");
                String codigoHorario = resultSet.getString("codigoHorario");
                Date fechaInicioActividad = resultSet.getTimestamp("fechaInicioActividad");
                Date fechaFinActividad = resultSet.getTimestamp("fechaFinActividad");
                String periodoActividad = resultSet.getString("periodoActividad");
                String tipoActividad = resultSet.getString("tipoActividad");

                ActividadDTO actividad = new ActividadDTO(codigoActividad, numeroAula, codigoHorario, fechaInicioActividad, fechaFinActividad, periodoActividad, tipoActividad);
                actividades.add(actividad);
            }
        }
        return actividades;
    }
}
