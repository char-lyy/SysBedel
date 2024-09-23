package paqueteDAO;

import paqueteDTO.AulaDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AulaDAO {

    private Connection connection;

    public AulaDAO(Connection connection) {
        this.connection = connection;
    }

    // Método para agregar una nueva aula
    public void agregarAula(AulaDTO aula) throws SQLException {
        String sql = "INSERT INTO Aula (nroAula, capacidad, ocupada) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, aula.getNroAula());
            statement.setInt(2, aula.getCapacidad());
            statement.setBoolean(3, aula.isOcupada());
            statement.executeUpdate();
        }
    }

    // Método para obtener todas las aulas
    public List<AulaDTO> obtenerTodasLasAulas() throws SQLException {
        List<AulaDTO> aulas = new ArrayList<>();
        String sql = "SELECT * FROM Aula";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int nroAula = resultSet.getInt("nroAula");
                int capacidad = resultSet.getInt("capacidad");
                boolean ocupada = resultSet.getBoolean("ocupada");
                aulas.add(new AulaDTO(nroAula, capacidad, ocupada));
            }
        }

        return aulas;
    }

    // Método para obtener aulas no ocupadas
    public List<AulaDTO> obtenerAulasNoOcupadas() throws SQLException {
        List<AulaDTO> aulasNoOcupadas = new ArrayList<>();
        String sql = "SELECT * FROM Aula WHERE ocupada = false";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int nroAula = resultSet.getInt("nroAula");
                int capacidad = resultSet.getInt("capacidad");
                aulasNoOcupadas.add(new AulaDTO(nroAula, capacidad, false));
            }
        }

        return aulasNoOcupadas;
    }

    // Método para obtener aulas ocupadas
    public List<AulaDTO> obtenerAulasOcupadas() throws SQLException {
        List<AulaDTO> aulasOcupadas = new ArrayList<>();
        String sql = "SELECT * FROM Aula WHERE ocupada = true";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int nroAula = resultSet.getInt("nroAula");
                int capacidad = resultSet.getInt("capacidad");
                aulasOcupadas.add(new AulaDTO(nroAula, capacidad, true));
            }
        }

        return aulasOcupadas;
    }

    // Método para actualizar el estado de ocupación de un aula
    public void actualizarOcupacion(int nroAula, boolean ocupada) throws SQLException {
        String sql = "UPDATE Aula SET ocupada = ? WHERE nroAula = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBoolean(1, ocupada);
            statement.setInt(2, nroAula);
            statement.executeUpdate();
        }
    }
}
