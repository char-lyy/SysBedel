package principal;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import paqueteDAO.NuevaReservaDAO;
import paqueteDTO.NuevaReservaDTO;
import utilidades.ConnectionManager;
import utilidades.Fecha;
import utilidades.Tiempo;

public class Main {

    public static void main(String[] args) {

//        try {
//            Tiempo horaInicio = new Tiempo(10, 0);
//            Tiempo horaFin = new Tiempo(12, 0);
//            Fecha fechaActividad = new Fecha(7, 7, 2025);
//
//            NuevaReservaDTO reservaDTO = new NuevaReservaDTO(30, horaInicio, horaFin, fechaActividad, "Cumple", "Carlos");
//            NuevaReservaDAO reservaDAO = new NuevaReservaDAO(ConnectionManager.getConnection());
//            reservaDAO.insertarReserva(reservaDTO);
//        } catch (SQLException ex) {
//            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
}
