package paqueteDAO;

import paqueteDTO.HorarioDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import paqueteDTO.Hora;

public class HorarioDAO {

    private Connection connection;

    // Constructor que recibe una conexión a la base de datos
    public HorarioDAO(Connection connection) {
        this.connection = connection;
    }

    // Método para agregar un nuevo horario a la base de datos
    public boolean agregarHorario(HorarioDTO horario) throws SQLException {
        String query = "INSERT INTO horarios (codigoHorario, dia, horaInicio, horaFin) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, horario.getCodigoHorario());
            statement.setString(2, horario.getDia());
            statement.setString(3, horario.getHoraInicio().toString());
            statement.setString(4, horario.getHoraFin().toString());

            int filasInsertadas = statement.executeUpdate();
            return filasInsertadas > 0;
        }
    }

    // Método para obtener un horario por su código
    public HorarioDTO obtenerHorarioPorCodigo(int codigoHorario) throws SQLException {
        String query = "SELECT * FROM horarios WHERE codigoHorario = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, codigoHorario);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String dia = resultSet.getString("dia");
                Hora horaInicio = convertirStringAHora(resultSet.getString("horaInicio"));
                Hora horaFin = convertirStringAHora(resultSet.getString("horaFin"));

                return new HorarioDTO(codigoHorario, dia, horaInicio, horaFin);
            }
        }
        return null;
    }

    // Método para actualizar un horario en la base de datos
    public boolean actualizarHorario(HorarioDTO horario) throws SQLException {
        String query = "UPDATE horarios SET dia = ?, horaInicio = ?, horaFin = ? WHERE codigoHorario = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, horario.getDia());
            statement.setString(2, horario.getHoraInicio().toString());
            statement.setString(3, horario.getHoraFin().toString());
            statement.setInt(4, horario.getCodigoHorario());

            int filasActualizadas = statement.executeUpdate();
            return filasActualizadas > 0;
        }
    }

    // Método para eliminar un horario por su código
    public boolean eliminarHorario(int codigoHorario) throws SQLException {
        String query = "DELETE FROM horarios WHERE codigoHorario = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, codigoHorario);

            int filasEliminadas = statement.executeUpdate();
            return filasEliminadas > 0;
        }
    }

    // Método para obtener todos los horarios
    public List<HorarioDTO> obtenerTodosLosHorarios() throws SQLException {
        List<HorarioDTO> horarios = new ArrayList<>();
        String query = "SELECT * FROM horarios";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int codigoHorario = resultSet.getInt("codigoHorario");
                String dia = resultSet.getString("dia");
                Hora horaInicio = convertirStringAHora(resultSet.getString("horaInicio"));
                Hora horaFin = convertirStringAHora(resultSet.getString("horaFin"));

                HorarioDTO horario = new HorarioDTO(codigoHorario, dia, horaInicio, horaFin);
                horarios.add(horario);
            }
        }
        return horarios;
    }

    // Método para convertir un String en un objeto Hora
    private Hora convertirStringAHora(String horaString) {
        String[] partes = horaString.split(":");
        int horas = Integer.parseInt(partes[0]);
        int minutos = Integer.parseInt(partes[1]);
        return new Hora(horas, minutos);
    }
}
