/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pantallas;

import Componentes.BotonRedondeado;
import Componentes.MenuLateralEmpleadoPanel;
import Controlador.Coordinador;
import Estilos.Dimensiones;
import Estilos.PaletaColores;
import dtos.DetalleComandaDTO;
import dtos.ProductoDTO;
import enumsDTO.DisponibilidadProductoDTO;
import enumsDTO.TipoProductoDTO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Pantalla para seleccionar productos dentro de una nueva comanda. Permite
 * visualizar productos disponibles en formato catálogo y seleccionar
 * cantidades.
 *
 * @author regina, mariana e isaac
 */
public class FrmCatalogoProductosComanda extends JFrame {

    private final Coordinador coordinador;

    private JTextField txtBuscar;
    private JComboBox<String> cmbFiltroTipo;
    private JPanel panelProductos;

    private List<ProductoDTO> productosDisponibles;

    private final Map<Long, Integer> cantidadesSeleccionadas = new LinkedHashMap<>();

    /**
     * Constructor de la pantalla de catálogo de productos.
     *
     * @param coordinador Referencia al coordinador de navegación.
     */
    public FrmCatalogoProductosComanda(Coordinador coordinador) {
        this.coordinador = coordinador;
        this.productosDisponibles = new ArrayList<>();

        setTitle("Restaurante Le Pusse - Catálogo de Productos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(Dimensiones.ANCHO_VENTANA, Dimensiones.ALTO_VENTANA);
        setMinimumSize(new Dimension(Dimensiones.ANCHO_VENTANA, Dimensiones.ALTO_VENTANA));
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(new MenuLateralEmpleadoPanel(coordinador), BorderLayout.WEST);
        add(crearContenidoPrincipal(), BorderLayout.CENTER);

        cargarProductosDisponibles();
    }

    /**
     * Crea el contenido principal del catálogo.
     *
     * @return Panel principal.
     */
    private JPanel crearContenidoPrincipal() {
        JPanel contenido = new JPanel(new BorderLayout());
        contenido.setBackground(PaletaColores.BEIGE_FONDO);

        contenido.add(crearCabecera(), BorderLayout.NORTH);
        contenido.add(crearZonaCentral(), BorderLayout.CENTER);

        return contenido;
    }

    /**
     * Cabecera principal del módulo.
     *
     * @return Panel de cabecera.
     */
    private JPanel crearCabecera() {
        JPanel cabecera = new JPanel(new BorderLayout());
        cabecera.setBackground(PaletaColores.BLANCO);
        cabecera.setBorder(new EmptyBorder(26, 28, 20, 28));

        JLabel titulo = new JLabel("Comandas");
        titulo.setFont(new Font("Segoe UI Semilight", Font.PLAIN, 34));
        titulo.setForeground(PaletaColores.TEXTO_MARRON);

        cabecera.add(titulo, BorderLayout.WEST);

        return cabecera;
    }

    /**
     * Crea la zona central del catálogo.
     *
     * @return Panel central.
     */
    private JPanel crearZonaCentral() {
        JPanel zona = new JPanel(new BorderLayout());
        zona.setBackground(PaletaColores.BEIGE_PANEL);
        zona.setBorder(new EmptyBorder(28, 34, 34, 34));

        zona.add(crearPanelCatalogo(), BorderLayout.CENTER);

        return zona;
    }

    /**
     * Crea el panel principal del catálogo.
     *
     * @return Panel del catálogo.
     */
    private JPanel crearPanelCatalogo() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(PaletaColores.BLANCO_SUAVE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(215, 205, 195), 1),
                new EmptyBorder(20, 24, 20, 24)
        ));

        panel.add(crearPanelSuperior(), BorderLayout.NORTH);
        panel.add(crearPanelProductos(), BorderLayout.CENTER);
        panel.add(crearPanelInferior(), BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Crea el panel superior con buscador y filtro.
     *
     * @return Panel superior.
     */
    private JPanel crearPanelSuperior() {
        JPanel superior = new JPanel(new BorderLayout());
        superior.setOpaque(false);
        superior.setBorder(new EmptyBorder(0, 0, 16, 0));

        txtBuscar = new JTextField();
        txtBuscar.setPreferredSize(new Dimension(260, 36));
        txtBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtBuscar.setForeground(PaletaColores.TEXTO_MARRON);
        txtBuscar.setBackground(PaletaColores.BLANCO);
        txtBuscar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PaletaColores.LINEA_SUAVE, 1),
                new EmptyBorder(4, 10, 4, 10)
        ));

        txtBuscar.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                aplicarFiltros();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                aplicarFiltros();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                aplicarFiltros();
            }
        });

        cmbFiltroTipo = new JComboBox<>(new String[]{"Todos los tipos", "PLATILLO", "BEBIDA", "POSTRE"});
        cmbFiltroTipo.setPreferredSize(new Dimension(180, 36));
        cmbFiltroTipo.setFont(new Font("Segoe UI", Font.BOLD, 13));
        cmbFiltroTipo.setForeground(PaletaColores.MARRON_OSCURO);
        cmbFiltroTipo.setBackground(PaletaColores.BLANCO);
        cmbFiltroTipo.setBorder(BorderFactory.createLineBorder(PaletaColores.LINEA_SUAVE, 1));
        cmbFiltroTipo.addActionListener(e -> aplicarFiltros());

        JPanel izquierda = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        izquierda.setOpaque(false);
        izquierda.add(txtBuscar);

        JPanel derecha = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        derecha.setOpaque(false);
        derecha.add(cmbFiltroTipo);

        superior.add(izquierda, BorderLayout.WEST);
        superior.add(derecha, BorderLayout.EAST);

        return superior;
    }

    /**
     * Crea el panel con scroll donde se mostrarán las tarjetas de productos.
     *
     * @return Scroll del catálogo.
     */
    private JScrollPane crearPanelProductos() {
        panelProductos = new JPanel(new GridLayout(0, 3, 22, 22));
        panelProductos.setOpaque(false);

        JScrollPane scroll = new JScrollPane(panelProductos);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(PaletaColores.BLANCO_SUAVE);
        scroll.setBackground(PaletaColores.BLANCO_SUAVE);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        return scroll;
    }

    /**
     * Crea el panel inferior con el botón para finalizar la selección.
     *
     * @return Panel inferior.
     */
    private JPanel crearPanelInferior() {
        JPanel inferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 16));
        inferior.setOpaque(false);

        BotonRedondeado btnTerminar = new BotonRedondeado("Terminar de añadir", 20);
        btnTerminar.setPreferredSize(new Dimension(180, 42));
        btnTerminar.setBackground(PaletaColores.MARRON_OSCURO);
        btnTerminar.setForeground(PaletaColores.BLANCO);
        btnTerminar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnTerminar.addActionListener(e -> terminarSeleccionProductos());

        inferior.add(btnTerminar);

        return inferior;
    }

    /**
     * Carga los productos activos y disponibles para venta.
     */
    private void cargarProductosDisponibles() {
        try {
            productosDisponibles.clear();

            List<ProductoDTO> productos = coordinador.obtenerProductos();

            if (productos != null) {
                for (ProductoDTO producto : productos) {
                    if (Boolean.TRUE.equals(producto.getActivo())
                            && producto.getDisponibilidad() == DisponibilidadProductoDTO.SI) {
                        productosDisponibles.add(producto);
                    }
                }
            }

            mostrarProductos(productosDisponibles);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "No se pudieron cargar los productos disponibles: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Aplica filtro por nombre y tipo sobre los productos disponibles.
     */
    private void aplicarFiltros() {
        String texto = txtBuscar.getText() != null ? txtBuscar.getText().trim().toLowerCase() : "";
        String tipoSeleccionado = (String) cmbFiltroTipo.getSelectedItem();

        List<ProductoDTO> filtrados = new ArrayList<>();

        for (ProductoDTO producto : productosDisponibles) {
            boolean coincideNombre = texto.isEmpty()
                    || (producto.getNombre() != null && producto.getNombre().toLowerCase().contains(texto));

            boolean coincideTipo = "Todos los tipos".equals(tipoSeleccionado)
                    || (producto.getTipo() != null && producto.getTipo().name().equals(tipoSeleccionado));

            if (coincideNombre && coincideTipo) {
                filtrados.add(producto);
            }
        }

        mostrarProductos(filtrados);
    }

    /**
     * Muestra las tarjetas de productos en el catálogo.
     *
     * @param productos Lista de productos a mostrar.
     */
    private void mostrarProductos(List<ProductoDTO> productos) {
        panelProductos.removeAll();

        if (productos == null || productos.isEmpty()) {
            JPanel vacio = new JPanel(new FlowLayout(FlowLayout.CENTER));
            vacio.setOpaque(false);

            JLabel lblVacio = new JLabel("No hay productos disponibles para mostrar.");
            lblVacio.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            lblVacio.setForeground(PaletaColores.TEXTO_MARRON);

            vacio.add(lblVacio);
            panelProductos.setLayout(new GridLayout(1, 1));
            panelProductos.add(vacio);
        } else {
            panelProductos.setLayout(new GridLayout(0, 3, 22, 22));

            for (ProductoDTO producto : productos) {
                panelProductos.add(crearTarjetaProducto(producto));
            }
        }

        panelProductos.revalidate();
        panelProductos.repaint();
    }

    /**
     * Crea una tarjeta visual para un producto del catálogo.
     *
     * @param producto Producto a representar.
     * @return Panel tarjeta.
     */
    private JPanel crearTarjetaProducto(ProductoDTO producto) {
        JPanel tarjeta = new JPanel();
        tarjeta.setBackground(PaletaColores.BLANCO);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PaletaColores.LINEA_SUAVE, 1),
                new EmptyBorder(18, 18, 18, 18)
        ));
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setPreferredSize(new Dimension(220, 240));

        JLabel lblImagen = new JLabel("Imagen");
        lblImagen.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblImagen.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblImagen.setForeground(PaletaColores.TEXTO_MARRON);

        JLabel lblNombre = new JLabel(producto.getNombre() != null ? producto.getNombre() : "Producto");
        lblNombre.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblNombre.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblNombre.setForeground(PaletaColores.TEXTO_MARRON);

        String precioTexto = producto.getPrecio() != null
                ? "$" + String.format("%.2f", producto.getPrecio())
                : "$0.00";

        JLabel lblPrecio = new JLabel(precioTexto);
        lblPrecio.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblPrecio.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblPrecio.setForeground(PaletaColores.TEXTO_MARRON);

        JLabel lblCantidad = new JLabel(String.valueOf(obtenerCantidadSeleccionada(producto.getIdProducto())));
        lblCantidad.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblCantidad.setForeground(PaletaColores.MARRON_OSCURO);

        BotonRedondeado btnMenos = new BotonRedondeado("-", 18);
        btnMenos.setPreferredSize(new Dimension(42, 32));
        btnMenos.setBackground(PaletaColores.DORADO);
        btnMenos.setForeground(PaletaColores.MARRON_OSCURO);
        btnMenos.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnMenos.addActionListener(e -> {
            disminuirCantidad(producto.getIdProducto());
            lblCantidad.setText(String.valueOf(obtenerCantidadSeleccionada(producto.getIdProducto())));
        });

        BotonRedondeado btnMas = new BotonRedondeado("+", 18);
        btnMas.setPreferredSize(new Dimension(42, 32));
        btnMas.setBackground(PaletaColores.DORADO);
        btnMas.setForeground(PaletaColores.MARRON_OSCURO);
        btnMas.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnMas.addActionListener(e -> {
            aumentarCantidad(producto.getIdProducto());
            lblCantidad.setText(String.valueOf(obtenerCantidadSeleccionada(producto.getIdProducto())));
        });

        JPanel panelCantidad = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        panelCantidad.setOpaque(false);
        panelCantidad.add(btnMenos);
        panelCantidad.add(lblCantidad);
        panelCantidad.add(btnMas);

        tarjeta.add(Box.createVerticalGlue());
        tarjeta.add(lblImagen);
        tarjeta.add(Box.createRigidArea(new Dimension(0, 14)));
        tarjeta.add(lblNombre);
        tarjeta.add(Box.createRigidArea(new Dimension(0, 8)));
        tarjeta.add(lblPrecio);
        tarjeta.add(Box.createRigidArea(new Dimension(0, 14)));
        tarjeta.add(panelCantidad);
        tarjeta.add(Box.createVerticalGlue());

        return tarjeta;
    }

    /**
     * Construye la lista de detalles seleccionados y los envía al coordinador
     * para actualizar la nueva comanda.
     */
    private void terminarSeleccionProductos() {
        List<DetalleComandaDTO> detallesSeleccionados = new ArrayList<>();

        for (ProductoDTO producto : productosDisponibles) {
            int cantidad = cantidadesSeleccionadas.getOrDefault(producto.getIdProducto(), 0);

            if (cantidad > 0) {
                double precio = producto.getPrecio() != null ? producto.getPrecio() : 0.0;
                double subtotal = precio * cantidad;

                DetalleComandaDTO detalle = new DetalleComandaDTO(
                        null,
                        cantidad,
                        "",
                        precio,
                        subtotal,
                        producto.getIdProducto(),
                        producto.getNombre()
                );

                detallesSeleccionados.add(detalle);
            }
        }

        coordinador.recibirProductosSeleccionadosDesdeCatalogo(detallesSeleccionados);
        cantidadesSeleccionadas.clear();
    }

    /**
     * Obtiene la cantidad actualmente seleccionada para un producto.
     *
     * @param idProducto Identificador del producto.
     * @return Cantidad seleccionada.
     */
    private int obtenerCantidadSeleccionada(Long idProducto) {
        return cantidadesSeleccionadas.getOrDefault(idProducto, 0);
    }

    /**
     * Aumenta la cantidad seleccionada de un producto.
     *
     * @param idProducto Identificador del producto.
     */
    private void aumentarCantidad(Long idProducto) {
        int cantidadActual = cantidadesSeleccionadas.getOrDefault(idProducto, 0);
        cantidadesSeleccionadas.put(idProducto, cantidadActual + 1);
    }

    /**
     * Disminuye la cantidad seleccionada de un producto.
     *
     * @param idProducto Identificador del producto.
     */
    private void disminuirCantidad(Long idProducto) {
        int cantidadActual = cantidadesSeleccionadas.getOrDefault(idProducto, 0);

        if (cantidadActual <= 1) {
            cantidadesSeleccionadas.remove(idProducto);
        } else {
            cantidadesSeleccionadas.put(idProducto, cantidadActual - 1);
        }
    }
}
