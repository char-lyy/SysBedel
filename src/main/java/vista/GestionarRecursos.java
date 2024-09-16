package vista;

import javax.swing.*;
import java.awt.*;

public class GestionarRecursos extends JFrame {

    public GestionarRecursos() {
        setTitle("Gestionar Recursos");
        setSize(400, 300);
        setLayout(new GridLayout(3, 2));
        setLocationRelativeTo(null);

        JLabel labelNombreRecurso = new JLabel("Nombre del Recurso:");
        JTextField textNombreRecurso = new JTextField();

        JLabel labelCantidad = new JLabel("Cantidad:");
        JTextField textCantidad = new JTextField();

        JButton buttonRegistrar = new JButton("Registrar Recurso");
        JButton buttonConsultar = new JButton("Consultar Recursos");

        add(labelNombreRecurso);
        add(textNombreRecurso);
        add(labelCantidad);
        add(textCantidad);
        add(buttonRegistrar);
        add(buttonConsultar);
    }
}

