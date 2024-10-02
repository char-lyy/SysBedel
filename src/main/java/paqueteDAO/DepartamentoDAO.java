package paqueteDAO;

import paqueteDTO.DepartamentoDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartamentoDAO {

    private Connection connection;
    /**
     * Constructor que recibe una conexión a la base de datos
     * @param connection 
     */

    public DepartamentoDAO(Connection connection) {
        this.connection = connection;
    }
    
    /**
     * Método para agregar un nuevo departamento,
     * @param departamento
     * @return verdadero si se pudo agregar un nuevo departamento.
     */

    public boolean agregarDepartamento(DepartamentoDTO departamento) {
        String sql = "INSERT INTO departamento (codigoDepartamento, nombreDepartamento) VALUES (?, ?)";
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, departamento.getCodigoDepartamento());
            ps.setString(2, departamento.getNombreDepartamento());
            return ps.executeUpdate() > 0; // Devuelve true si se insertó correctamente
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            cerrarRecursos(ps, null);
        }
    }
    
    /**
     * Método para obtener un departamento por su código.
     * @param codigoDepartamento
     * @return verdadero si se pudo obtener un departamento por su codigo.
     */

    public DepartamentoDTO obtenerDepartamentoPorCodigo(int codigoDepartamento) {
        String sql = "SELECT * FROM departamento WHERE codigoDepartamento = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, codigoDepartamento);
            rs = ps.executeQuery();
            if (rs.next()) {
                return new DepartamentoDTO(
                    rs.getInt("codigoDepartamento"),
                    rs.getString("nombreDepartamento")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarRecursos(ps, rs);
        }
        return null; // Devuelve null si no se encontró el departamento
    }
    
    /**
     * Método para obtener todos los departamentos
     * @return todos los departamentos.
     */


    public List<DepartamentoDTO> obtenerTodosLosDepartamentos() {
        List<DepartamentoDTO> departamentos = new ArrayList<>();
        String sql = "SELECT * FROM departamento";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                DepartamentoDTO departamento = new DepartamentoDTO(
                    rs.getInt("codigoDepartamento"),
                    rs.getString("nombreDepartamento")
                );
                departamentos.add(departamento);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarRecursos(ps, rs);
        }
        return departamentos;
    }
    
    /**
     * Método para actualizar un departamento
     * @param departamento
     * @return verdadero si se pudo actualizar un departamento.
     */

    public boolean actualizarDepartamento(DepartamentoDTO departamento) {
        String sql = "UPDATE departamento SET nombreDepartamento = ? WHERE codigoDepartamento = ?";
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, departamento.getNombreDepartamento());
            ps.setInt(2, departamento.getCodigoDepartamento());
            return ps.executeUpdate() > 0; // Devuelve true si se actualizó correctamente
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            cerrarRecursos(ps, null);
        }
    }
    /**
     * Método para eliminar un departamento por su código
     * @param codigoDepartamento
     * @return verdadero si se elimino un departamento por su codigo.
     */

    public boolean eliminarDepartamento(int codigoDepartamento) {
        String sql = "DELETE FROM departamento WHERE codigoDepartamento = ?";
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, codigoDepartamento);
            return ps.executeUpdate() > 0; // Devuelve true si se eliminó correctamente
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            cerrarRecursos(ps, null);
        }
    }
    
    /**
     * Método para cerrar recursos como PreparedStatement y ResultSet
     * @param ps
     * @param rs 
     */

    private void cerrarRecursos(PreparedStatement ps, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close(); // Cerramos la conexión después de cada operación
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
