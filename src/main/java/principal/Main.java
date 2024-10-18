package principal;

import javax.swing.SwingUtilities;
import vista.VentanaPrincipal;

public class Main {

    /**
     * Este metodo muestra la ventana principal por pantalla.
     * @param args 
     */

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VentanaPrincipal().setVisible(true);
        });
    }
}
