/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import conexion.ConexionBD;
import DAOs.ComandaDAO;
import entidades.ClienteFrecuente;
import entidades.Comanda;
import entidades.DetalleComanda;
import entidades.Mesa;
import entidades.Producto;
import enums.DisponibilidadProducto;
import enums.EstadoComanda;
import enums.EstadoMesa;
import enums.TipoProducto;
import excepciones.PersistenciaException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ComandaDAOTest {

    private ComandaDAO comandaDAO;

    private List<Long> idsComandasCreadas;
    private List<Long> idsMesasCreadas;
    private List<Long> idsProductosCreados;
    private List<Long> idsClientesCreados;

    @BeforeEach
    void setUp() {
        comandaDAO = ComandaDAO.getInstance();
        idsComandasCreadas = new ArrayList<>();
        idsMesasCreadas = new ArrayList<>();
        idsProductosCreados = new ArrayList<>();
        idsClientesCreados = new ArrayList<>();
    }

    @AfterEach
    void tearDown() {
        EntityManager em = ConexionBD.crearConexion();

        try {
            em.getTransaction().begin();

            for (Long id : idsComandasCreadas) {
                Comanda comanda = em.find(Comanda.class, id);
                if (comanda != null) {
                    em.remove(comanda);
                }
            }

            for (Long id : idsClientesCreados) {
                ClienteFrecuente cliente = em.find(ClienteFrecuente.class, id);
                if (cliente != null) {
                    em.remove(cliente);
                }
            }

            for (Long id : idsProductosCreados) {
                Producto producto = em.find(Producto.class, id);
                if (producto != null) {
                    em.remove(producto);
                }
            }

            for (Long id : idsMesasCreadas) {
                Mesa mesa = em.find(Mesa.class, id);
                if (mesa != null) {
                    em.remove(mesa);
                }
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            em.close();
        }
    }

    private void registrarIdComanda(Comanda comanda) {
        if (comanda != null && comanda.getIdComanda() != null) {
            idsComandasCreadas.add(comanda.getIdComanda());
        }
    }

    private void registrarIdMesa(Mesa mesa) {
        if (mesa != null && mesa.getIdMesa() != null) {
            idsMesasCreadas.add(mesa.getIdMesa());
        }
    }

    private void registrarIdProducto(Producto producto) {
        if (producto != null && producto.getIdProducto() != null) {
            idsProductosCreados.add(producto.getIdProducto());
        }
    }

    private void registrarIdCliente(ClienteFrecuente cliente) {
        if (cliente != null && cliente.getId() != null) {
            idsClientesCreados.add(cliente.getId());
        }
    }

    private Mesa crearMesa(Integer numeroMesa, EstadoMesa estadoMesa) {
        EntityManager em = ConexionBD.crearConexion();

        try {
            Mesa mesa = new Mesa();
            mesa.setNumeroMesa(numeroMesa);
            mesa.setEstado(estadoMesa);

            em.getTransaction().begin();
            em.persist(mesa);
            em.getTransaction().commit();

            registrarIdMesa(mesa);
            return mesa;
        } finally {
            em.close();
        }
    }

    private Producto crearProducto(String nombre, Double precio) {
        EntityManager em = ConexionBD.crearConexion();

        try {
            Producto producto = new Producto();
            producto.setNombre(nombre);
            producto.setDescripcion("Producto de prueba");
            producto.setPrecio(precio);
            producto.setTipo(TipoProducto.PLATILLO);
            producto.setDisponibilidad(DisponibilidadProducto.SI);
            producto.setActivo(true);
            producto.setRutaImagen(null);

            em.getTransaction().begin();
            em.persist(producto);
            em.getTransaction().commit();

            registrarIdProducto(producto);
            return producto;
        } finally {
            em.close();
        }
    }

    private ClienteFrecuente crearClienteFrecuente(String sufijo) {
        EntityManager em = ConexionBD.crearConexion();

        try {
            ClienteFrecuente cliente = new ClienteFrecuente();
            cliente.setNombre("Cliente" + sufijo);
            cliente.setApellidoPaterno("Prueba");
            cliente.setApellidoMaterno("DAO");
            cliente.setTelefono("6441000" + sufijo);
            cliente.setCorreoElectronico("cliente" + sufijo + "@correo.com");
            cliente.setFechaRegistro(LocalDateTime.now());
            cliente.setNumeroVisitas(1);
            cliente.setPuntosFidelidad(0);
            cliente.setFechaUltimaComanda(LocalDateTime.now());

            em.getTransaction().begin();
            em.persist(cliente);
            em.getTransaction().commit();

            registrarIdCliente(cliente);
            return cliente;
        } finally {
            em.close();
        }
    }

    private Comanda crearComandaBase(String folio, Mesa mesa, ClienteFrecuente cliente, Producto producto, EstadoComanda estado) {
        Comanda comanda = new Comanda();
        comanda.setFolio(folio);
        comanda.setFechaHoraCreacion(LocalDateTime.now());
        comanda.setEstado(estado);
        comanda.setTotalVenta(producto.getPrecio());
        comanda.setMesa(mesa);
        comanda.setCliente(cliente);

        DetalleComanda detalle = new DetalleComanda();
        detalle.setCantidad(1);
        detalle.setComentarioEspecial("Sin cebolla");
        detalle.setPrecio(producto.getPrecio());
        detalle.setSubtotal(producto.getPrecio());
        detalle.setProducto(producto);

        comanda.agregarDetalle(detalle);

        return comanda;
    }

    @Test
    void testGuardarExitoNuevaComanda() throws PersistenciaException {
        Mesa mesa = crearMesa(101, EstadoMesa.DISPONIBLE);
        Producto producto = crearProducto("HamburguesaTest01", 120.0);
        ClienteFrecuente cliente = crearClienteFrecuente("01");

        Comanda comanda = crearComandaBase("OB-20260412-001", mesa, cliente, producto, EstadoComanda.ABIERTA);

        comandaDAO.guardar(comanda);
        registrarIdComanda(comanda);

        assertNotNull(comanda.getIdComanda());
        assertEquals("OB-20260412-001", comanda.getFolio());
    }

    @Test
    void testGuardarErrorComandaNula() {
        assertThrows(PersistenciaException.class, () -> comandaDAO.guardar(null));
    }

    @Test
    void testBuscarPorIdExito() throws PersistenciaException {
        Mesa mesa = crearMesa(102, EstadoMesa.DISPONIBLE);
        Producto producto = crearProducto("HamburguesaTest02", 130.0);
        ClienteFrecuente cliente = crearClienteFrecuente("02");

        Comanda comanda = crearComandaBase("OB-20260412-002", mesa, cliente, producto, EstadoComanda.ABIERTA);
        comandaDAO.guardar(comanda);
        registrarIdComanda(comanda);

        Comanda encontrada = comandaDAO.buscarPorId(comanda.getIdComanda());

        assertNotNull(encontrada);
        assertEquals(comanda.getIdComanda(), encontrada.getIdComanda());
    }

    @Test
    void testBuscarPorIdErrorIdNulo() {
        assertThrows(PersistenciaException.class, () -> comandaDAO.buscarPorId(null));
    }

    @Test
    void testEditarExitoComanda() throws PersistenciaException {
        Mesa mesa = crearMesa(103, EstadoMesa.DISPONIBLE);
        Producto producto = crearProducto("HamburguesaTest03", 140.0);
        ClienteFrecuente cliente = crearClienteFrecuente("03");

        Comanda comanda = crearComandaBase("OB-20260412-003", mesa, cliente, producto, EstadoComanda.ABIERTA);
        comandaDAO.guardar(comanda);
        registrarIdComanda(comanda);

        comanda.setEstado(EstadoComanda.ENTREGADA);
        comanda.setTotalVenta(200.0);

        Comanda actualizada = comandaDAO.editar(comanda);

        assertNotNull(actualizada);
        assertEquals(EstadoComanda.ENTREGADA, actualizada.getEstado());
        assertEquals(200.0, actualizada.getTotalVenta());
    }

    @Test
    void testBuscarPorFiltrosExitoPorFolio() throws PersistenciaException {
        Mesa mesa = crearMesa(104, EstadoMesa.DISPONIBLE);
        Producto producto = crearProducto("HamburguesaTest04", 150.0);
        ClienteFrecuente cliente = crearClienteFrecuente("04");

        Comanda comanda = crearComandaBase("OB-20260412-XYZ", mesa, cliente, producto, EstadoComanda.ABIERTA);
        comandaDAO.guardar(comanda);
        registrarIdComanda(comanda);

        List<Comanda> resultados = comandaDAO.buscarPorFiltros("XYZ");

        assertNotNull(resultados);
        assertFalse(resultados.isEmpty());
    }
    
    @Test
    void testObtenerMesasDisponiblesExito() throws PersistenciaException {
        Mesa mesaDisponible = crearMesa(105, EstadoMesa.DISPONIBLE);
        Mesa mesaOcupada = crearMesa(106, EstadoMesa.OCUPADA);

        List<Mesa> mesasDisponibles = comandaDAO.obtenerMesasDisponibles();

        assertNotNull(mesasDisponibles);
        assertTrue(mesasDisponibles.stream().anyMatch(m -> m.getIdMesa().equals(mesaDisponible.getIdMesa())));
        assertFalse(mesasDisponibles.stream().anyMatch(m -> m.getIdMesa().equals(mesaOcupada.getIdMesa())));
    }

    @Test
    void testBuscarComandaActivaPorMesaExito() throws PersistenciaException {
        Mesa mesa = crearMesa(107, EstadoMesa.DISPONIBLE);
        Producto producto = crearProducto("HamburguesaTest05", 160.0);
        ClienteFrecuente cliente = crearClienteFrecuente("05");

        Comanda comanda = crearComandaBase("OB-20260412-005", mesa, cliente, producto, EstadoComanda.ABIERTA);
        comandaDAO.guardar(comanda);
        registrarIdComanda(comanda);

        Comanda encontrada = comandaDAO.buscarComandaActivaPorMesa(mesa.getIdMesa());

        assertNotNull(encontrada);
        assertEquals(comanda.getIdComanda(), encontrada.getIdComanda());
    }

    @Test
    void testBuscarComandaActivaPorMesaRetornaNullSiNoExiste() throws PersistenciaException {
        Mesa mesa = crearMesa(108, EstadoMesa.DISPONIBLE);

        Comanda encontrada = comandaDAO.buscarComandaActivaPorMesa(mesa.getIdMesa());

        assertNull(encontrada);
    }

    @Test
    void testBuscarComandaActivaPorMesaErrorIdNulo() {
        assertThrows(PersistenciaException.class, () -> comandaDAO.buscarComandaActivaPorMesa(null));
    }

}
