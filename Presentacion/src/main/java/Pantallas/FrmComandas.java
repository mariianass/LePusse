/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pantallas;

import Componentes.BotonEditar;
import Componentes.BotonRedondeado;
import Componentes.MenuLateralEmpleadoPanel;
import Controlador.Coordinador;
import Estilos.Dimensiones;
import Estilos.PaletaColores;
import dtos.ComandaDTO;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.SwingConstants;

/**
 * Pantalla principal para la gestión de comandas. Muestra la vista principal
 * del módulo de comandas para empleado, manteniendo la identidad visual del
 * sistema.
 *
 * @author regina, mariana e isaac
 */
public class FrmComandas extends JFrame {

    private final Coordinador coordinador;

    private JTable tablaComandas;
    private DefaultTableModel modeloTabla;

    public FrmComandas(Coordinador coordinador) {
        this.coordinador = coordinador;

        setTitle("Restaurante Le Pusse - Comandas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Dimensiones.ANCHO_VENTANA, Dimensiones.ALTO_VENTANA);
        setMinimumSize(new Dimension(Dimensiones.ANCHO_VENTANA, Dimensiones.ALTO_VENTANA));
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(new MenuLateralEmpleadoPanel(coordinador), BorderLayout.WEST);
        add(crearContenidoPrincipal(), BorderLayout.CENTER);

        cargarComandasEnTabla();

    }

    /**
     * Crea el contenido principal de la pantalla.
     *
     * @return Panel principal de contenido.
     */
    private JPanel crearContenidoPrincipal() {
        JPanel contenido = new JPanel(new BorderLayout());
        contenido.setBackground(PaletaColores.BEIGE_FONDO);
        contenido.setBorder(new EmptyBorder(0, 0, 0, 0));

        JPanel cabecera = new JPanel(new BorderLayout());
        cabecera.setBackground(PaletaColores.BLANCO);
        cabecera.setBorder(new EmptyBorder(26, 28, 20, 28));

        JLabel titulo = new JLabel("Comandas");
        titulo.setFont(new Font("Segoe UI Semilight", Font.PLAIN, 34));
        titulo.setForeground(PaletaColores.TEXTO_MARRON);
        cabecera.add(titulo, BorderLayout.WEST);

        contenido.add(cabecera, BorderLayout.NORTH);

        JPanel zonaCentral = new JPanel(new BorderLayout());
        zonaCentral.setBackground(PaletaColores.BEIGE_PANEL);
        zonaCentral.setBorder(new EmptyBorder(22, 22, 22, 22));

        zonaCentral.add(crearPanelSuperior(), BorderLayout.NORTH);
        zonaCentral.add(crearPanelTabla(), BorderLayout.CENTER);

        contenido.add(zonaCentral, BorderLayout.CENTER);

        return contenido;
    }

    /**
     * Crea el panel superior con el botón principal de registro.
     *
     * @return Panel superior.
     */
    private JPanel crearPanelSuperior() {
        JPanel superior = new JPanel(new BorderLayout());
        superior.setOpaque(false);
        superior.setBorder(new EmptyBorder(10, 26, 18, 26));

        BotonRedondeado btnNuevaComanda = new BotonRedondeado("Nueva Comanda", 20);
        btnNuevaComanda.setPreferredSize(new Dimension(160, 42));
        btnNuevaComanda.setBackground(PaletaColores.DORADO);
        btnNuevaComanda.setForeground(PaletaColores.MARRON_OSCURO);
        btnNuevaComanda.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnNuevaComanda.addActionListener(e -> coordinador.mostrarNuevaComanda());

        JPanel panelBoton = new JPanel();
        panelBoton.setOpaque(false);
        panelBoton.add(btnNuevaComanda);

        superior.add(panelBoton, BorderLayout.EAST);

        return superior;
    }

    /**
     * Crea el panel contenedor de la tabla.
     *
     * @return Panel con la tabla de comandas.
     */
    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(PaletaColores.BLANCO);
        panel.setBorder(BorderFactory.createLineBorder(PaletaColores.LINEA_SUAVE, 1));
        panel.setPreferredSize(new Dimension(0, 420));

        panel.add(crearTablaComandas(), BorderLayout.CENTER);

        return panel;
    }

    /**
     * Crea la tabla principal de comandas.
     *
     * @return Scroll con la tabla.
     */
    private JScrollPane crearTablaComandas() {
        String[] columnas = {
            "ID", "Folio", "Fecha", "Hora", "Mesa", "Cliente", "Total", "Estado", "Acciones"
        };

        modeloTabla = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        tablaComandas = new JTable(modeloTabla);
        tablaComandas.setRowHeight(49);
        tablaComandas.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaComandas.setForeground(PaletaColores.TEXTO_MARRON);
        tablaComandas.setBackground(PaletaColores.BLANCO);
        tablaComandas.setShowVerticalLines(false);
        tablaComandas.setShowHorizontalLines(true);
        tablaComandas.setGridColor(PaletaColores.LINEA_SUAVE);
        tablaComandas.setFocusable(false);
        tablaComandas.setSelectionBackground(PaletaColores.BLANCO_SUAVE);
        tablaComandas.setSelectionForeground(PaletaColores.TEXTO_MARRON);
        tablaComandas.setIntercellSpacing(new Dimension(0, 1));
        tablaComandas.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        JTableHeader header = tablaComandas.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(PaletaColores.BLANCO);
        header.setForeground(PaletaColores.TEXTO_MARRON);
        header.setPreferredSize(new Dimension(0, 54));
        header.setReorderingAllowed(false);
        header.setResizingAllowed(false);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, PaletaColores.LINEA_SUAVE));

        tablaComandas.getColumnModel().getColumn(0).setMinWidth(0);
        tablaComandas.getColumnModel().getColumn(0).setMaxWidth(0);
        tablaComandas.getColumnModel().getColumn(0).setPreferredWidth(0);

        tablaComandas.getColumnModel().getColumn(1).setPreferredWidth(150);
        tablaComandas.getColumnModel().getColumn(2).setPreferredWidth(110);
        tablaComandas.getColumnModel().getColumn(3).setPreferredWidth(90);
        tablaComandas.getColumnModel().getColumn(4).setPreferredWidth(90);
        tablaComandas.getColumnModel().getColumn(5).setPreferredWidth(210);
        tablaComandas.getColumnModel().getColumn(6).setPreferredWidth(110);
        tablaComandas.getColumnModel().getColumn(7).setPreferredWidth(120);
        tablaComandas.getColumnModel().getColumn(8).setPreferredWidth(120);

        DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
        centrado.setHorizontalAlignment(SwingConstants.CENTER);
        centrado.setBackground(PaletaColores.BLANCO);
        centrado.setForeground(PaletaColores.TEXTO_MARRON);

        tablaComandas.getColumnModel().getColumn(2).setCellRenderer(centrado);
        tablaComandas.getColumnModel().getColumn(3).setCellRenderer(centrado);
        tablaComandas.getColumnModel().getColumn(4).setCellRenderer(centrado);
        tablaComandas.getColumnModel().getColumn(6).setCellRenderer(centrado);
        tablaComandas.getColumnModel().getColumn(7).setCellRenderer(centrado);
        tablaComandas.getColumnModel().getColumn(8).setCellRenderer(new BotonEditar());

        tablaComandas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tablaComandas.getSelectedRow();
                int columna = tablaComandas.getSelectedColumn();

                if (fila == -1) {
                    return;
                }

                Long idComanda = (Long) modeloTabla.getValueAt(fila, 0);

                if (columna == 8 && e.getClickCount() == 1) {
                    confirmarEntrega(idComanda);
                }

                if (e.getClickCount() == 2) {
                    coordinador.mostrarEditarComanda(idComanda);
                }
            }
        });

        JScrollPane scroll = new JScrollPane(tablaComandas);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(PaletaColores.BLANCO);
        scroll.setBackground(PaletaColores.BLANCO);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        return scroll;
    }

    private void cargarComandasEnTabla() {
        try {
            modeloTabla.setRowCount(0);

            List<ComandaDTO> comandas = coordinador.obtenerComandas();

            if (comandas == null || comandas.isEmpty()) {
                return;
            }

            for (ComandaDTO comanda : comandas) {
                modeloTabla.addRow(new Object[]{
                    comanda.getIdComanda(),
                    comanda.getFolio(),
                    comanda.getFechaHoraCreacion() != null ? comanda.getFechaHoraCreacion().toLocalDate() : null,
                    comanda.getFechaHoraCreacion() != null ? comanda.getFechaHoraCreacion().toLocalTime() : null,
                    comanda.getNumeroMesa(),
                    comanda.getNombreCliente() != null ? comanda.getNombreCliente() : "Cliente General",
                    comanda.getTotalVenta(),
                    comanda.getEstado(),
                    ""
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error al cargar las comandas: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Solicita confirmación antes de marcar una comanda como entregada.
     *
     * @param idComanda ID de la comanda seleccionada.
     */
    private void confirmarEntrega(Long idComanda) {
        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Deseas marcar como entregada la comanda?",
                "Confirmar",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (opcion == JOptionPane.YES_OPTION) {
            try {
                coordinador.entregarComanda(idComanda);
                recargarTabla();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                        this,
                        e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    /**
     * Recarga la información visible de la pantalla.
     */
    public void recargarTabla() {
        cargarComandasEnTabla();
    }
}
