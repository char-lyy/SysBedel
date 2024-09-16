package vista;

import javax.swing.*;
import java.awt.*;

public class GestionarActividades extends JFrame {

    public GestionarActividades() {
        setTitle("Gestionar Actividades Académicas");
        setSize(400, 300);
        setLayout(new GridLayout(3, 2));
        setLocationRelativeTo(null);

        JLabel labelActividad = new JLabel("Nombre Actividad:");
        JTextField textActividad = new JTextField();

        JLabel labelAula = new JLabel("Aula:");
        JTextField textAula = new JTextField();

        JLabel labelHorario = new JLabel("Horario:");
        JTextField textHorario = new JTextField();

        JButton buttonGuardar = new JButton("Guardar");
        JButton buttonConsultar = new JButton("Consultar Disponibilidad");

        add(labelActividad);
        add(textActividad);
        add(labelAula);
        add(textAula);
        add(labelHorario);
        add(textHorario);
        add(buttonGuardar);
        add(buttonConsultar);
    }
}

