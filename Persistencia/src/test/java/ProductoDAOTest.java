/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import DAOs.ProductoDAO;
import conexion.ConexionBD;
import entidades.DetalleProductoIngrediente;
import entidades.Ingrediente;
import entidades.Producto;
import enums.DisponibilidadProducto;
import enums.TipoProducto;
import enums.UnidadMedida;
import excepciones.PersistenciaException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la clase ProductoDAO.
 * 
 * Esta clase contiene los casos de prueba para validar el correcto funcionamiento 
 * de las operaciones CRUD y validaciones de negocio en la persistencia de Productos.
 * 
 * @author regina, mariana e isaac
 */
public class ProductoDAOTest {

    /** DAO bajo prueba. */
    private ProductoDAO productoDAO;
    
    /** Lista de IDs de productos creados durante los tests para su limpieza posterior. */
    private List<Long> idsProductosCreados;
    
    /** Lista de IDs de ingredientes creados durante los tests para su limpieza posterior. */
    private List<Long> idsIngredientesCreados;

    /**
     * Configuración inicial antes de cada prueba. 
     * Inicializa el DAO y las listas de seguimiento de IDs.
     */
    @BeforeEach
    void setUp() {
        productoDAO = ProductoDAO.getInstance();
        idsProductosCreados = new ArrayList<>();
        idsIngredientesCreados = new ArrayList<>();
    }

    /**
     * Limpieza de la base de datos después de cada prueba.
     * Elimina los productos e ingredientes creados para mantener la independencia 
     * entre pruebas y evitar contaminación de datos.
     * * @throws PersistenciaException Si ocurre un error al eliminar productos.
     */
    @AfterEach
    void tearDown() throws PersistenciaException {
        for (Long idProducto : idsProductosCreados) {
            try {
                productoDAO.eliminar(idProducto);
            } catch (PersistenciaException e) {
                // Silencio intencional para continuar limpieza
            }
        }

        EntityManager em = ConexionBD.crearConexion();
        try {
            em.getTransaction().begin();
            for (Long idIngrediente : idsIngredientesCreados) {
                try {
                    Ingrediente ingrediente = em.find(Ingrediente.class, idIngrediente);
                    if (ingrediente != null) {
                        em.remove(ingrediente);
                    }
                } catch (Exception e) {
                    // Silencio intencional para continuar limpieza
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

    /**
     * Registra el ID de un producto en la lista de seguimiento para limpieza.
     * @param producto Producto cuyo ID será registrado.
     */
    private void registrarIdProductoSiExiste(Producto producto) {
        if (producto != null && producto.getIdProducto() != null) {
            idsProductosCreados.add(producto.getIdProducto());
        }
    }

    /**
     * Método auxiliar para crear un ingrediente persistido necesario para las pruebas de producto.
     * @param sufijo Cadena única para diferenciar nombres de ingredientes.
     * @return El Ingrediente persistido.
     */
    private Ingrediente crearIngredientePrueba(String sufijo) {
        EntityManager em = ConexionBD.crearConexion();

        try {
            Ingrediente ingrediente = new Ingrediente();
            ingrediente.setNombre("IngredienteTest_" + sufijo);
            ingrediente.setUnidadMedida(UnidadMedida.GRAMOS);
            ingrediente.setStockActual(500.0);
            ingrediente.setUmbral(50.0);

            em.getTransaction().begin();
            em.persist(ingrediente);
            em.getTransaction().commit();

            idsIngredientesCreados.add(ingrediente.getIdIngrediente());
            return ingrediente;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            fail("No se pudo crear ingrediente de prueba: " + e.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

    /**
     * Método auxiliar para instanciar un objeto Producto con datos válidos y un ingrediente asociado.
     * @param sufijo Cadena única para diferenciar nombres.
     * @param tipo El tipo de producto a crear.
     * @return Objeto Producto listo para ser persistido.
     */
    private Producto crearProductoValido(String sufijo, TipoProducto tipo) {
        Ingrediente ingrediente = crearIngredientePrueba(sufijo);

        DetalleProductoIngrediente detalle = new DetalleProductoIngrediente();
        detalle.setIngrediente(ingrediente);
        detalle.setCantidadRequerida(100);

        Producto producto = new Producto();
        producto.setNombre("ProductoTest_" + sufijo);
        producto.setDescripcion("Descripción de prueba " + sufijo);
        producto.setPrecio(120.0);
        producto.setTipo(tipo);
        producto.setDisponibilidad(DisponibilidadProducto.SI);
        producto.setActivo(true);
        producto.setRutaImagen("ruta/test/" + sufijo + ".jpg");
        producto.agregarDetalleIngrediente(detalle);

        return producto;
    }

    /**
     * Prueba el flujo exitoso de guardado de un producto válido.
     * @throws PersistenciaException Si ocurre un error inesperado.
     */
    @Test
    void guardarProductoValido() throws PersistenciaException {
        Producto producto = crearProductoValido("guardar", TipoProducto.PLATILLO);

        Producto resultado = productoDAO.guardar(producto);
        registrarIdProductoSiExiste(resultado);

        assertNotNull(resultado);
        assertNotNull(resultado.getIdProducto(), "La base de datos debió generar un ID para el producto");
        assertEquals("ProductoTest_guardar", resultado.getNombre());
        assertEquals(1, resultado.getDetallesIngredientes().size());
    }

    /**
     * Verifica que el sistema impida guardar productos con nombres duplicados.
     * @throws PersistenciaException Caso esperado.
     */
    @Test
    void guardarProductoDuplicadoLanzaExcepcion() throws PersistenciaException {
        Producto p1 = crearProductoValido("duplicadoA", TipoProducto.PLATILLO);
        p1.setNombre("ProductoDuplicado");

        Producto guardado = productoDAO.guardar(p1);
        registrarIdProductoSiExiste(guardado);

        Producto p2 = crearProductoValido("duplicadoB", TipoProducto.BEBIDA);
        p2.setNombre("ProductoDuplicado");

        assertThrows(PersistenciaException.class, () -> productoDAO.guardar(p2));
    }

    /**
     * Verifica que se lance una excepción si se intenta guardar un producto 
     * con detalles de ingredientes que carecen de ID válido.
     */
    @Test
    void guardarProductoSinIngredienteValidoLanzaExcepcion() {
        DetalleProductoIngrediente detalle = new DetalleProductoIngrediente();
        detalle.setIngrediente(new Ingrediente()); // sin id
        detalle.setCantidadRequerida(50);

        Producto producto = new Producto();
        producto.setNombre("ProductoSinIngrediente");
        producto.setDescripcion("Producto inválido");
        producto.setPrecio(50.0);
        producto.setTipo(TipoProducto.PLATILLO);
        producto.setDisponibilidad(DisponibilidadProducto.NO);
        producto.setActivo(true);
        producto.agregarDetalleIngrediente(detalle);

        assertThrows(PersistenciaException.class, () -> productoDAO.guardar(producto));
    }

    /**
     * Prueba la recuperación exitosa de un producto por su identificador.
     * @throws PersistenciaException Si ocurre un error en la búsqueda.
     */
    @Test
    void buscarPorIdExistente() throws PersistenciaException {
        Producto producto = crearProductoValido("buscarId", TipoProducto.POSTRE);

        Producto guardado = productoDAO.guardar(producto);
        registrarIdProductoSiExiste(guardado);

        Producto encontrado = productoDAO.buscarPorId(guardado.getIdProducto());

        assertNotNull(encontrado);
        assertEquals(guardado.getIdProducto(), encontrado.getIdProducto());
        assertEquals("ProductoTest_buscarId", encontrado.getNombre());
    }

    /**
     * Verifica que la búsqueda por un ID inexistente retorne null.
     * @throws PersistenciaException Si ocurre un error técnico.
     */
    @Test
    void buscarPorIdInexistente() throws PersistenciaException {
        Producto encontrado = productoDAO.buscarPorId(-1L);
        assertNull(encontrado, "Debería retornar null si el producto no existe");
    }

    /**
     * Verifica que buscar por un ID nulo resulte en una excepción de persistencia.
     */
    @Test
    void buscarPorIdNuloLanzaExcepcion() {
        assertThrows(PersistenciaException.class, () -> productoDAO.buscarPorId(null));
    }

    /**
     * Prueba la actualización de los campos básicos de un producto.
     * @throws PersistenciaException Si ocurre un error en la edición.
     */
    @Test
    void editarProducto() throws PersistenciaException {
        Producto producto = crearProductoValido("editar", TipoProducto.BEBIDA);

        Producto guardado = productoDAO.guardar(producto);
        registrarIdProductoSiExiste(guardado);

        guardado.setNombre("ProductoEditado");
        guardado.setPrecio(180.0);
        guardado.setDescripcion("Descripción editada");

        Producto actualizado = productoDAO.editar(guardado);

        assertNotNull(actualizado);
        assertEquals(guardado.getIdProducto(), actualizado.getIdProducto());
        assertEquals("ProductoEditado", actualizado.getNombre());
        assertEquals(180.0, actualizado.getPrecio());
        assertEquals("Descripción editada", actualizado.getDescripcion());
    }

    /**
     * Verifica que no se pueda editar un producto que no tenga ID asignado.
     */
    @Test
    void editarProductoConIdNuloLanzaExcepcion() {
        Producto producto = crearProductoValido("sinIdEditar", TipoProducto.PLATILLO);
        producto.setIdProducto(null);

        assertThrows(PersistenciaException.class, () -> productoDAO.editar(producto));
    }

    /**
     * Prueba la eliminación física de un producto en la base de datos.
     * @throws PersistenciaException Si falla la transacción de eliminación.
     */
    @Test
    void eliminarProductoExistente() throws PersistenciaException {
        Producto producto = crearProductoValido("eliminar", TipoProducto.PLATILLO);

        Producto guardado = productoDAO.guardar(producto);

        boolean eliminado = productoDAO.eliminar(guardado.getIdProducto());

        assertTrue(eliminado, "El método debería retornar true al eliminar exitosamente");
        assertNull(productoDAO.buscarPorId(guardado.getIdProducto()), "El producto ya no debería existir en la BD");
    }

    /**
     * Verifica que el método eliminar retorne false si el ID no corresponde a ningún registro.
     * @throws PersistenciaException Si ocurre un error técnico.
     */
    @Test
    void eliminarProductoInexistente() throws PersistenciaException {
        boolean eliminado = productoDAO.eliminar(-999L);
        assertFalse(eliminado, "Debería retornar false si el producto no existe");
    }

    /**
     * Verifica que la recuperación de todos los productos incluya los recién guardados.
     * @throws PersistenciaException Si falla la consulta.
     */
    @Test
    void obtenerTodosDebeRegresarProductosGuardados() throws PersistenciaException {
        Producto producto = crearProductoValido("obtenerTodos", TipoProducto.POSTRE);

        Producto guardado = productoDAO.guardar(producto);
        registrarIdProductoSiExiste(guardado);

        List<Producto> resultados = productoDAO.obtenerTodos();

        assertNotNull(resultados);
        assertTrue(
                resultados.stream().anyMatch(p -> p.getIdProducto().equals(guardado.getIdProducto())),
                "El producto guardado debería aparecer en obtenerTodos()"
        );
    }

    /**
     * Prueba el filtro de búsqueda por nombre parcial.
     * @throws PersistenciaException Si falla la consulta dinámica.
     */
    @Test
    void buscarPorNombreYTipoPorNombreParcial() throws PersistenciaException {
        Producto producto = crearProductoValido("nombreParcial", TipoProducto.BEBIDA);
        producto.setNombre("Limonada Especial");

        Producto guardado = productoDAO.guardar(producto);
        registrarIdProductoSiExiste(guardado);

        List<Producto> resultados = productoDAO.buscarPorNombreYTipo("Limon", null);

        assertNotNull(resultados);
        assertFalse(resultados.isEmpty());
        assertTrue(
                resultados.stream().anyMatch(p -> p.getIdProducto().equals(guardado.getIdProducto())),
                "El producto debería aparecer al buscar por nombre parcial"
        );
    }

    /**
     * Prueba el filtro de búsqueda por tipo de producto.
     * @throws PersistenciaException Si falla la consulta dinámica.
     */
    @Test
    void buscarPorNombreYTipoPorTipo() throws PersistenciaException {
        Producto producto = crearProductoValido("soloTipo", TipoProducto.POSTRE);

        Producto guardado = productoDAO.guardar(producto);
        registrarIdProductoSiExiste(guardado);

        List<Producto> resultados = productoDAO.buscarPorNombreYTipo(null, TipoProducto.POSTRE);

        assertNotNull(resultados);
        assertFalse(resultados.isEmpty());
        assertTrue(
                resultados.stream().anyMatch(p -> p.getIdProducto().equals(guardado.getIdProducto())),
                "El producto debería aparecer al buscar por tipo"
        );
    }

    /**
     * Prueba la combinación de filtros de nombre y tipo simultáneamente.
     * @throws PersistenciaException Si falla la consulta.
     */
    @Test
    void buscarPorNombreYTipoCombinado() throws PersistenciaException {
        Producto producto = crearProductoValido("combinado", TipoProducto.PLATILLO);
        producto.setNombre("Hamburguesa Suprema");

        Producto guardado = productoDAO.guardar(producto);
        registrarIdProductoSiExiste(guardado);

        List<Producto> resultados = productoDAO.buscarPorNombreYTipo("Hamburguesa", TipoProducto.PLATILLO);

        assertNotNull(resultados);
        assertFalse(resultados.isEmpty());
        assertTrue(
                resultados.stream().anyMatch(p -> p.getIdProducto().equals(guardado.getIdProducto())),
                "El producto debería aparecer al buscar por nombre y tipo"
        );
    }

    /**
     * Verifica que el cambio de estado lógico (activo/inactivo) persista correctamente.
     * @throws PersistenciaException Si ocurre un error en el UPDATE.
     */
    @Test
    void cambiarEstadoActivoDebeActualizarProducto() throws PersistenciaException {
        Producto producto = crearProductoValido("estadoActivo", TipoProducto.BEBIDA);

        Producto guardado = productoDAO.guardar(producto);
        registrarIdProductoSiExiste(guardado);

        productoDAO.cambiarEstadoActivo(guardado.getIdProducto(), false);

        Producto actualizado = productoDAO.buscarPorId(guardado.getIdProducto());

        assertNotNull(actualizado);
        assertFalse(actualizado.getActivo(), "El estado activo debió cambiar a false");
    }

    /**
     * Verifica que la actualización del nivel de disponibilidad se guarde correctamente.
     * @throws PersistenciaException Si falla el UPDATE en la BD.
     */
    @Test
    void actualizarDisponibilidadDebeActualizarProducto() throws PersistenciaException {
        Producto producto = crearProductoValido("disponibilidad", TipoProducto.POSTRE);
        producto.setDisponibilidad(DisponibilidadProducto.NO);

        Producto guardado = productoDAO.guardar(producto);
        registrarIdProductoSiExiste(guardado);

        productoDAO.actualizarDisponibilidad(guardado.getIdProducto(), DisponibilidadProducto.SI);

        Producto actualizado = productoDAO.buscarPorId(guardado.getIdProducto());

        assertNotNull(actualizado);
        assertEquals(DisponibilidadProducto.SI, actualizado.getDisponibilidad());
    }

    /**
     * Verifica que se detecte correctamente un nombre que ya está en uso.
     * @throws PersistenciaException Si falla la consulta de validación.
     */
    @Test
    void existeDuplicadoActivoDebeRetornarTrueSiYaExisteNombre() throws PersistenciaException {
        Producto producto = crearProductoValido("duplicadoActivo", TipoProducto.PLATILLO);
        producto.setNombre("ProductoUnicoTest");

        Producto guardado = productoDAO.guardar(producto);
        registrarIdProductoSiExiste(guardado);

        boolean existe = productoDAO.existeDuplicadoActivo("ProductoUnicoTest", null);

        assertTrue(existe, "Debería detectar duplicado por nombre");
    }

    /**
     * Verifica que la validación de duplicados ignore al producto actual mediante su ID.
     * @throws PersistenciaException Si falla la consulta de validación.
     */
    @Test
    void existeDuplicadoActivoDebeRetornarFalseSiSeExcluyeElMismoId() throws PersistenciaException {
        Producto producto = crearProductoValido("excluirMismo", TipoProducto.PLATILLO);
        producto.setNombre("ProductoExcluir");

        Producto guardado = productoDAO.guardar(producto);
        registrarIdProductoSiExiste(guardado);

        boolean existe = productoDAO.existeDuplicadoActivo("ProductoExcluir", guardado.getIdProducto());

        assertFalse(existe, "No debería detectar duplicado al excluir el mismo ID");
    }
}