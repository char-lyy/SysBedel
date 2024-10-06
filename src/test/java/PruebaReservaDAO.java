//import java.sql.SQLException;
//import paqueteDAO.ReservaDAO;
//import paqueteDTO.FechaDTO;
//import paqueteDTO.ReservaDTO;
//import paqueteDTO.TiempoDTO;
//import principal.ConnectionManager;
//
//public class PruebaReservaDAO {
//    
//    public static void main(String[] args) throws SQLException {
//        
//        FechaDTO f01 = new FechaDTO(23, 9, 2024);
//        FechaDTO f02 = new FechaDTO(24, 8, 2024);
//        FechaDTO f03 = new FechaDTO(25, 8, 2024);
//        FechaDTO f04 = new FechaDTO(26, 8, 2024);
//        FechaDTO f05 = new FechaDTO(27, 8, 2024);
////        FechaDTO f03 = new FechaDTO(28, 8, 2024);
//    
//        TiempoDTO t01 = new TiempoDTO(8, 0);
//        TiempoDTO t02 = new TiempoDTO(9, 0);
//        TiempoDTO t03 = new TiempoDTO(10, 0);
//        
//        // Prueba de insercion:
//        
//        ReservaDTO r01 = new ReservaDTO(1, 1, 24, true, t01, t03, f01, f01);
//        ReservaDTO r02 = new ReservaDTO(2, 1, 24, true, t01, t03, f01, f01);
//        
//        ConnectionManager cm = new ConnectionManager();
//        ReservaDAO reservaDAO = new ReservaDAO(cm.getConnection());
//        
////        System.out.println(cm.toString());
////        System.out.println(reservaDAO.toString());
//        reservaDAO.insertarReserva(r01);
//        reservaDAO.insertarReserva(r02);
//    }
//    
//}
