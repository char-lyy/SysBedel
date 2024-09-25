package principal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/sysbedel";
    private static final String USER = "root"; // Cambia esto por tu usuario de MySQL
    private static final String PASSWORD = "7707"; // Cambia esto por tu contraseña de MySQL
    private Connection connection;

    public ConnectionManager() {
        try {
            // Cargar el driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establecer la conexión
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            System.out.println("Conexión establecida con éxito.");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC no encontrado: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error de conexión a la base de datos: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void close() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Conexión cerrada.");
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ConnectionManager{");
        sb.append("connection=").append(connection);
        sb.append('}');
        return sb.toString();
    }
    
    
}

