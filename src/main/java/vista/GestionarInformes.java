package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import principal.ConnectionManager;

public class GestionarInformes extends JFrame {

    private DefaultTableModel tableModel;
    private JTable tableInformes;

    public GestionarInformes() {
        setTitle("Gestionar Informes");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear panel principal con BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Márgenes

        // Panel central con GridBagLayout para ordenar los componentes
        JPanel centralPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Espacio entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Etiqueta y campo de texto para el número de aula
        JLabel labelAula = new JLabel("Número de Aula:");
        JTextField textAula = new JTextField(15);
        centralPanel.add(labelAula, gbc);

        gbc.gridx = 1;
        centralPanel.add(textAula, gbc);

        // Etiqueta y área de texto para la descripción del problema
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel labelDescripcion = new JLabel("Descripción del Problema:");
        centralPanel.add(labelDescripcion, gbc);

        gbc.gridx = 1;
        JTextArea textDescripcion = new JTextArea(5, 15);
        JScrollPane scrollDescripcion = new JScrollPane(textDescripcion); // Scroll para el JTextArea
        centralPanel.add(scrollDescripcion, gbc);

        // Botón para generar el informe
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton buttonGenerar = new JButton("Generar Informe");
        centralPanel.add(buttonGenerar, gbc);

        // Tabla para mostrar los informes registrados
        tableModel = new DefaultTableModel(new Object[]{"Número de Aula", "Descripción"}, 0);
        tableInformes = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(tableInformes);

        // Acción para generar el informe
        buttonGenerar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int nroAula = Integer.parseInt(textAula.getText());
                    String descripcion = textDescripcion.getText();

                    if (!descripcion.isEmpty() && nroAula > 0) {
                        generarInforme(nroAula, descripcion);
                        cargarInformes(); // Refrescar la tabla después de generar un informe
                    } else {
                        JOptionPane.showMessageDialog(null, "Por favor, ingresa valores válidos.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingresa un número de aula válido.");
                }
            }
        });

        // Agregar el panel central y la tabla al panel principal
        mainPanel.add(centralPanel, BorderLayout.NORTH);
        mainPanel.add(tableScrollPane, BorderLayout.CENTER); // Agregar la tabla al centro

        // Añadir el panel principal a la ventana
        add(mainPanel);

        // Cargar los informes al iniciar la ventana
        cargarInformes();
    }

    // Método para generar un informe en la base de datos
    private void generarInforme(int nroAula, String descripcion) {
        String query = "INSERT INTO informes (numero_aula, descripcion) VALUES (?, ?)";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, nroAula);
            statement.setString(2, descripcion);
            statement.executeUpdate();

            JOptionPane.showMessageDialog(this, "Informe generado exitosamente.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al generar informe: " + ex.getMessage());
        }
    }

    // Método para cargar los informes de la base de datos en la tabla
    private void cargarInformes() {
        String query = "SELECT numero_aula, descripcion FROM informes";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            // Limpiar el modelo de la tabla antes de agregar nuevos datos
            tableModel.setRowCount(0);

            // Agregar filas a la tabla con los datos de la base de datos
            while (resultSet.next()) {
                int numeroAula = resultSet.getInt("numero_aula");
                String descripcion = resultSet.getString("descripcion");
                tableModel.addRow(new Object[]{numeroAula, descripcion});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar los informes: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GestionarInformes frame = new GestionarInformes();
            frame.setVisible(true);
        });
    }
}
