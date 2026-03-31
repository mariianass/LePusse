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
    void tearDown() throws PersistenciaException {
        for (Long id : idsIngredientesCreados) {
            try {
                ingredienteDAO.eliminar(id);
            } catch (PersistenciaException e) {
            }
        }
    }

    private void registrarId(Ingrediente ingrediente) {
        if (ingrediente != null && ingrediente.getIdIngrediente() != null) {
            idsIngredientesCreados.add(ingrediente.getIdIngrediente());
        }
    }

    @Test
    void testGuardarIngredienteValido() throws PersistenciaException {
        Ingrediente ing = new Ingrediente(null, "Pimienta", UnidadMedida.GRAMOS, 500.0, 50.0);
        
        ingredienteDAO.guardar(ing);
        registrarId(ing);

        assertNotNull(ing.getIdIngrediente(), "Debería generar un ID");
        assertTrue(ingredienteDAO.existeDuplicado("Pimienta", UnidadMedida.GRAMOS));
    }

    @Test
    void testGuardarDuplicadoMismoNombreDiferenteUnidad() throws PersistenciaException {
        Ingrediente ing1 = new Ingrediente(null, "Queso", UnidadMedida.GRAMOS, 1000.0, 100.0);
        Ingrediente ing2 = new Ingrediente(null, "Queso", UnidadMedida.PIEZAS, 10.0, 2.0);

        ingredienteDAO.guardar(ing1);
        registrarId(ing1);
        
        ingredienteDAO.guardar(ing2);
        registrarId(ing2);

        assertNotNull(ing1.getIdIngrediente());
        assertNotNull(ing2.getIdIngrediente());
        assertNotEquals(ing1.getIdIngrediente(), ing2.getIdIngrediente());
    }

    @Test
    void testGuardarDuplicadoMismaUnidadLanzaExcepcion() throws PersistenciaException {
        Ingrediente ing1 = new Ingrediente(null, "Sal", UnidadMedida.GRAMOS, 100.0, 10.0);
        ingredienteDAO.guardar(ing1);
        registrarId(ing1);

        Ingrediente ing2 = new Ingrediente(null, "Sal", UnidadMedida.GRAMOS, 200.0, 20.0);

        assertThrows(PersistenciaException.class, () -> {
            ingredienteDAO.guardar(ing2);
        }, "Debería lanzar PersistenciaException por nombre y unidad duplicada");
    }

    @Test
    void testEliminarIngrediente() throws PersistenciaException {
        Ingrediente ing = new Ingrediente(null, "Aceite", UnidadMedida.MILILITROS, 1000.0, 100.0);
        ingredienteDAO.guardar(ing);

        boolean eliminado = ingredienteDAO.eliminar(ing.getIdIngrediente());
        
        assertTrue(eliminado);
        assertFalse(ingredienteDAO.existeDuplicado("Aceite", UnidadMedida.MILILITROS));
    }

    @Test
    void testEliminarInexistente() throws PersistenciaException {
        boolean resultado = ingredienteDAO.eliminar(-1L);
        assertFalse(resultado, "Debería retornar false si el ID no existe");
    }

    @Test
    void testExisteDuplicado() throws PersistenciaException {
        String nombre = "Azúcar";
        UnidadMedida unidad = UnidadMedida.GRAMOS;
        
        assertFalse(ingredienteDAO.existeDuplicado(nombre, unidad));

        Ingrediente ing = new Ingrediente(null, nombre, unidad, 500.0, 50.0);
        ingredienteDAO.guardar(ing);
        registrarId(ing);

        assertTrue(ingredienteDAO.existeDuplicado(nombre, unidad));
    }
}