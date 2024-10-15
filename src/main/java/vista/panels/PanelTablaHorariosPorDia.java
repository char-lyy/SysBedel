package vista.panels;

import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import paqueteDAO.AulaDAO;
import paqueteDTO.AulaDTO;

/**
 *
 * @author carlos
 */
public class PanelTablaHorariosPorDia {

    private JTable table;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPanel;

    public PanelTablaHorariosPorDia() {

        ControladorTablaHorarios controller = new ControladorTablaHorarios();

        String[] columnNames = controller.generarColumnas();
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);

        controller.cargarHoras(tableModel);
                
        table.setCellSelectionEnabled(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        scrollPanel = new JScrollPane(table);
    }

    public JTable getTable() {
        return table;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public JScrollPane getScrollPanel() {
        return scrollPanel;
    }

    public void setScrollPanel(JScrollPane scrollPanel) {
        this.scrollPanel = scrollPanel;
    }
}

class ControladorTablaHorarios {

    public ControladorTablaHorarios() {
    }

    public String[] generarColumnas() {

        String[] aux;
        try {
            AulaDAO aulaDAO = new AulaDAO();

            AulaDTO[] aulas = aulaDAO.obtenerAulas().toArray(new AulaDTO[0]);
            aux = new String[aulas.length + 1]; // Aumentar tamaño para incluir "Hora"

            aux[0] = "Hora";
            int i = 1;
            for (AulaDTO aula : aulas) {
                aux[i] = String.valueOf(aula.getNroAula());
                i++;
            }
            return aux;

        } catch (SQLException ex) {
            Logger.getLogger(ControladorTablaHorarios.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public void cargarHoras(DefaultTableModel tableModel) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime startTime = LocalTime.of(6, 0); 
        LocalTime endTime = LocalTime.of(22, 0);  

        while (!startTime.isAfter(endTime)) {
            Object[] fila = new Object[tableModel.getColumnCount()];
            fila[0] = startTime.format(timeFormatter); 
            for (int i = 1; i < fila.length; i++) {
                fila[i] = "";
            }
            tableModel.addRow(fila);
            startTime = startTime.plusMinutes(30); 
        }
    }
}
