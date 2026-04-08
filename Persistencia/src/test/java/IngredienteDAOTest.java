/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import DAOs.IngredienteDAO;
import entidades.Ingrediente;
import enums.UnidadMedida;
import excepciones.PersistenciaException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para IngredienteDAO.
 * @author regina, mariana e isaac
 */
public class IngredienteDAOTest {

    private IngredienteDAO ingredienteDAO;
    private List<Long> idsIngredientesCreados;

    @BeforeEach
    void setUp() {
        ingredienteDAO = IngredienteDAO.getInstance();
        idsIngredientesCreados = new ArrayList<>();
    }

    @AfterEach
    void tearDown() {
        for (Long id : idsIngredientesCreados) {
            try {
                ingredienteDAO.eliminar(id);
            } catch (PersistenciaException e) {
            }
        }
    }

    private void registrarId(Ingrediente ing) {
        if (ing != null && ing.getIdIngrediente() != null) {
            idsIngredientesCreados.add(ing.getIdIngrediente());
        }
    }


    @Test
    void testGuardarExitoNuevoIngrediente() throws PersistenciaException {
        Ingrediente ing = new Ingrediente(null, "Cebolla", UnidadMedida.GRAMOS, 1000.0, 100.0);
        ingredienteDAO.guardar(ing);
        registrarId(ing);
        assertNotNull(ing.getIdIngrediente());
    }

    @Test
    void testGuardarExitoMismoNombreDiferenteUnidad() throws PersistenciaException {
        Ingrediente ing1 = new Ingrediente(null, "Mantequila", UnidadMedida.GRAMOS, 500.0, 50.0);
        Ingrediente ing2 = new Ingrediente(null, "Mantequila", UnidadMedida.MILILITROS, 500.0, 50.0);
        
        ingredienteDAO.guardar(ing1);
        registrarId(ing1);
        
        assertDoesNotThrow(() -> {
            ingredienteDAO.guardar(ing2);
            registrarId(ing2);
        });
    }

    @Test
    void testGuardarErrorDuplicadoExacto() throws PersistenciaException {
        Ingrediente ing1 = new Ingrediente(null, "Sal", UnidadMedida.GRAMOS, 100.0, 10.0);
        ingredienteDAO.guardar(ing1);
        registrarId(ing1);

        Ingrediente ing2 = new Ingrediente(null, "Sal", UnidadMedida.GRAMOS, 200.0, 20.0);
        assertThrows(PersistenciaException.class, () -> ingredienteDAO.guardar(ing2));
    }


    @Test
    void testEditarExitoMismoRegistro() throws PersistenciaException {
        Ingrediente ing = new Ingrediente(null, "Harina", UnidadMedida.GRAMOS, 500.0, 50.0);
        ingredienteDAO.guardar(ing);
        registrarId(ing);

        ing.setStockActual(800.0);
        assertDoesNotThrow(() -> ingredienteDAO.editar(ing));
    }

    @Test
    void testEditarErrorConOtroIngrediente() throws PersistenciaException {
        Ingrediente ing1 = new Ingrediente(null, "Leche", UnidadMedida.MILILITROS, 1000.0, 100.0);
        Ingrediente ing2 = new Ingrediente(null, "Agua", UnidadMedida.MILILITROS, 1000.0, 100.0);
        ingredienteDAO.guardar(ing1);
        ingredienteDAO.guardar(ing2);
        registrarId(ing1);
        registrarId(ing2);
        ing2.setNombre("Leche");
        assertThrows(PersistenciaException.class, () -> ingredienteDAO.editar(ing2));
    }

    @Test
    void testExisteDuplicado_ValidacionCruzada() throws PersistenciaException {
        Ingrediente ing = new Ingrediente(null, "Azúcar", UnidadMedida.GRAMOS, 10.0, 1.0);
        ingredienteDAO.guardar(ing);
        registrarId(ing);
        assertTrue(ingredienteDAO.existeDuplicado("Azúcar", UnidadMedida.GRAMOS, null));
        assertFalse(ingredienteDAO.existeDuplicado("Azúcar", UnidadMedida.GRAMOS, ing.getIdIngrediente()));
    }
    
    @Test
    void testActualizarStockExito() throws PersistenciaException {
        Ingrediente ing = new Ingrediente(null, "Carne", UnidadMedida.GRAMOS, 100.0, 10.0);
        ingredienteDAO.guardar(ing);
        registrarId(ing);
        ingredienteDAO.actualizarStock(ing.getIdIngrediente(), 999.0);
        Ingrediente actualizado = ingredienteDAO.buscarPorId(ing.getIdIngrediente());
        assertEquals(999.0, actualizado.getStockActual());
    }

    @Test
    void testEliminarExito() throws PersistenciaException {
        Ingrediente ing = new Ingrediente(null, "Temporal", UnidadMedida.GRAMOS, 1.0, 1.0);
        ingredienteDAO.guardar(ing);
        
        assertTrue(ingredienteDAO.eliminar(ing.getIdIngrediente()));
        assertFalse(ingredienteDAO.eliminar(-99L));
    }
}