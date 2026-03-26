package Recursos;

import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 *
 * @author Mariana
 */
public class CargadorRecursos {
    
    public static ImageIcon cargarIcono(String ruta, int ancho, int alto) {
        URL url = CargadorRecursos.class.getResource(ruta);

        if (url == null) {
            return null;
        }

        Image imagen = new ImageIcon(url).getImage()
                .getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);

        return new ImageIcon(imagen);
    }
    
}
