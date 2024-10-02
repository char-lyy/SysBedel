package vista;

import paqueteDTO.AulaDTO;
import principal.ConnectionManager;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GestionarAulas extends JFrame {
    private JTextField textNumeroAula;
    private JTextField textCapacidad;
    private JCheckBox checkBoxOcupada;
    private JTable tableAulas;
    private DefaultTableModel tableModel;
    
    /**
     * Constructor que configura la interfaz de la clase.
     */

    public GestionarAulas() {
        setTitle("Gestionar Aulas");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel centralPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Campo para número de aula
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel labelNumeroAula = new JLabel("Número de Aula:");
        centralPanel.add(labelNumeroAula, gbc);

        gbc.gridx = 1;
        textNumeroAula = new JTextField(20);
        centralPanel.add(textNumeroAula, gbc);

        // Campo para capacidad
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel labelCapacidad = new JLabel("Capacidad:");
        centralPanel.add(labelCapacidad, gbc);

        gbc.gridx = 1;
        textCapacidad = new JTextField(20);
        centralPanel.add(textCapacidad, gbc);

        // Checkbox para ocupada
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel labelOcupada = new JLabel("Ocupada:");
        centralPanel.add(labelOcupada, gbc);

        gbc.gridx = 1;
        checkBoxOcupada = new JCheckBox("¿Está ocupada?");
        centralPanel.add(checkBoxOcupada, gbc);

        // Botón para registrar el aula
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        JButton buttonRegistrar = new JButton("Registrar Aula");
        centralPanel.add(buttonRegistrar, gbc);

        // Acción para registrar el aula
        buttonRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarAula();
            }
        });

        // Tabla para mostrar las aulas
        tableModel = new DefaultTableModel(new String[]{"Número de Aula", "Capacidad", "Ocupada"}, 0);
        tableAulas = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableAulas);

        // Seleccionar una fila de la tabla
        tableAulas.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && tableAulas.getSelectedRow() != -1) {
                    int selectedRow = tableAulas.getSelectedRow();
                    textNumeroAula.setText(tableModel.getValueAt(selectedRow, 0).toString());
                    textCapacidad.setText(tableModel.getValueAt(selectedRow, 1).toString());
                    checkBoxOcupada.setSelected((Boolean) tableModel.getValueAt(selectedRow, 2));
                }
            }
        });

        // Panel para botones de modificar y cancelar
        JPanel buttonPanel = new JPanel();
        JButton buttonModificar = new JButton("Modificar Aula");
        JButton buttonCancelar = new JButton("Cancelar Aula");
        buttonPanel.add(buttonModificar);
        buttonPanel.add(buttonCancelar);

        // Acción para modificar una aula seleccionada
        buttonModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarAula();
            }
        });

        // Acción para cancelar una aula seleccionada
        buttonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelarAula();
            }
        });

        // Agregar componentes al mainPanel
        mainPanel.add(centralPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Cargar las aulas reservadas en la tabla
        cargarAulas();
    }
    
    /**
     * Método para registrar aula en la base de datos
     */
    private void registrarAula() {
        try {
            int nroAula = Integer.parseInt(textNumeroAula.getText());
            int capacidad = Integer.parseInt(textCapacidad.getText());
            boolean ocupada = checkBoxOcupada.isSelected();

            AulaDTO aula = new AulaDTO(nroAula, capacidad, ocupada);
            String query = "INSERT INTO aulas (numero_aula, capacidad, ocupada) VALUES (?, ?, ?)";

            try (Connection connection = ConnectionManager.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {

                statement.setInt(1, aula.getNroAula());
                statement.setInt(2, aula.getCapacidad());
                statement.setBoolean(3, aula.isOcupada());
                statement.executeUpdate();

                JOptionPane.showMessageDialog(this, "Aula registrada exitosamente.");
                cargarAulas();  // Refrescar la tabla
            }
        } catch (SQLException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error al registrar aula: " + ex.getMessage());
        }
    }
    
    /**
     * Método para cargar las aulas desde la base de datos
     */

    private void cargarAulas() {
        tableModel.setRowCount(0);  // Limpiar la tabla
        String query = "SELECT numero_aula, capacidad, ocupada FROM aulas";

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int nroAula = resultSet.getInt("numero_aula");
                int capacidad = resultSet.getInt("capacidad");
                boolean ocupada = resultSet.getBoolean("ocupada");

                tableModel.addRow(new Object[]{nroAula, capacidad, ocupada});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar aulas: " + ex.getMessage());
        }
    }
    
    /**
     * Método para modificar una aula seleccionada
     */

    private void modificarAula() {
        try {
            int nroAula = Integer.parseInt(textNumeroAula.getText());
            int capacidad = Integer.parseInt(textCapacidad.getText());
            boolean ocupada = checkBoxOcupada.isSelected();

            String query = "UPDATE aulas SET capacidad = ?, ocupada = ? WHERE numero_aula = ?";

            try (Connection connection = ConnectionManager.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {

                statement.setInt(1, capacidad);
                statement.setBoolean(2, ocupada);
                statement.setInt(3, nroAula);
                statement.executeUpdate();

                JOptionPane.showMessageDialog(this, "Aula modificada exitosamente.");
                cargarAulas();  // Refrescar la tabla
            }
        } catch (SQLException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error al modificar aula: " + ex.getMessage());
        }
    }
    
    /**
     * Método para cancelar una aula seleccionada
     */

    private void cancelarAula() {
        try {
            int nroAula = Integer.parseInt(textNumeroAula.getText());

            String query = "DELETE FROM aulas WHERE numero_aula = ?";

            try (Connection connection = ConnectionManager.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {

                statement.setInt(1, nroAula);
                statement.executeUpdate();

                JOptionPane.showMessageDialog(this, "Aula cancelada exitosamente.");
                cargarAulas();  // Refrescar la tabla
            }
        } catch (SQLException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error al cancelar aula: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GestionarAulas frame = new GestionarAulas();
            frame.setVisible(true);
        });
    }
}
