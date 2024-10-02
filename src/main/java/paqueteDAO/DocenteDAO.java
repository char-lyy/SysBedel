package paqueteDAO;

import paqueteDTO.DocenteDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DocenteDAO {
    private Connection connection;
    
    /**
     * Constructor de la clase que permite la conexion con la Base de Datos.
     * @param connection 
     */

    public DocenteDAO(Connection connection) {
        this.connection = connection;
    }
    /**
     * Crear un nuevo docente
     * @param docente
     * @throws SQLException 
     */

    public void crearDocente(DocenteDTO docente) throws SQLException {
        String sql = "INSERT INTO docentes (legajoDocente, nombreDocente, apellidoDocente, mailDocente, estadoDocente, tituloDocente, telefonoDocente) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, docente.getLegajo());
            statement.setString(2, docente.getNombre());
            statement.setString(3, docente.getApellido());
            statement.setString(4, docente.getMail());
            statement.setBoolean(5, docente.isActivo());
            statement.setString(6, docente.getTitulo());
            statement.setString(7, docente.getTelefono());
            statement.executeUpdate();
        }
    }
    
    /**
     * Este metodo lee todos los docentes
     * @return los docentes ingresados.
     * @throws SQLException 
     */


    public List<DocenteDTO> obtenerTodosLosDocentes() throws SQLException {
        List<DocenteDTO> docentes = new ArrayList<>();
        String sql = "SELECT * FROM docentes";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                DocenteDTO docente = new DocenteDTO(
                        resultSet.getString("legajoDocente"),
                        resultSet.getString("nombreDocente"),
                        resultSet.getString("apellidoDocente"),
                        resultSet.getString("mailDocente"),
                        resultSet.getBoolean("estadoDocente"),
                        resultSet.getString("tituloDocente"),
                        resultSet.getString("telefonoDocente")
                );
                docentes.add(docente);
            }
        }
        return docentes;
    }
    
    /**
     * Metodo para Actualizar un docente 
     * @param docente
     * @throws SQLException 
     */

    public void actualizarDocente(DocenteDTO docente) throws SQLException {
        String sql = "UPDATE docentes SET nombreDocente = ?, apellidoDocente = ?, mailDocente = ?, estadoDocente = ?, tituloDocente = ?, telefonoDocente = ? WHERE legajoDocente = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, docente.getNombre());
            statement.setString(2, docente.getApellido());
            statement.setString(3, docente.getMail());
            statement.setBoolean(4, docente.isActivo());
            statement.setString(5, docente.getTitulo());
            statement.setString(6, docente.getTelefono());
            statement.setString(7, docente.getLegajo());
            statement.executeUpdate();
        }
    }
    
    /**
     * Metodo para eliminar un docente.
     * @param legajo
     * @throws SQLException 
     */

    public void eliminarDocente(String legajo) throws SQLException {
        String sql = "DELETE FROM docentes WHERE legajoDocente = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, legajo);
            statement.executeUpdate();
        }
    }
    
    /**
     * Obtener un docente por legajo
     * @param legajo
     * @return el docente por legajo.
     * @throws SQLException 
     */
    public DocenteDTO obtenerDocentePorLegajo(String legajo) throws SQLException {
        String sql = "SELECT * FROM docentes WHERE legajoDocente = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, legajo);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new DocenteDTO(
                            resultSet.getString("legajoDocente"),
                            resultSet.getString("nombreDocente"),
                            resultSet.getString("apellidoDocente"),
                            resultSet.getString("mailDocente"),
                            resultSet.getBoolean("estadoDocente"),
                            resultSet.getString("tituloDocente"),
                            resultSet.getString("telefonoDocente")
                    );
                }
            }
        }
        return null; // No se encontró el docente
    }
}
