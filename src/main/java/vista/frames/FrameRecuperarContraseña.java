package vista.frames;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import javax.swing.JFrame;
import vista.panels.PanelLogin;
import vista.panels.PanelRecuperarContraseña;

/**
 *
 * @author carlos
 */
public class FrameRecuperarContraseña extends JFrame{

    public FrameRecuperarContraseña() {

        PanelRecuperarContraseña panelRecuperarContraseña = new PanelRecuperarContraseña(this);

        setTitle("Recuperar Contraseña");

        Toolkit display001 = Toolkit.getDefaultToolkit();

        Dimension displaySize = display001.getScreenSize();

        setBounds(displaySize.width / 3,
                displaySize.height / 3,
                (int) (displaySize.width / 3),
                (int) (displaySize.height / 4));

        setLayout(new GridLayout(1, 1));

        setVisible(true);

        add(panelRecuperarContraseña);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
