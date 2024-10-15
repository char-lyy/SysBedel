package vista.panels;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class PanelTablaHorariosPorAula extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPanel;

    public PanelTablaHorariosPorAula() {

        String[] columnNames = {"Hora", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado"};

        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);

        // Formato de tiempo para las celdas
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        // Generar las filas de la tabla con intervalos de 30 minutos entre las 6:00 y las 22:00
        LocalTime startTime = LocalTime.of(6, 0); // Hora de inicio
        LocalTime endTime = LocalTime.of(22, 0);  // Hora de fin

        while (!startTime.isAfter(endTime)) {
            // Agregar una fila con la hora y una celda vacía para la descripción
            tableModel.addRow(new Object[]{startTime.format(timeFormatter), ""});
            startTime = startTime.plusMinutes(30); // Incremento de 30 minutos
        }

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
