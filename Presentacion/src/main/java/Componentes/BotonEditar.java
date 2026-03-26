package Componentes;

import Estilos.PaletaColores;
import Recursos.CargadorRecursos;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Mariana
 */
public class BotonEditar extends DefaultTableCellRenderer{
    
    private final JLabel label;
    private final ImageIcon iconoEditar;

    public BotonEditar() {
        label = new JLabel("✎", SwingConstants.CENTER);
        label.setOpaque(true);
        label.setFont(new Font("Segoe UI", Font.BOLD, 16));
        label.setForeground(PaletaColores.MARRON_OSCURO);
        label.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(170, 160, 150), 1),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));

        iconoEditar = CargadorRecursos.cargarIcono("/editar.png", 16, 16);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {

        label.setBackground(isSelected ? table.getSelectionBackground() : PaletaColores.BLANCO);

        if (iconoEditar != null) {
            label.setText("");
            label.setIcon(iconoEditar);
        } else {
            label.setText("✎");
            label.setIcon(null);
        }

        return label;
    }
    
}
