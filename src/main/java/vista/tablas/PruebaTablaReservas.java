package vista.tablas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PruebaTablaReservas {

    private JPanel panel = new JPanel(new BorderLayout()); // Usa BorderLayout para el panel
    private JTable table;
    private DefaultTableModel tableModel;

    public PruebaTablaReservas() {

        // Definir los nombres de las columnas
        String[] columnNames = {"ID", "Número Aula", "Descripción", "Responsable"};
        // Crear un modelo de tabla con datos de ejemplo
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);

        // Agregar algunos datos de ejemplo
        tableModel.addRow(new Object[]{1, 101, "Reserva 1", "Carlos"});
        tableModel.addRow(new Object[]{2, 102, "Reserva 2", "Ana"});

        // Permitir la selección de celdas
        table.setCellSelectionEnabled(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Listener para detectar selección de celdas
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                int column = table.getSelectedColumn();

                if (row != -1 && column != -1) {
                    // Obtener el valor actual de la celda seleccionada
                    Object currentValue = table.getValueAt(row, column);
                    String newValue = JOptionPane.showInputDialog("Modificar valor:", currentValue);

                    // Si el usuario no cancela y proporciona un nuevo valor, actualizar la celda
                    if (newValue != null && !newValue.trim().isEmpty()) {
                        table.setValueAt(newValue, row, column);
                    }
                }
            }
        });

        // Agregar la tabla a un JScrollPane para desplazamiento
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER); // Agrega solo el JScrollPane al panel
    }

    public JTable getTable() {
        return table;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public JPanel getPanel() {
        return panel;
    }
}
