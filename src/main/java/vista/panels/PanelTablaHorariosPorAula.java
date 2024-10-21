package vista.panels;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class PanelTablaHorariosPorAula {

    private JTable table;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPanel;

    public PanelTablaHorariosPorAula() {
        ControladorTablaHorariosPorAula controller = new ControladorTablaHorariosPorAula();

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

class ControladorTablaHorariosPorAula {

    public ControladorTablaHorariosPorAula() {
    }

    public String[] generarColumnas() {
        return new String[]{"Hora", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"};
    }

    public void cargarHoras(DefaultTableModel tableModel) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime startTime = LocalTime.of(6, 0); // Hora de inicio
        LocalTime endTime = LocalTime.of(22, 0);  // Hora de fin

        while (!startTime.isAfter(endTime)) {
            Object[] fila = new Object[tableModel.getColumnCount()];
            fila[0] = startTime.format(timeFormatter); // Hora
            for (int i = 1; i < fila.length; i++) {
                fila[i] = ""; // Celdas vacías para los días
            }
            tableModel.addRow(fila);
            startTime = startTime.plusMinutes(30); // Incremento de 30 minutos
        }
    }
}
