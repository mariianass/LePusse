import DAOs.ClienteDAO;
import entidades.Cliente;
import excepciones.PersistenciaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author regin
 */
public class ClienteDAOTest {
    
    private ClienteDAO clienteDAO;
    
    @BeforeEach
    void setUp() {
         //1. Obtenemos la instancia Singleton
        clienteDAO = ClienteDAO.getInstance();      
    }
    
//    @Test
//    void guardarClienteValido() throws PersistenciaException {
//
//        //Preparamos un Cliente normal con todos sus campos obligatorios
//        Cliente cliente = new Cliente();
//        cliente.setNombre("Regina");
//        cliente.setApellidoPaterno("Jimenez");
//        cliente.setApellidoMaterno("Meneses");
//        cliente.setTelefono("6442491867");
//        cliente.setCorreoElectronico("regina@test.com");
//        
//        Cliente resultado = clienteDAO.guardar(cliente);
//        assertNotNull(resultado.getId(), "La base de datos debió generar un ID para el cliente");
//        assertEquals("Regina", resultado.getNombre());
//       
//    }
//
//    @Test
//    void testBuscarPorIdExistente() throws PersistenciaException {
//        //Primero guardamos uno para asegurar que existe
//        Cliente nuevo = new Cliente();
//        nuevo.setNombre("Isaac");
//        nuevo.setApellidoPaterno("Fierro");
//        nuevo.setApellidoMaterno("Gerhardus");
//        nuevo.setTelefono("1234567890");
//        nuevo.setCorreoElectronico("isaac@test.com");
//        
//        Cliente guardado = clienteDAO.guardar(nuevo);
//        
//        Cliente encontrado = clienteDAO.buscarPorId(guardado.getId());
//        
//        assertNotNull(encontrado);
//        assertEquals(guardado.getId(), encontrado.getId());
//    }
//    
//    @Test
//    void testBuscarPorIdInexistente() throws PersistenciaException {
//        // Buscamos un ID que probablemente no exista
//        Cliente encontrado = clienteDAO.buscarPorId(-1L);
//        assertNull(encontrado, "Debería retornar null si el cliente no existe");
//    }
//    
//    @Test
//    void testEditarCliente() throws PersistenciaException {
//        // 1. Guardar
//        Cliente cliente = new Cliente();
//        cliente.setNombre("Jose");
//        cliente.setApellidoPaterno("Trista");
//        cliente.setApellidoMaterno("Rosales");
//        cliente.setTelefono("2346819881");
//        cliente.setCorreoElectronico("jose@test.com");
//        Cliente guardado = clienteDAO.guardar(cliente);
//        
//        // 2. Modificar
//        guardado.setNombre("Jose Joaquin");
//        Cliente actualizado = clienteDAO.editar(guardado);
//        
//        // 3. Verificar
//        assertEquals("Jose Joaquin", actualizado.getNombre());
//        assertEquals(guardado.getId(), actualizado.getId());
//    }
//    
//    @Test
//    void testEliminarCliente() throws PersistenciaException {
//        // 1. Guardar
//        Cliente cliente = new Cliente();
//        cliente.setNombre("Ernesto");
//        cliente.setApellidoPaterno("Cisneros");
//        cliente.setApellidoMaterno("Valenzuela");
//        cliente.setTelefono("8764567652");
//        cliente.setCorreoElectronico("ernesto@test.com");
//        Cliente guardado = clienteDAO.guardar(cliente);
//        
//        // 2. Eliminar
//        boolean eliminado = clienteDAO.eliminar(guardado.getId());
//        
//        // 3. Verificar
//        assertTrue(eliminado, "El método debería retornar true al eliminar exitosamente");
//        assertNull(clienteDAO.buscarPorId(guardado.getId()), "El cliente ya no debería existir en la BD");
//    }
//    
//    @Test
//    void testEliminarInexistente() throws PersistenciaException {
//        boolean resultado = clienteDAO.eliminar(-1L);
//        assertFalse(resultado, "Debería retornar false si el ID no existe");
//    }
//    
//    @Test
//    void testGuardarClienteDuplicadoLanzaExcepcion() throws PersistenciaException {
//        // El teléfono es unico
//        String telDuplicado = "1112223334";
//        Cliente c1 = new Cliente();
//        c1.setNombre("Carmen");
//        c1.setApellidoPaterno("Lopez");
//        c1.setApellidoMaterno("Valencia");
//        c1.setTelefono(telDuplicado);
//        c1.setCorreoElectronico("carmen@test.com");
//        clienteDAO.guardar(c1);
//        
//        Cliente c2 = new Cliente();
//        c2.setNombre("Tristan");
//        c2.setApellidoPaterno("Lugo");
//        c2.setApellidoMaterno("Garcia");
//        c2.setTelefono(telDuplicado);
//        c2.setCorreoElectronico("Tristan@test.com");
//        
//        // Verificamos que lance la excepción de persistencia por violar el constraint Unique
//        assertThrows(PersistenciaException.class, () -> {
//            clienteDAO.guardar(c2);
//        });
//    }
////   
}
