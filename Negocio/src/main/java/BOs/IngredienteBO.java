/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BOs;

import DAOs.IngredienteDAO;
import dtos.IngredienteDTO;
import entidades.Ingrediente;
import enums.UnidadMedida;
import enumsDTO.UnidadMedidaDTO;
import excepciones.NegocioException;
import excepciones.PersistenciaException;
import interfaces.IIngredienteBO;
import interfaces.IIngredienteDAO;
import java.util.ArrayList;
import java.util.List;
import static validadores.ValidadorIngrediente.validar;

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
     * Guarda un nuevo ingrediente en el sistema.
     *
     * @param ingredienteDTO DTO con la información del ingrediente.
     * @return Ingrediente guardado.
     * @throws NegocioException Si ocurre un error durante el guardado.
     */
    @Override
    public IngredienteDTO guardar(IngredienteDTO ingredienteDTO) throws NegocioException {
        validar(ingredienteDTO);

        try {
            Ingrediente ingrediente = convertirEntidad(ingredienteDTO);
            ingredienteDAO.guardar(ingrediente);
            return convertirDTO(ingrediente);
        } catch (PersistenciaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
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
    public List<IngredienteDTO> buscarPorNombreYUnidad(String nombre, UnidadMedidaDTO unidad) throws NegocioException {
        try {
            List<Ingrediente> ingredientes = ingredienteDAO.buscarPorNombreYUnidad(nombre, convertirUnidadMedidaEntidad(unidad));
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
               convertirUnidadMedidaEntidad(ingredienteDTO.getUnidadMedida()),
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
               convertirUnidadMedidaDTO(ingrediente.getUnidadMedida()),
               ingrediente.getStockActual(),
               ingrediente.getUmbral()
       );
   }

   /**
    * Convierte un enum UnidadMedidaDTO a UnidadMedida.
    *
    * @param unidadMedidaDTO Unidad de medida del DTO.
    * @return Unidad de medida de la entidad.
    */
   private UnidadMedida convertirUnidadMedidaEntidad(UnidadMedidaDTO unidadMedidaDTO) {
       if (unidadMedidaDTO == null) {
           return null;
       }

       switch (unidadMedidaDTO) {
           case PIEZAS:
               return UnidadMedida.PIEZAS;
           case GRAMOS:
               return UnidadMedida.GRAMOS;
           case MILILITROS:
               return UnidadMedida.MILILITROS;
           default:
               throw new IllegalArgumentException("Unidad de medida DTO no válida: " + unidadMedidaDTO);
       }
   }

   /**
    * Convierte un enum UnidadMedida a UnidadMedidaDTO.
    *
    * @param unidadMedida Unidad de medida de la entidad.
    * @return Unidad de medida del DTO.
    */
   private UnidadMedidaDTO convertirUnidadMedidaDTO(UnidadMedida unidadMedida) {
       if (unidadMedida == null) {
           return null;
       }

       switch (unidadMedida) {
           case PIEZAS:
               return UnidadMedidaDTO.PIEZAS;
           case GRAMOS:
               return UnidadMedidaDTO.GRAMOS;
           case MILILITROS:
               return UnidadMedidaDTO.MILILITROS;
           default:
               throw new IllegalArgumentException("Unidad de medida no válida: " + unidadMedida);
       }
   }

   /**
     * Actualiza la información de un ingrediente existente en el sistema.
     * Primero valida que los datos cumplan con las reglas de negocio y que el ID no sea nulo.
     * @param ingredienteDTO DTO con los nuevos datos del ingrediente.
     * @return DTO del ingrediente actualizado y sincronizado con la base de datos.
     * @throws NegocioException Si los datos son inválidos, el ID es nulo, o si ocurre 
     * un error de duplicidad o persistencia.
     */
    @Override
    public IngredienteDTO editar(IngredienteDTO ingredienteDTO) throws NegocioException {
        validar(ingredienteDTO);
        
        if (ingredienteDTO.getIdIngrediente() == null) {
            throw new NegocioException("No se puede editar un ingrediente sin su ID.");
        }

        try {
            Ingrediente ingrediente = convertirEntidad(ingredienteDTO);
            Ingrediente actualizado = ingredienteDAO.editar(ingrediente);
            return convertirDTO(actualizado);
        } catch (PersistenciaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    /**
     * Elimina de forma definitiva un ingrediente del sistema mediante su identificador.
     * @param id Identificador único del ingrediente a eliminar.
     * @throws NegocioException Si el ID es nulo, si el ingrediente no existe 
     * o si ocurre un error en la capa de persistencia.
     */
    @Override
    public void eliminar(Long id) throws NegocioException {
        if (id == null) {
            throw new NegocioException("El ID del ingrediente es obligatorio para eliminar.");
        }
        try {
            boolean eliminado = ingredienteDAO.eliminar(id);
            if (!eliminado) {
                throw new NegocioException("El ingrediente a eliminar no existe en el sistema.");
            }
        } catch (PersistenciaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    /**
     * Actualiza de manera rápida y directa únicamente el stock actual de un ingrediente.
     * Ideal para procesos de inventario donde no se desea modificar el resto de atributos.
     * @param id Identificador único del ingrediente.
     * @param cantidadNeta Nuevo valor de stock que se asignará.
     * @throws NegocioException Si el ID es nulo, la cantidad es nula o negativa, 
     * o si hay un error en la base de datos.
     */
    @Override
    public void actualizarStock(Long id, Double cantidadNeta) throws NegocioException {
        if (id == null) {
            throw new NegocioException("ID obligatorio.");
        }
        if (cantidadNeta == null || cantidadNeta < 0) {
            throw new NegocioException("El stock no puede ser negativo.");
        }

        try {
            ingredienteDAO.actualizarStock(id, cantidadNeta);
        } catch (PersistenciaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    
    }
    
}