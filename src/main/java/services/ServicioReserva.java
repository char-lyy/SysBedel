package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilidades.ConnectionManager;
import utilidades.Fecha;

/**
 *
 * @author carlos
 */
public class ServicioReserva {

    public ServicioReserva() {
    }

    public void validarInsersionReserva() {
        
    }

    public String[][] obtenerReservasPorDia(Fecha fecha) {

        try {

            LocalTime HORA_INICIO = LocalTime.of(6, 0);
            LocalTime HORA_FIN = LocalTime.of(22, 0);
            int INTERVALO_MINUTOS = 30;

            // Obtener todas las aulas para determinar el número de columnas
            List<Integer> aulas = obtenerNumerosAula();
            int numAulas = aulas.size();
            int numIntervalos = (int) ((HORA_FIN.toSecondOfDay() - HORA_INICIO.toSecondOfDay()) / (INTERVALO_MINUTOS * 60)) + 1;

            // Inicializar la matriz con filas para cada intervalo y columnas para cada aula
            String[][] matrizReservas = new String[numIntervalos][numAulas];

            // Inicializar todas las celdas como "Disponible"
            for (int i = 0; i < numIntervalos; i++) {
                for (int j = 0; j < numAulas; j++) {
                    matrizReservas[i][j] = "";
                }
            }

            String query = """
                       SELECT numeroAula, horaInicio, horaFin, descripcion
                       FROM Reserva
                       WHERE fechaActividad = ?;
                       """;

            Connection conn = ConnectionManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, fecha.toSqlDate().toString());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int numeroAula = rs.getInt("numeroAula");
                Time horaInicio = rs.getTime("horaInicio");
                Time horaFin = rs.getTime("horaFin");
                String descripcion = rs.getString("descripcion");

                int col = aulas.indexOf(numeroAula); // Índice de la columna según el aula
                if (col == -1) {
                    continue;
                }

                // Convertir las horas de inicio y fin en LocalTime
                LocalTime inicioReserva = horaInicio.toLocalTime();
                LocalTime finReserva = horaFin.toLocalTime();

                // Llenar la matriz para cada intervalo de 30 minutos
                LocalTime horaActual = HORA_INICIO;
                for (int fila = 0; fila < numIntervalos; fila++) {
                    if (!horaActual.isBefore(inicioReserva) && horaActual.isBefore(finReserva)) {
                        matrizReservas[fila][col] = (descripcion == null || descripcion.trim().isEmpty()) ? "" : descripcion;
                    }
                    horaActual = horaActual.plusMinutes(INTERVALO_MINUTOS);
                }
            }

            return matrizReservas;

        } catch (SQLException ex) {
            Logger.getLogger(ServicioReserva.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private List<Integer> obtenerNumerosAula() {
        try {
            List<Integer> aulas = new ArrayList<>();
            String query = "SELECT numeroAula FROM Aula ORDER BY numeroAula";

            Connection conn = ConnectionManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                aulas.add(rs.getInt("numeroAula"));
            }

            return aulas;
        } catch (SQLException ex) {
            Logger.getLogger(ServicioReserva.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

//    public static void main(String[] args) {
//        ServicioReserva sv = new ServicioReserva();
//        String[][] matriz = sv.obtenerReservasPorDia(new Fecha(7, 7, 2025));
//        for (String[] matriz1 : matriz) {
//            // Itera por cada fila
//            for (String matriz11 : matriz1) {
//                // Itera por cada columna en la fila
//                System.out.print(matriz11 + "\t"); // Imprime cada valor separado por tabulaciones
//            }
//            System.out.println(); // Salto de línea al final de cada fila
//        }
//    }
}
