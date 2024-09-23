//package paqueteDAO;
//
//import paqueteDTO.ObservacionDTO;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class ObservacionDAO {
//    private Connection connection;
//
//    public ObservacionDAO(Connection connection) {
//        this.connection = connection;
//    }
//
//    // Crear una nueva observación
//    public void crearObservacion(ObservacionDTO observacion, Time horaObservacion) throws SQLException {
//        String sql = "INSERT INTO Observacion (codigoObservacion, numeroAula, descripcion, fechaObservacion, horaObservacion) VALUES (?, ?, ?, ?, ?)";
//        try (PreparedStatement statement = connection.prepareStatement(sql)) {
//            statement.setInt(1, observacion.getCodigoObservacion());
//            statement.setInt(2, observacion.getNumeroAula());
//            statement.setString(3, observacion.getDescripcion());
//            statement.setDate(4, observacion.getFechaObservacion());
//            statement.setTime(5, horaObservacion);
//            statement.executeUpdate();
//        }
//    }
//
//    // Leer todas las observaciones
//    public List<ObservacionDTO> obtenerTodasLasObservaciones() throws SQLException {
//        List<ObservacionDTO> observaciones = new ArrayList<>();
//        String sql = "SELECT * FROM Observacion";
//        try (Statement statement = connection.createStatement();
//             ResultSet resultSet = statement.executeQuery(sql)) {
//            while (resultSet.next()) {
//                ObservacionDTO observacion = new ObservacionDTO(
//                        resultSet.getInt("codigoObservacion"),
//                        resultSet.getInt("numeroAula"),
//                        resultSet.getString("descripcion"),
//                        resultSet.getDate("fechaObservacion")
//                );
//                observaciones.add(observacion);
//            }
//        }
//        return observaciones;
//    }
//
//    // Actualizar una observación
//    public void actualizarObservacion(ObservacionDTO observacion, Time horaObservacion) throws SQLException {
//        String sql = "UPDATE Observacion SET numeroAula = ?, descripcion = ?, fechaObservacion = ?, horaObservacion = ? WHERE codigoObservacion = ?";
//        try (PreparedStatement statement = connection.prepareStatement(sql)) {
//            statement.setInt(1, observacion.getNumeroAula());
//            statement.setString(2, observacion.getDescripcion());
//            statement.setDate(3, observacion.getFechaObservacion());
//            statement.setTime(4, horaObservacion);
//            statement.setInt(5, observacion.getCodigoObservacion());
//            statement.executeUpdate();
//        }
//    }
//
//    // Eliminar una observación
//    public void eliminarObservacion(int codigoObservacion) throws SQLException {
//        String sql = "DELETE FROM Observacion WHERE codigoObservacion = ?";
//        try (PreparedStatement statement = connection.prepareStatement(sql)) {
//            statement.setInt(1, codigoObservacion);
//            statement.executeUpdate();
//        }
//    }
//
//    // Obtener una observación por código
//    public ObservacionDTO obtenerObservacionPorCodigo(int codigoObservacion) throws SQLException {
//        String sql = "SELECT * FROM Observacion WHERE codigoObservacion = ?";
//        try (PreparedStatement statement = connection.prepareStatement(sql)) {
//            statement.setInt(1, codigoObservacion);
//            try (ResultSet resultSet = statement.executeQuery()) {
//                if (resultSet.next()) {
//                    return new ObservacionDTO(
//                            resultSet.getInt("codigoObservacion"),
//                            resultSet.getInt("numeroAula"),
//                            resultSet.getString("descripcion"),
//                            resultSet.getDate("fechaObservacion")
//                    );
//                }
//            }
//        }
//        return null; // No se encontró la observación
//    }
//}
