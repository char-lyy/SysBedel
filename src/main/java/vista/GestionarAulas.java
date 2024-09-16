package vista;

import javax.swing.*;
import java.awt.*;

public class GestionarAulas extends JFrame {

    public GestionarAulas() {
        setTitle("Gestionar Aulas");
        setSize(400, 300);
        setLayout(new GridLayout(3, 2));
        setLocationRelativeTo(null);

        JLabel labelNumeroAula = new JLabel("Número de Aula:");
        JTextField textNumeroAula = new JTextField();

        JLabel labelCondiciones = new JLabel("Condiciones Ambientales:");
        JTextArea textCondiciones = new JTextArea();

        JButton buttonRegistrar = new JButton("Registrar Condiciones");

        add(labelNumeroAula);
        add(textNumeroAula);
        add(labelCondiciones);
        add(textCondiciones);
        add(buttonRegistrar);
    }
}

