package paqueteDAO;

import paqueteDTO.RecursoDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecursoDAO {

    private Connection connection;

    // Constructor que recibe una conexión a la base de datos
    public RecursoDAO(Connection connection) {
        this.connection = connection;
    }

    // Método para agregar un nuevo recurso
    public boolean agregarRecurso(RecursoDTO recurso) throws SQLException {
        String query = "INSERT INTO Recurso (codigoRecurso, DescripcionRecurso, cantidadRecurso) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, recurso.getCodigoRecurso());
            statement.setString(2, recurso.getDescripcionRecurso());
            statement.setInt(3, recurso.getCantidadRecurso());

            int filasInsertadas = statement.executeUpdate();
            return filasInsertadas > 0;
        }
    }

    // Método para obtener un recurso por su código
    public RecursoDTO obtenerRecursoPorCodigo(int codigoRecurso) throws SQLException {
        String query = "SELECT * FROM Recurso WHERE codigoRecurso = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, codigoRecurso);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String descripcionRecurso = resultSet.getString("DescripcionRecurso");
                int cantidadRecurso = resultSet.getInt("cantidadRecurso");
                return new RecursoDTO(codigoRecurso, descripcionRecurso, cantidadRecurso);
            }
        }
        return null;
    }

    // Método para actualizar un recurso
    public boolean actualizarRecurso(RecursoDTO recurso) throws SQLException {
        String query = "UPDATE Recurso SET DescripcionRecurso = ?, cantidadRecurso = ? WHERE codigoRecurso = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, recurso.getDescripcionRecurso());
            statement.setInt(2, recurso.getCantidadRecurso());
            statement.setInt(3, recurso.getCodigoRecurso());

            int filasActualizadas = statement.executeUpdate();
            return filasActualizadas > 0;
        }
    }

    // Método para eliminar un recurso por su código
    public boolean eliminarRecurso(int codigoRecurso) throws SQLException {
        String query = "DELETE FROM Recurso WHERE codigoRecurso = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, codigoRecurso);

            int filasEliminadas = statement.executeUpdate();
            return filasEliminadas > 0;
        }
    }

    // Método para obtener todos los recursos
    public List<RecursoDTO> obtenerTodosLosRecursos() throws SQLException {
        List<RecursoDTO> recursos = new ArrayList<>();
        String query = "SELECT * FROM Recurso";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int codigoRecurso = resultSet.getInt("codigoRecurso");
                String descripcionRecurso = resultSet.getString("DescripcionRecurso");
                int cantidadRecurso = resultSet.getInt("cantidadRecurso");

                RecursoDTO recurso = new RecursoDTO(codigoRecurso, descripcionRecurso, cantidadRecurso);
                recursos.add(recurso);
            }
        }
        return recursos;
    }
}
