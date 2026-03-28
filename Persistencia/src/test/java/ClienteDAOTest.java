import DAOs.ClienteDAO;
import entidades.Cliente;
import excepciones.PersistenciaException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author regin
 */
public class ClienteDAOTest {
    
    private ClienteDAO clienteDAO;
    private List<Long> idsClientesCreados;
    
    @BeforeEach
    void setUp() {
        clienteDAO = ClienteDAO.getInstance();
        idsClientesCreados = new ArrayList<>();
    }
    
    @AfterEach
    void tearDown() throws PersistenciaException {
        for (Long id : idsClientesCreados) {
            try {
                clienteDAO.eliminar(id);
            } catch (PersistenciaException e) {
                // Ignorar si ya fue eliminado o no existe
            }
        }
    }
    
    private void registrarIdSiExiste(Cliente cliente) {
        if (cliente != null && cliente.getId() != null) {
            idsClientesCreados.add(cliente.getId());
        }
    }
    
    @Test
    void guardarClienteValido() throws PersistenciaException {
        Cliente cliente = new Cliente();
        cliente.setNombre("Regina");
        cliente.setApellidoPaterno("Jimenez");
        cliente.setApellidoMaterno("Meneses");
        cliente.setTelefono("6441112222");
        cliente.setCorreoElectronico("regina@test.com");
        
        Cliente resultado = clienteDAO.guardar(cliente);
        registrarIdSiExiste(resultado);

        assertNotNull(resultado.getId(), "La base de datos debió generar un ID para el cliente");
        assertEquals("Regina", resultado.getNombre());
    }

    @Test
    void testBuscarPorIdExistente() throws PersistenciaException {
        Cliente nuevo = new Cliente();
        nuevo.setNombre("Isaac");
        nuevo.setApellidoPaterno("Fierro");
        nuevo.setApellidoMaterno("Gerhardus");
        nuevo.setTelefono("1234567890");
        nuevo.setCorreoElectronico("isaac@test.com");
        
        Cliente guardado = clienteDAO.guardar(nuevo);
        registrarIdSiExiste(guardado);
        
        Cliente encontrado = clienteDAO.buscarPorId(guardado.getId());
        
        assertNotNull(encontrado);
        assertEquals(guardado.getId(), encontrado.getId());
    }
    
    @Test
    void testBuscarPorIdInexistente() throws PersistenciaException {
        Cliente encontrado = clienteDAO.buscarPorId(-1L);
        assertNull(encontrado, "Debería retornar null si el cliente no existe");
    }
    
    @Test
    void testEditarCliente() throws PersistenciaException {
        Cliente cliente = new Cliente();
        cliente.setNombre("Jose");
        cliente.setApellidoPaterno("Trista");
        cliente.setApellidoMaterno("Rosales");
        cliente.setTelefono("2346819881");
        cliente.setCorreoElectronico("jose@test.com");
        
        Cliente guardado = clienteDAO.guardar(cliente);
        registrarIdSiExiste(guardado);
        
        guardado.setNombre("Jose Joaquin");
        Cliente actualizado = clienteDAO.editar(guardado);
        
        assertEquals("Jose Joaquin", actualizado.getNombre());
        assertEquals(guardado.getId(), actualizado.getId());
    }
    
    @Test
    void testEliminarCliente() throws PersistenciaException {
        Cliente cliente = new Cliente();
        cliente.setNombre("Ernesto");
        cliente.setApellidoPaterno("Cisneros");
        cliente.setApellidoMaterno("Valenzuela");
        cliente.setTelefono("8764567652");
        cliente.setCorreoElectronico("ernesto@test.com");
        
        Cliente guardado = clienteDAO.guardar(cliente);
        
        boolean eliminado = clienteDAO.eliminar(guardado.getId());
        
        assertTrue(eliminado, "El método debería retornar true al eliminar exitosamente");
        assertNull(clienteDAO.buscarPorId(guardado.getId()), "El cliente ya no debería existir en la BD");
    }
    
    @Test
    void testEliminarInexistente() throws PersistenciaException {
        boolean resultado = clienteDAO.eliminar(-1L);
        assertFalse(resultado, "Debería retornar false si el ID no existe");
    }
    
    @Test
    void testGuardarClienteDuplicadoLanzaExcepcion() throws PersistenciaException {
        String telDuplicado = "1112223334";
        
        Cliente c1 = new Cliente();
        c1.setNombre("Carmen");
        c1.setApellidoPaterno("Lopez");
        c1.setApellidoMaterno("Valencia");
        c1.setTelefono(telDuplicado);
        c1.setCorreoElectronico("carmen@test.com");
        
        Cliente guardado1 = clienteDAO.guardar(c1);
        registrarIdSiExiste(guardado1);
        
        Cliente c2 = new Cliente();
        c2.setNombre("Tristan");
        c2.setApellidoPaterno("Lugo");
        c2.setApellidoMaterno("Garcia");
        c2.setTelefono(telDuplicado);
        c2.setCorreoElectronico("Tristan@test.com");
        
        assertThrows(PersistenciaException.class, () -> {
            clienteDAO.guardar(c2);
        });
    }
   
    @Test
    void buscarPorFiltroNombreCompleto() throws PersistenciaException {
        Cliente cliente = new Cliente();
        cliente.setNombre("Leonel");
        cliente.setApellidoPaterno("Martinez");
        cliente.setApellidoMaterno("Hernandez");
        cliente.setTelefono("1764532731");
        cliente.setCorreoElectronico("Leonel@test.com");
        
        Cliente guardado = clienteDAO.guardar(cliente);
        registrarIdSiExiste(guardado);
        
        List<Cliente> resultados = clienteDAO.buscarPorFiltros("Leonel Martinez Hernandez");
        
        assertNotNull(resultados);
        assertFalse(resultados.isEmpty(), "La búsqueda debería regresar al menos un cliente");
        assertTrue(
            resultados.stream().anyMatch(c -> c.getId().equals(guardado.getId())),
            "El cliente guardado debería aparecer en los resultados"
        );
    }
    
    @Test
    void buscarPorFiltroNombreParcial() throws PersistenciaException {
        Cliente cliente = new Cliente();
        cliente.setNombre("Martina");
        cliente.setApellidoPaterno("Orduño");
        cliente.setApellidoMaterno("Lopez");
        cliente.setTelefono("9874536723");
        cliente.setCorreoElectronico("mariana@test.com");

        Cliente guardado = clienteDAO.guardar(cliente);
        registrarIdSiExiste(guardado);

        List<Cliente> resultados = clienteDAO.buscarPorFiltros("Martina");

        assertNotNull(resultados);
        assertFalse(resultados.isEmpty(), "La búsqueda parcial por nombre debería encontrar resultados");
        assertTrue(
                resultados.stream().anyMatch(c -> c.getId().equals(guardado.getId())),
                "El cliente guardado debería aparecer en los resultados"
        );
    }
    
    @Test
    void buscarPorFiltroTelefono() throws PersistenciaException {
        Cliente cliente = new Cliente();
        cliente.setNombre("Ana");
        cliente.setApellidoPaterno("Torres");
        cliente.setApellidoMaterno("Ruiz");
        cliente.setTelefono("6871614264");
        cliente.setCorreoElectronico("ana@test.com");

        Cliente guardado = clienteDAO.guardar(cliente);
        registrarIdSiExiste(guardado);

        List<Cliente> resultados = clienteDAO.buscarPorFiltros("6871614264");

        assertNotNull(resultados);
        assertFalse(resultados.isEmpty(), "La búsqueda por teléfono debería encontrar resultados");
        assertTrue(
                resultados.stream().anyMatch(c -> c.getId().equals(guardado.getId())),
                "El cliente guardado debería aparecer en los resultados"
        );
    }
    
    @Test
    void buscarPorFiltroCorreo() throws PersistenciaException {
        Cliente cliente = new Cliente();
        cliente.setNombre("Luis");
        cliente.setApellidoPaterno("Perez");
        cliente.setApellidoMaterno("Diaz");
        cliente.setTelefono("6445738790");
        cliente.setCorreoElectronico("luis.perez@test.com");

        Cliente guardado = clienteDAO.guardar(cliente);
        registrarIdSiExiste(guardado);

        List<Cliente> resultados = clienteDAO.buscarPorFiltros("luis.perez@test.com");

        assertNotNull(resultados);
        assertFalse(resultados.isEmpty(), "La búsqueda por correo debería encontrar resultados");
        assertTrue(
                resultados.stream().anyMatch(c -> c.getId().equals(guardado.getId())),
                "El cliente guardado debería aparecer en los resultados"
        );
    }
    
    @Test
    void buscarPorFiltroInexistente() throws PersistenciaException {
        List<Cliente> resultados = clienteDAO.buscarPorFiltros("noexiste123456");

        assertNotNull(resultados);
        assertTrue(resultados.isEmpty(), "No debería encontrar clientes con un filtro inexistente");
    }
}