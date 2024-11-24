/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista.frames;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import javax.swing.JFrame;
import services.ServicioRecuperacion;
import vista.panels.PanelCambiarContraseña;

/**
 *
 * @author carlos
 */
public class FrameCambiarContraseña extends JFrame {

    public FrameCambiarContraseña(ServicioRecuperacion servicioRecuperacion) {
        
        PanelCambiarContraseña panelCambiarContraseña = new PanelCambiarContraseña(this, servicioRecuperacion);

        setTitle("Cambiar Contraseña");

        Toolkit display001 = Toolkit.getDefaultToolkit();

        Dimension displaySize = display001.getScreenSize();

        setBounds(displaySize.width / 3,
                displaySize.height / 3,
                (int) (displaySize.width / 3),
                (int) (displaySize.height / 4));

        setLayout(new GridLayout(1, 1));

        setVisible(true);

        add(panelCambiarContraseña);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
