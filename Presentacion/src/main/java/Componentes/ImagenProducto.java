/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Componentes;

import Estilos.PaletaColores;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * Clase de apoyo para cargar y escalar imágenes de productos.
 *
 * @author regina, mariana e isaac
 */
public class ImagenProducto {

    /**
     * Crea un JLabel con la imagen del producto escalada.
     * Si no existe la ruta o falla la carga, devuelve un placeholder.
     *
     * @param rutaImagen Ruta de la imagen.
     * @param ancho Ancho deseado.
     * @param alto Alto deseado.
     * @return JLabel configurado con imagen o placeholder.
     */
    public static JLabel crearLabelImagenProducto(String rutaImagen, int ancho, int alto) {
        JLabel lblImagen = new JLabel();
        lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
        lblImagen.setVerticalAlignment(SwingConstants.CENTER);
        lblImagen.setOpaque(true);
        lblImagen.setBackground(PaletaColores.BLANCO);
        lblImagen.setBorder(BorderFactory.createLineBorder(PaletaColores.LINEA_SUAVE, 1));
        lblImagen.setText("");

        try {
            if (rutaImagen != null && !rutaImagen.isBlank()) {
                File archivo = new File(rutaImagen);

                if (archivo.exists()) {
                    ImageIcon iconoOriginal = new ImageIcon(rutaImagen);
                    Image imagenEscalada = escalarImagen(iconoOriginal.getImage(), ancho, alto);
                    lblImagen.setIcon(new ImageIcon(imagenEscalada));
                    return lblImagen;
                }
            }
        } catch (Exception e) {
        }

        lblImagen.setText("Sin imagen");
        return lblImagen;
    }

    /**
     * Escala una imagen manteniendo buena calidad visual.
     *
     * @param imagen Imagen original.
     * @param ancho Ancho destino.
     * @param alto Alto destino.
     * @return Imagen escalada.
     */
    private static Image escalarImagen(Image imagen, int ancho, int alto) {
        BufferedImage buffer = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = buffer.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.drawImage(imagen, 0, 0, ancho, alto, null);
        g2.dispose();

        return buffer;
    }
}