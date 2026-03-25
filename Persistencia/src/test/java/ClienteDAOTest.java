/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import DAOs.ClienteDAO;
import entidades.Cliente;
import excepciones.PersistenciaException;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
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
    
    @Test
    void guardarClienteValido() throws PersistenciaException {

        // Preparamos un Cliente normal con todos sus campos obligatorios
        
        long numeroAleatorio = (long) (Math.random() * 10000000000L);
        String telefonoUnico = String.valueOf(numeroAleatorio);
        Cliente cliente = new Cliente();
        cliente.setNombre("Regina");
        cliente.setApellidoPaterno("Jimenez");
        cliente.setApellidoMaterno("Meneses");
        cliente.setTelefono(telefonoUnico);
        cliente.setCorreoElectronico("regina@test.com");
        
        Cliente resultado = clienteDAO.guardar(cliente);
        assertNotNull(resultado.getId(), "La base de datos debió generar un ID para el cliente");
        assertEquals("Regina", resultado.getNombre());
       
    }

}
