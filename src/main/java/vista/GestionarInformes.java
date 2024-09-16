package vista;

import javax.swing.*;
import java.awt.*;

public class GestionarInformes extends JFrame {

    public GestionarInformes() {
        setTitle("Gestionar Informes");
        setSize(400, 300);
        setLayout(new GridLayout(4, 2));
        setLocationRelativeTo(null);

        JLabel labelAula = new JLabel("Número de Aula:");
        JTextField textAula = new JTextField();

        JLabel labelDescripcion = new JLabel("Descripción del Problema:");
        JTextArea textDescripcion = new JTextArea();

        JButton buttonGenerar = new JButton("Generar Informe");

        add(labelAula);
        add(textAula);
        add(labelDescripcion);
        add(textDescripcion);
        add(new JLabel()); // Espacio vacío
        add(buttonGenerar);
    }
}
