package vista;

import paqueteDAO.AulaDAO;
import paqueteDTO.AulaDTO;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class GestionarAulas extends JFrame {
    private JTextField textNumeroAula;
    private JTextField textCapacidad;
    private JCheckBox checkBoxOcupada;
    private JTextField textObservaciones; // Campo para observaciones
    private JTextField textResponsable; // Campo para responsable
    private JTextField textFecha; // Campo para fecha
    private JTextField textHora; // Campo para hora
    private JTable tableAulas;
    private DefaultTableModel tableModel;
    private AulaDAO aulaDAO;

    public GestionarAulas() {
        aulaDAO = new AulaDAO(); // Inicializar el AulaDAO
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

        // Campo para observaciones
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel labelObservaciones = new JLabel("Observaciones:");
        centralPanel.add(labelObservaciones, gbc);

        gbc.gridx = 1;
        textObservaciones = new JTextField(20);
        centralPanel.add(textObservaciones, gbc);

        // Campo para responsable
        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel labelResponsable = new JLabel("Responsable:");
        centralPanel.add(labelResponsable, gbc);

        gbc.gridx = 1;
        textResponsable = new JTextField(20);
        centralPanel.add(textResponsable, gbc);

        // Campo para fecha
        gbc.gridx = 0;
        gbc.gridy = 5;
        JLabel labelFecha = new JLabel("Fecha (dd/MM/yyyy):");
        centralPanel.add(labelFecha, gbc);

        gbc.gridx = 1;
        textFecha = new JTextField(20);
        centralPanel.add(textFecha, gbc);

        // Campo para hora
        gbc.gridx = 0;
        gbc.gridy = 6;
        JLabel labelHora = new JLabel("Hora (HH:mm):");
        centralPanel.add(labelHora, gbc);

        gbc.gridx = 1;
        textHora = new JTextField(20);
        centralPanel.add(textHora, gbc);

        // Botón para registrar el aula
        gbc.gridx = 0;
        gbc.gridy = 7;
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
        tableModel = new DefaultTableModel(new String[]{"Número de Aula", "Capacidad", "Ocupada", "Observaciones", "Responsable", "Fecha", "Hora"}, 0);
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
                    textObservaciones.setText(tableModel.getValueAt(selectedRow, 3).toString());
                    textResponsable.setText(tableModel.getValueAt(selectedRow, 4).toString());
                    textFecha.setText(tableModel.getValueAt(selectedRow, 5).toString());
                    textHora.setText(tableModel.getValueAt(selectedRow, 6).toString());
                }
            }
        });

        // Panel para botón de modificar
        JPanel buttonPanel = new JPanel();
        JButton buttonModificar = new JButton("Modificar Aula");
        buttonPanel.add(buttonModificar);

        // Acción para modificar una aula seleccionada
        buttonModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarAula();
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

    private void registrarAula() {
    try {
        int nroAula = Integer.parseInt(textNumeroAula.getText());
        int capacidad = Integer.parseInt(textCapacidad.getText());
        boolean ocupada = checkBoxOcupada.isSelected();
        String ocupacionTexto = ocupada ? "Sí" : "No";
        String observaciones = textObservaciones.getText().trim();
        String responsable = textResponsable.getText().trim();
        String fecha = textFecha.getText().trim();
        String hora = textHora.getText().trim();

        // Verificar si el aula está en el rango permitido
        if (nroAula < 20 || nroAula > 31) {
            JOptionPane.showMessageDialog(this, "El aula debe estar en el rango entre 20 y 31.");
            return; // Salir del método
        }

        // Verificar si hay observaciones y se intenta marcar el aula como ocupada
        if (!observaciones.isEmpty() && ocupada) {
            JOptionPane.showMessageDialog(this, "No se puede ocupar el aula si tiene observaciones.");
            return; // Salir del método
        }

        AulaDTO nuevaAula = new AulaDTO(nroAula, capacidad, ocupada, observaciones, responsable, fecha, hora);
        aulaDAO.registrarAula(nuevaAula);
        tableModel.addRow(new Object[]{nroAula, capacidad, ocupacionTexto, observaciones, responsable, fecha, hora});
        limpiarCampos();
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Por favor, ingrese valores válidos.");
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error al registrar el aula: " + e.getMessage());
    }
}

private void modificarAula() {
    int selectedRow = tableAulas.getSelectedRow();
    if (selectedRow != -1) {
        try {
            int nroAula = Integer.parseInt(textNumeroAula.getText());
            int capacidad = Integer.parseInt(textCapacidad.getText());
            boolean ocupada = checkBoxOcupada.isSelected();
            String ocupacionTexto = ocupada ? "Sí" : "No";
            String observaciones = textObservaciones.getText().trim();
            String responsable = textResponsable.getText().trim();
            String fecha = textFecha.getText().trim();
            String hora = textHora.getText().trim();

            // Verificar si el aula está en el rango permitido
            if (nroAula < 20 || nroAula > 31) {
                JOptionPane.showMessageDialog(this, "El aula debe estar en el rango entre 20 y 31.");
                return; // Salir del método
            }

            // Verificar si hay observaciones y se intenta marcar el aula como ocupada
            if (!observaciones.isEmpty() && ocupada) {
                JOptionPane.showMessageDialog(this, "No se puede ocupar el aula si tiene observaciones.");
                return; // Salir del método
            }

            AulaDTO aulaModificada = new AulaDTO(nroAula, capacidad, ocupada, observaciones, responsable, fecha, hora);
            aulaDAO.modificarAula(aulaModificada);
            tableModel.setValueAt(nroAula, selectedRow, 0);
            tableModel.setValueAt(capacidad, selectedRow, 1);
            tableModel.setValueAt(ocupacionTexto, selectedRow, 2);
            tableModel.setValueAt(observaciones, selectedRow, 3);
            tableModel.setValueAt(responsable, selectedRow, 4);
            tableModel.setValueAt(fecha, selectedRow, 5);
            tableModel.setValueAt(hora, selectedRow, 6);
            limpiarCampos();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese valores válidos.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al modificar el aula: " + e.getMessage());
        }
    }
}


    private void cargarAulas() {
        try {
            List<AulaDTO> aulas = aulaDAO.obtenerAulas();
            for (AulaDTO aula : aulas) {
                tableModel.addRow(new Object[]{aula.getNroAula(), aula.getCapacidad(), aula.isOcupada(), aula.getObservaciones(), aula.getResponsable(), aula.getFecha(), aula.getHora()});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar las aulas: " + e.getMessage());
        }
    }

    private void limpiarCampos() {
        textNumeroAula.setText("");
        textCapacidad.setText("");
        checkBoxOcupada.setSelected(false);
        textObservaciones.setText("");
        textResponsable.setText("");
        textFecha.setText("");
        textHora.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GestionarAulas ventana = new GestionarAulas();
            ventana.setVisible(true);
        });
    }
}
