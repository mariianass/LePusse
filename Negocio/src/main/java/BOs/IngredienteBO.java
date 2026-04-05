/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BOs;

import DAOs.IngredienteDAO;
import dtos.IngredienteDTO;
import entidades.Ingrediente;
import enums.UnidadMedida;
import excepciones.NegocioException;
import excepciones.PersistenciaException;
import interfaces.IIngredienteBO;
import interfaces.IIngredienteDAO;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementa la lógica de negocio de la entidad Ingrediente. Esta clase se
 * encarga de convertir entre DTO y entidad, y delegar las operaciones
 * correspondientes a la capa de persistencia.
 *
 * @author regina, mariana e isaac.
 */
public class IngredienteBO implements IIngredienteBO {

    private static IngredienteBO instanciaIngredienteBO;
    private IIngredienteDAO ingredienteDAO;

    /**
     * Constructor privado de IngredienteBO.
     */
    private IngredienteBO() {
        this.ingredienteDAO = IngredienteDAO.getInstance();
    }

    /**
     * Obtiene la instancia única de IngredienteBO.
     *
     * @return Instancia única de IngredienteBO.
     */
    public static IngredienteBO getInstance() {
        if (instanciaIngredienteBO == null) {
            instanciaIngredienteBO = new IngredienteBO();
        }
        return instanciaIngredienteBO;
    }

    /**
     * Busca ingredientes aplicando filtros opcionales por nombre y unidad de
     * medida.
     *
     * Si el nombre viene vacío o nulo, no se filtra por nombre.
     * Si la unidad viene nula, no se filtra por unidad.
     * Si ambos vienen vacíos, se devuelven todos los ingredientes.
     *
     * @param nombre Nombre o parte del nombre del ingrediente.
     * @param unidad Unidad de medida seleccionada.
     * @return Lista de ingredientes encontrados.
     * @throws NegocioException Si ocurre un error durante la búsqueda.
     */
    @Override
    public List<IngredienteDTO> buscarPorNombreYUnidad(String nombre, UnidadMedida unidad) throws NegocioException {
        try {
            List<Ingrediente> ingredientes = ingredienteDAO.buscarPorNombreYUnidad(nombre, unidad);
            List<IngredienteDTO> ingredientesDTO = new ArrayList<>();

            if (ingredientes != null) {
                for (Ingrediente ingrediente : ingredientes) {
                    ingredientesDTO.add(convertirDTO(ingrediente));
                }
            }

            return ingredientesDTO;
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al buscar ingredientes por filtros en negocio.", e);
        }
    }

    /**
     * Busca un ingrediente por su identificador único.
     *
     * @param id Identificador único del ingrediente.
     * @return Ingrediente encontrado.
     * @throws NegocioException Si ocurre un error durante la búsqueda.
     */
    @Override
    public IngredienteDTO buscarPorId(Long id) throws NegocioException {
        if (id == null) {
            throw new NegocioException("El id del ingrediente es obligatorio.");
        }

        try {
            Ingrediente ingrediente = ingredienteDAO.buscarPorId(id);

            if (ingrediente == null) {
                throw new NegocioException("No se encontró el ingrediente.");
            }

            return convertirDTO(ingrediente);
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al buscar el ingrediente en negocio.", e);
        }
    }

    /**
     * Convierte un DTO de Ingrediente a su representación en entidad.
     *
     * @param ingredienteDTO DTO del ingrediente.
     * @return Entidad ingrediente.
     */
    private Ingrediente convertirEntidad(IngredienteDTO ingredienteDTO) {
        if (ingredienteDTO == null) {
            return null;
        }

        return new Ingrediente(
                ingredienteDTO.getIdIngrediente(),
                ingredienteDTO.getNombre(),
                ingredienteDTO.getUnidadMedida(),
                ingredienteDTO.getStockActual(),
                ingredienteDTO.getUmbral()
        );
    }

    /**
     * Convierte una entidad Ingrediente a su representación en DTO.
     *
     * @param ingrediente Entidad ingrediente.
     * @return DTO del ingrediente.
     */
    private IngredienteDTO convertirDTO(Ingrediente ingrediente) {
        if (ingrediente == null) {
            return null;
        }

        return new IngredienteDTO(
                ingrediente.getIdIngrediente(),
                ingrediente.getNombre(),
                ingrediente.getUnidadMedida(),
                ingrediente.getStockActual(),
                ingrediente.getUmbral()
        );
    }
}