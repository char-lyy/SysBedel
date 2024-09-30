package vista;

import javax.swing.*;

public class VentanaPrincipal extends JFrame {

    public VentanaPrincipal() {
        setTitle("Sistema de Gestión Académica");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();

        // Menú de Gestión de Actividades Académicas
        JMenu menuActividades = new JMenu("Actividades Académicas");
        JMenuItem itemGestionarActividades = new JMenuItem("Gestionar Actividades");
        itemGestionarActividades.addActionListener(e -> new GestionarActividades().setVisible(true));
        menuActividades.add(itemGestionarActividades);

        // Menú de Gestión de Informes
        JMenu menuInformes = new JMenu("Informes");
        JMenuItem itemGestionarInformes = new JMenuItem("Gestionar Informes");
        itemGestionarInformes.addActionListener(e -> new GestionarInformes().setVisible(true));
        menuInformes.add(itemGestionarInformes);

        // Menú de Gestión de Aulas
        JMenu menuAulas = new JMenu("Aulas");
        JMenuItem itemGestionarAulas = new JMenuItem("Gestionar Aulas");
        itemGestionarAulas.addActionListener(e -> new GestionarAulas().setVisible(true));
        menuAulas.add(itemGestionarAulas);

        // Menú de Gestión de Recursos
        JMenu menuRecursos = new JMenu("Recursos");
        JMenuItem itemGestionarRecursos = new JMenuItem("Gestionar Recursos");
        itemGestionarRecursos.addActionListener(e -> new GestionarRecursos().setVisible(true));
        menuRecursos.add(itemGestionarRecursos);

        menuBar.add(menuActividades);
        menuBar.add(menuInformes);
        menuBar.add(menuAulas);
        menuBar.add(menuRecursos);

        setJMenuBar(menuBar);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VentanaPrincipal().setVisible(true);
        });
    }
}

