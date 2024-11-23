package paqueteDAO;

import paqueteDTO.AulaDTO;
import utilidades.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AulaDAO {

    // Método para registrar un aula en la base de datos
    public void registrarAula(AulaDTO aula) throws SQLException {
        String query = "INSERT INTO aula (numeroAula, capacidadAula, ocupada, observaciones, responsable, fecha, hora) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, aula.getNroAula());
            statement.setInt(2, aula.getCapacidad());
            statement.setBoolean(3, aula.isOcupada());
            statement.setString(4, aula.getObservaciones());
            statement.setString(5, aula.getResponsable()); // Nuevo atributo
            statement.setString(6, aula.getFecha()); // Nuevo atributo
            statement.setString(7, aula.getHora()); // Nuevo atributo
            statement.executeUpdate();
        }
    }

    // Método para modificar un aula en la base de datos
    public void modificarAula(AulaDTO aula) throws SQLException {
        String query = "UPDATE aula SET capacidadAula = ?, ocupada = ?, observaciones = ?, responsable = ?, fecha = ?, hora = ? WHERE numeroAula = ?";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, aula.getCapacidad());
            statement.setBoolean(2, aula.isOcupada());
            statement.setString(3, aula.getObservaciones());
            statement.setString(4, aula.getResponsable()); // Nuevo atributo
            statement.setDate(5, java.sql.Date.valueOf(aula.getFecha())); // Nuevo atributo
            statement.setTime(6, Time.valueOf(aula.getHora())); // Nuevo atributo
            statement.setInt(7, aula.getNroAula());
            statement.executeUpdate();
        }
    }

    // Método para cancelar un aula en la base de datos
    public void cancelarAula(int numeroAula) throws SQLException {
        String query = "DELETE FROM aula WHERE numeroAula = ?";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, numeroAula);
            statement.executeUpdate();
        }
    }

    // Método para obtener una lista de aulas de la base de datos
    public List<AulaDTO> obtenerAulas() throws SQLException {
        List<AulaDTO> aulas = new ArrayList<>();
        String query = "SELECT numeroAula, capacidadAula, ocupada, observaciones, responsable, fecha, hora FROM aula"; // Actualizado para incluir nuevos atributos
        
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int nroAula = resultSet.getInt("numeroAula");
                int capacidad = resultSet.getInt("capacidadAula");
                boolean ocupada = resultSet.getBoolean("ocupada");
                String observaciones = resultSet.getString("observaciones");
                String responsable = resultSet.getString("responsable"); // Nuevo atributo
                String fecha = resultSet.getString("fecha"); // Nuevo atributo
                String hora = resultSet.getString("hora"); // Nuevo atributo

                AulaDTO aula = new AulaDTO(nroAula, capacidad, ocupada, observaciones, responsable, fecha, hora);
                aulas.add(aula);
            }
        }
        return aulas;
    }
}
