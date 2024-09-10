package paqueteDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import paqueteDTO.AulaDTO;


public class AulaDAO {
   private Connection connection;
   private final String SQL_INSERTAR = "INSERT INTO Aula(numeroAula, capacidadAula, llaveAsignada, BooleanAula) VALUES (?,?,?,?)";
   private final String SQL_ACTUALIZAR = "";
   private final String SQL_BORRAR = "";
   private final String SQL_LEER = "";
   

    public AulaDAO(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }
   
   public boolean agregarAula(AulaDTO aulaNueva){
       try(PreparedStatement ps = connection.prepareStatement(SQL_INSERTAR)){
           ps.setInt(1, aulaNueva.getNumeroAulaDTO());
           ps.setInt(2, aulaNueva.getCapacidadAulaDTO());
           ps.setString(3, aulaNueva.getLlaveAsignada());
           ps.setBoolean(4, true);
           return ps.executeUpdate() > 0;
           
       }catch(SQLException e){
           e.printStackTrace();
           return false;
       }
   }
}