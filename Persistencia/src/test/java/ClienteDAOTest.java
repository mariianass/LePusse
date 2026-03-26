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
        // 1. Obtenemos la instancia Singleton
        clienteDAO = ClienteDAO.getInstance();      
    }
//    
//    @Test
//    void guardarClienteValido() throws PersistenciaException {
//
//        // Preparamos un Cliente normal con todos sus campos obligatorios
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
    
<<<<<<< HEAD
=======
    @Test
    void guardarClienteValido() throws PersistenciaException {

        // Preparamos un Cliente normal con todos sus campos obligatorios
        Cliente cliente = new Cliente();
        cliente.setNombre("Regina");
        cliente.setApellidoPaterno("Jimenez");
        cliente.setApellidoMaterno("Meneses");
        cliente.setTelefono("6822491867");
        cliente.setCorreoElectronico("regina@test.com");
        
        Cliente resultado = clienteDAO.guardar(cliente);
        assertNotNull(resultado.getId(), "La base de datos debió generar un ID para el cliente");
        assertEquals("Regina", resultado.getNombre());
       
    }

>>>>>>> 4f607930f10b8f0622778147fc827fabfa357e15
}
