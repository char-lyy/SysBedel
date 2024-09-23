//package paqueteDAO;
//
//import paqueteDTO.PrestamoDTO;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class PrestamoDAO {
//    private Connection connection;
//
//    public PrestamoDAO(Connection connection) {
//        this.connection = connection;
//    }
//
//    // Crear un nuevo préstamo
//    public void crearPrestamo(PrestamoDTO prestamo) throws SQLException {
//        String sql = "INSERT INTO Prestamo (legajoDocente, codigoRecurso, estaPrestado, fechaPrestamo, horaPrestamo, observacionPrestamo) VALUES (?, ?, ?, ?, ?, ?)";
//        try (PreparedStatement statement = connection.prepareStatement(sql)) {
//            statement.setString(1, prestamo.getLegajoDocente());
//            statement.setString(2, prestamo.getCodigoRecurso());
//            statement.setBoolean(3, prestamo.isEstaPrestado());
//            statement.setDate(4, prestamo.getFechaPrestamo());
//            statement.setDate(5, prestamo.getHoraPrestamo());
//            statement.setString(6, prestamo.getObservacionPrestamo());
//            statement.executeUpdate();
//        }
//    }
//
//    // Leer todos los préstamos
//    public List<PrestamoDTO> obtenerTodosLosPrestamos() throws SQLException {
//        List<PrestamoDTO> prestamos = new ArrayList<>();
//        String sql = "SELECT * FROM Prestamo";
//        try (Statement statement = connection.createStatement();
//             ResultSet resultSet = statement.executeQuery(sql)) {
//            while (resultSet.next()) {
//                PrestamoDTO prestamo = new PrestamoDTO(
//                        resultSet.getString("legajoDocente"),
//                        resultSet.getString("codigoRecurso"),
//                        resultSet.getBoolean("estaPrestado"),
//                        resultSet.getDate("fechaPrestamo"),
//                        resultSet.getDate("horaPrestamo"),
//                        resultSet.getString("observacionPrestamo")
//                );
//                prestamos.add(prestamo);
//            }
//        }
//        return prestamos;
//    }
//
//    // Actualizar un préstamo
//    public void actualizarPrestamo(PrestamoDTO prestamo) throws SQLException {
//        String sql = "UPDATE Prestamo SET estaPrestado = ?, fechaPrestamo = ?, horaPrestamo = ?, observacionPrestamo = ? WHERE legajoDocente = ? AND codigoRecurso = ?";
//        try (PreparedStatement statement = connection.prepareStatement(sql)) {
//            statement.setBoolean(1, prestamo.isEstaPrestado());
//            statement.setDate(2, prestamo.getFechaPrestamo());
//            statement.setDate(3, prestamo.getHoraPrestamo());
//            statement.setString(4, prestamo.getObservacionPrestamo());
//            statement.setString(5, prestamo.getLegajoDocente());
//            statement.setString(6, prestamo.getCodigoRecurso());
//            statement.executeUpdate();
//        }
//    }
//
//    // Eliminar un préstamo
//    public void eliminarPrestamo(String legajoDocente, String codigoRecurso) throws SQLException {
//        String sql = "DELETE FROM Prestamo WHERE legajoDocente = ? AND codigoRecurso = ?";
//        try (PreparedStatement statement = connection.prepareStatement(sql)) {
//            statement.setString(1, legajoDocente);
//            statement.setString(2, codigoRecurso);
//            statement.executeUpdate();
//        }
//    }
//
//    // Obtener un préstamo por legajo y código de recurso
//    public PrestamoDTO obtenerPrestamoPorLegajoYRecurso(String legajoDocente, String codigoRecurso) throws SQLException {
//        String sql = "SELECT * FROM Prestamo WHERE legajoDocente = ? AND codigoRecurso = ?";
//        try (PreparedStatement statement = connection.prepareStatement(sql)) {
//            statement.setString(1, legajoDocente);
//            statement.setString(2, codigoRecurso);
//            try (ResultSet resultSet = statement.executeQuery()) {
//                if (resultSet.next()) {
//                    return new PrestamoDTO(
//                            resultSet.getString("legajoDocente"),
//                            resultSet.getString("codigoRecurso"),
//                            resultSet.getBoolean("estaPrestado"),
//                            resultSet.getDate("fechaPrestamo"),
//                            resultSet.getDate("horaPrestamo"),
//                            resultSet.getString("observacionPrestamo")
//                    );
//                }
//            }
//        }
//        return null; // No se encontró el préstamo
//    }
//}
