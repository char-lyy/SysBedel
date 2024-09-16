package paqueteDAO;

import paqueteDTO.ClaseDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClaseDAO {

    private Connection connection;

    // Constructor que recibe una conexión a la base de datos
    public ClaseDAO(Connection connection) {
        this.connection = connection;
    }


    // Método para agregar una nueva clase
    public boolean agregarClase(ClaseDTO clase) throws SQLException {
        ActividadDAO actividadDAO = new ActividadDAO();
        actividadDAO.agregarActividad(clase);
        String query = "INSERT INTO Clase (codigoActividad, legajoDocente, asignatura) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, clase.getCodigoActividad());
            statement.setInt(2, clase.getLegajoDocente());
            statement.setString(3, clase.getAsignatura());
            int filasInsertadas = statement.executeUpdate();
            return filasInsertadas > 0;
        }
    }

    // Método para obtener una clase por su código de actividad y legajo de docente
    public ClaseDTO obtenerClasePorCodigoYDocente(String codigoActividad, int legajoDocente) throws SQLException {
        String query = "SELECT * FROM Clase WHERE codigoActividad = ? AND legajoDocente = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, codigoActividad);
            statement.setInt(2, legajoDocente);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String asignatura = resultSet.getString("asignatura");
                return new ClaseDTO(codigoActividad, legajoDocente, asignatura);
            }
        }
        return null;
    }

    // Método para actualizar una clase
    public boolean actualizarClase(ClaseDTO clase) throws SQLException {
        String query = "UPDATE Clase SET asignatura = ? WHERE codigoActividad = ? AND legajoDocente = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, clase.getAsignatura());
            statement.setString(2, clase.getCodigoActividad());
            statement.setInt(3, clase.getLegajoDocente());

            int filasActualizadas = statement.executeUpdate();
            return filasActualizadas > 0;
        }
    }

    // Método para eliminar una clase por su código de actividad y legajo de docente
    public boolean eliminarClase(int codigoActividad, int legajoDocente) throws SQLException {
        String query = "DELETE FROM Clase WHERE codigoActividad = ? AND legajoDocente = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, codigoActividad);
            statement.setInt(2, legajoDocente);

            int filasEliminadas = statement.executeUpdate();
            return filasEliminadas > 0;
        }
    }

    // Método para obtener todas las clases
    public List<ClaseDTO> obtenerTodasLasClases() throws SQLException {
        List<ClaseDTO> clases = new ArrayList<>();
        String query = "SELECT * FROM Clase";
        try (PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String codigoActividad = resultSet.getString("codigoActividad");
                int legajoDocente = resultSet.getInt("legajoDocente");
                String asignatura = resultSet.getString("asignatura");

                ClaseDTO clase = new ClaseDTO(codigoActividad, legajoDocente, asignatura);
                clases.add(clase);
            }
        }
        return clases;
    }
}
