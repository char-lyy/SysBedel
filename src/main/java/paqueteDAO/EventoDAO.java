package paqueteDAO;

import paqueteDTO.EventoDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventoDAO {

//    private Connection connection;
//
//    // Constructor que recibe una conexión a la base de datos
//    public EventoDAO(Connection connection) {
//        this.connection = connection;
//    }
//
//    // Método para agregar un nuevo evento
//    public boolean agregarEvento(EventoDTO evento) throws SQLException {
//        String query = "INSERT INTO Evento (codigoActividad, ResponsableDelEvento, DescripcionDelEvento, nroNotaEvento) VALUES (?, ?, ?, ?)";
//        try (PreparedStatement statement = connection.prepareStatement(query)) {
//            statement.setString(1, evento.getCodigoActividad());
//            statement.setString(2, evento.getResponsableEvento());
//            statement.setString(3, evento.getDescripcionEvento());
//            statement.setInt(4, evento.getNroNotaEvento());
//
//            int filasInsertadas = statement.executeUpdate();
//            return filasInsertadas > 0;
//        }
//    }
//
//    // Método para obtener un evento por su código de actividad
//    public EventoDTO obtenerEventoPorCodigo(String codigoActividad) throws SQLException {
//        String query = "SELECT * FROM Evento WHERE codigoActividad = ?";
//        try (PreparedStatement statement = connection.prepareStatement(query)) {
//            statement.setString(1, codigoActividad);
//            ResultSet resultSet = statement.executeQuery();
//
//            if (resultSet.next()) {
//                String responsableEvento = resultSet.getString("ResponsableDelEvento");
//                String descripcionEvento = resultSet.getString("DescripcionDelEvento");
//                int nroNotaEvento = resultSet.getInt("nroNotaEvento");
//                return new EventoDTO(codigoActividad, responsableEvento, descripcionEvento, nroNotaEvento);
//            }
//        }
//        return null;
//    }
//
//    // Método para actualizar un evento
//    public boolean actualizarEvento(EventoDTO evento) throws SQLException {
//        String query = "UPDATE Evento SET ResponsableDelEvento = ?, DescripcionDelEvento = ?, nroNotaEvento = ? WHERE codigoActividad = ?";
//        try (PreparedStatement statement = connection.prepareStatement(query)) {
//            statement.setString(1, evento.getResponsableEvento());
//            statement.setString(2, evento.getDescripcionEvento());
//            statement.setInt(3, evento.getNroNotaEvento());
//            statement.setString(4, evento.getCodigoActividad());
//
//            int filasActualizadas = statement.executeUpdate();
//            return filasActualizadas > 0;
//        }
//    }
//
//    // Método para eliminar un evento por su código de actividad
//    public boolean eliminarEvento(int codigoActividad) throws SQLException {
//        String query = "DELETE FROM Evento WHERE codigoActividad = ?";
//        try (PreparedStatement statement = connection.prepareStatement(query)) {
//            statement.setInt(1, codigoActividad);
//
//            int filasEliminadas = statement.executeUpdate();
//            return filasEliminadas > 0;
//        }
//    }
//
//    // Método para obtener todos los eventos
//    public List<EventoDTO> obtenerTodosLosEventos() throws SQLException {
//        List<EventoDTO> eventos = new ArrayList<>();
//        String query = "SELECT * FROM Evento";
//        try (PreparedStatement statement = connection.prepareStatement(query);
//             ResultSet resultSet = statement.executeQuery()) {
//
//            while (resultSet.next()) {
//                String codigoActividad = resultSet.getString("codigoActividad");
//                String responsableEvento = resultSet.getString("ResponsableDelEvento");
//                String descripcionEvento = resultSet.getString("DescripcionDelEvento");
//                int nroNotaEvento = resultSet.getInt("nroNotaEvento");
//
//                EventoDTO evento = new EventoDTO(codigoActividad, responsableEvento, descripcionEvento, nroNotaEvento);
//                eventos.add(evento);
//            }
//        }
//        return eventos;
//    }
}
