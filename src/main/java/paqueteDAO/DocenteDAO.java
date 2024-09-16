package paqueteDAO;

import paqueteDTO.DocenteDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DocenteDAO {

    private Connection connection;

    // Constructor que recibe una conexión a la base de datos
    public DocenteDAO(Connection connection) {
        this.connection = connection;
    }

    // Método para agregar un nuevo docente
    public boolean agregarDocente(DocenteDTO docente) {
        String sql = "INSERT INTO docente (legajoDocente, nombreDocente, apellidoDocente, mailDocente, estadoDocente, tituloDocente, telefonoDocente) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, docente.getLegajoDocente());
            ps.setString(2, docente.getNombreDocente());
            ps.setString(3, docente.getApellidoDocente());
            ps.setString(4, docente.getMailDocente());
            ps.setBoolean(5, docente.getEstadoDocente());
            ps.setString(6, docente.getTituloDocente());
            ps.setInt(7, docente.getTelefonoDocente());

            return ps.executeUpdate() > 0; // Devuelve true si se insertó correctamente
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para obtener un docente por su legajo
    public DocenteDTO obtenerDocentePorLegajo(int legajoDocente) {
        String sql = "SELECT * FROM docente WHERE legajoDocente = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, legajoDocente);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new DocenteDTO(
                    rs.getInt("legajoDocente"),
                    rs.getString("nombreDocente"),
                    rs.getString("apellidoDocente"),
                    rs.getString("mailDocente"),
                    rs.getBoolean("estadoDocente"),
                    rs.getString("tituloDocente"),
                    rs.getInt("telefonoDocente")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Devuelve null si no se encontró el docente
    }

    // Método para obtener todos los docentes
    public List<DocenteDTO> obtenerTodosLosDocentes() {
        List<DocenteDTO> docentes = new ArrayList<>();
        String sql = "SELECT * FROM docente";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DocenteDTO docente = new DocenteDTO(
                    rs.getInt("legajoDocente"),
                    rs.getString("nombreDocente"),
                    rs.getString("apellidoDocente"),
                    rs.getString("mailDocente"),
                    rs.getBoolean("estadoDocente"),
                    rs.getString("tituloDocente"),
                    rs.getInt("telefonoDocente")
                );
                docentes.add(docente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return docentes;
    }

    // Método para actualizar un docente
    public boolean actualizarDocente(DocenteDTO docente) {
        String sql = "UPDATE docente SET nombreDocente = ?, apellidoDocente = ?, mailDocente = ?, estadoDocente = ?, tituloDocente = ?, telefonoDocente = ? WHERE legajoDocente = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, docente.getNombreDocente());
            ps.setString(2, docente.getApellidoDocente());
            ps.setString(3, docente.getMailDocente());
            ps.setBoolean(4, docente.getEstadoDocente());
            ps.setString(5, docente.getTituloDocente());
            ps.setInt(6, docente.getTelefonoDocente());
            ps.setInt(7, docente.getLegajoDocente());

            return ps.executeUpdate() > 0; // Devuelve true si se actualizó correctamente
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para eliminar un docente por su legajo
    public boolean eliminarDocente(int legajoDocente) {
        String sql = "DELETE FROM docente WHERE legajoDocente = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, legajoDocente);
            return ps.executeUpdate() > 0; // Devuelve true si se eliminó correctamente
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
}
