/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BOs;

import DAOs.ProductoDAO;
import dtos.DetalleProductoIngredienteDTO;
import dtos.IngredienteDTO;
import dtos.ProductoDTO;
import entidades.DetalleProductoIngrediente;
import entidades.Ingrediente;
import entidades.Producto;
import enums.DisponibilidadProducto;
import enums.TipoProducto;
import enums.UnidadMedida;
import enumsDTO.DisponibilidadProductoDTO;
import enumsDTO.TipoProductoDTO;
import enumsDTO.UnidadMedidaDTO;
import excepciones.NegocioException;
import excepciones.PersistenciaException;
import interfaces.IProductoBO;
import interfaces.IProductoDAO;
import java.util.ArrayList;
import java.util.List;
import validadores.ValidadorProducto;

/**
 * Clase que implementa la lógica de negocio para la gestión de productos.
 * 
 * Se encarga de validar la información recibida, convertir entre entidades
 * y DTOs, aplicar reglas de negocio y delegar las operaciones correspondientes
 * a la capa de persistencia.
 * 
 * Implementa el patrón Singleton para asegurar una única instancia durante
 * la ejecución del sistema.
 *
 * @author Regina, Mariana e Isaac
 */
public class ProductoBO implements IProductoBO {

    /**
     * Instancia única de la clase ProductoBO.
     */
    private static ProductoBO instanciaProductoBO;

    /**
     * Objeto de acceso a datos para la gestión de productos.
     */
    private IProductoDAO productoDAO;

    /**
     * Constructor privado para aplicar el patrón Singleton.
     */
    private ProductoBO() {
        this.productoDAO = ProductoDAO.getInstance();
    }

    /**
     * Obtiene la instancia única de la clase ProductoBO.
     * 
     * @return instancia única de ProductoBO.
     */
    public static ProductoBO getInstance() {
        if (instanciaProductoBO == null) {
            instanciaProductoBO = new ProductoBO();
        }
        return instanciaProductoBO;
    }

    /**
     * Guarda un nuevo producto en el sistema.
     * 
     * Primero valida la información del producto, después convierte el DTO
     * a entidad, calcula su disponibilidad con base en sus ingredientes
     * y finalmente delega el guardado a la capa de persistencia.
     *
     * @param productoDTO producto a guardar.
     * @return producto guardado como DTO.
     * @throws NegocioException si ocurre un error de validación o persistencia.
     */
    @Override
    public ProductoDTO guardar(ProductoDTO productoDTO) throws NegocioException {
        ValidadorProducto.validar(productoDTO);

        try {
            Producto producto = convertirEntidad(productoDTO);
            producto.setDisponibilidad(calcularDisponibilidad(producto));
            Producto guardado = productoDAO.guardar(producto);
            return convertirDTO(guardado);
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al guardar el producto en negocio.", e);
        }
    }

    /**
     * Edita un producto existente.
     * 
     * Verifica que el producto y su identificador sean válidos, valida sus datos,
     * actualiza su disponibilidad y delega la edición a la capa de persistencia.
     *
     * @param productoDTO producto con la información actualizada.
     * @return producto editado como DTO.
     * @throws NegocioException si ocurre un error de validación o persistencia.
     */
    @Override
    public ProductoDTO editar(ProductoDTO productoDTO) throws NegocioException {
        if (productoDTO == null) {
            throw new NegocioException("El producto no puede ser nulo.");
        }

        if (productoDTO.getIdProducto() == null) {
            throw new NegocioException("El ID del producto es obligatorio para editar.");
        }

        ValidadorProducto.validar(productoDTO);

        try {
            Producto producto = convertirEntidad(productoDTO);
            producto.setDisponibilidad(calcularDisponibilidad(producto));
            Producto editado = productoDAO.editar(producto);
            return convertirDTO(editado);
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al editar el producto en negocio.", e);
        }
    }

    /**
     * Busca un producto por su identificador.
     *
     * @param id identificador del producto.
     * @return producto encontrado como DTO.
     * @throws NegocioException si el ID es nulo, no se encuentra el producto
     * o ocurre un error en persistencia.
     */
    @Override
    public ProductoDTO buscarPorId(Long id) throws NegocioException {
        if (id == null) {
            throw new NegocioException("El ID del producto es obligatorio.");
        }

        try {
            Producto producto = productoDAO.buscarPorId(id);

            if (producto == null) {
                throw new NegocioException("No se encontró el producto.");
            }

            return convertirDTO(producto);
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al buscar el producto en negocio.", e);
        }
    }

    /**
     * Elimina un producto del sistema a partir de su identificador.
     *
     * @param id identificador del producto a eliminar.
     * @throws NegocioException si el ID es nulo, el producto no existe
     * o ocurre un error en persistencia.
     */
    @Override
    public void eliminar(Long id) throws NegocioException {
        if (id == null) {
            throw new NegocioException("El ID del producto es obligatorio para eliminar.");
        }

        try {
            boolean eliminado = productoDAO.eliminar(id);

            if (!eliminado) {
                throw new NegocioException("El producto a eliminar no existe.");
            }
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al eliminar el producto en negocio.", e);
        }
    }

    /**
     * Obtiene todos los productos registrados en el sistema.
     *
     * @return lista de productos como DTO.
     * @throws NegocioException si ocurre un error en la capa de persistencia.
     */
    @Override
    public List<ProductoDTO> obtenerTodos() throws NegocioException {
        try {
            List<Producto> productos = productoDAO.obtenerTodos();
            List<ProductoDTO> productosDTO = new ArrayList<>();

            if (productos != null) {
                for (Producto producto : productos) {
                    productosDTO.add(convertirDTO(producto));
                }
            }

            return productosDTO;
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al obtener todos los productos en negocio.", e);
        }
    }

    /**
     * Busca productos filtrando por nombre y tipo.
     * 
     * Este método permite realizar búsquedas específicas sin necesidad
     * de recuperar toda la lista de productos.
     *
     * @param nombre nombre o parte del nombre del producto.
     * @param tipoDTO tipo de producto a filtrar.
     * @return lista de productos que coinciden con los criterios.
     * @throws NegocioException si ocurre un error en la búsqueda.
     */
    @Override
    public List<ProductoDTO> buscarPorNombreYTipo(String nombre, TipoProductoDTO tipoDTO) throws NegocioException {
        try {
            TipoProducto tipoEntidad = convertirTipoEntidad(tipoDTO);

            List<Producto> productos = productoDAO.buscarPorNombreYTipo(nombre, tipoEntidad);
            List<ProductoDTO> productosDTO = new ArrayList<>();

            if (productos != null) {
                for (Producto producto : productos) {
                    productosDTO.add(convertirDTO(producto));
                }
            }

            return productosDTO;
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al buscar productos por nombre y tipo en negocio.", e);
        }
    }

    /**
     * Cambia el estado lógico de un producto.
     *
     * @param idProducto identificador del producto.
     * @param activo nuevo estado del producto.
     * @throws NegocioException si el ID o el estado son nulos,
     * o si ocurre un error en persistencia.
     */
    @Override
    public void cambiarEstadoActivo(Long idProducto, Boolean activo) throws NegocioException {
        if (idProducto == null) {
            throw new NegocioException("El ID del producto es obligatorio.");
        }

        if (activo == null) {
            throw new NegocioException("El estado activo es obligatorio.");
        }

        try {
            productoDAO.cambiarEstadoActivo(idProducto, activo);
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al cambiar el estado del producto en negocio.", e);
        }
    }

    /**
     * Actualiza la disponibilidad de un producto en función de sus ingredientes.
     *
     * @param idProducto identificador del producto.
     * @throws NegocioException si el ID es nulo, no se encuentra el producto
     * o ocurre un error en persistencia.
     */
    @Override
    public void actualizarDisponibilidad(Long idProducto) throws NegocioException {
        if (idProducto == null) {
            throw new NegocioException("El ID del producto es obligatorio.");
        }

        try {
            Producto producto = productoDAO.buscarPorId(idProducto);

            if (producto == null) {
                throw new NegocioException("No se encontró el producto.");
            }

            DisponibilidadProducto disponibilidad = calcularDisponibilidad(producto);
            productoDAO.actualizarDisponibilidad(idProducto, disponibilidad);

        } catch (PersistenciaException e) {
            throw new NegocioException("Error al actualizar la disponibilidad del producto.", e);
        }
    }

    /**
     * Calcula si un producto está disponible con base en los ingredientes requeridos.
     * 
     * El producto se considera disponible únicamente si todos sus ingredientes
     * tienen stock suficiente para cubrir la cantidad requerida.
     *
     * @param producto producto a evaluar.
     * @return {@code DisponibilidadProducto.SI} si todos los ingredientes tienen
     * stock suficiente; {@code DisponibilidadProducto.NO} en caso contrario.
     */
    private DisponibilidadProducto calcularDisponibilidad(Producto producto) {
        if (producto == null || producto.getDetallesIngredientes() == null || producto.getDetallesIngredientes().isEmpty()) {
            return DisponibilidadProducto.NO;
        }

        for (DetalleProductoIngrediente detalle : producto.getDetallesIngredientes()) {
            if (detalle == null || detalle.getIngrediente() == null) {
                return DisponibilidadProducto.NO;
            }

            Double stock = detalle.getIngrediente().getStockActual();
            Integer cantidad = detalle.getCantidadRequerida();

            if (stock == null || cantidad == null || stock < cantidad) {
                return DisponibilidadProducto.NO;
            }
        }

        return DisponibilidadProducto.SI;
    }

    /**
     * Convierte un objeto {@code ProductoDTO} en una entidad {@code Producto}.
     *
     * @param productoDTO DTO a convertir.
     * @return entidad Producto equivalente.
     */
    private Producto convertirEntidad(ProductoDTO productoDTO) {
        if (productoDTO == null) {
            return null;
        }

        Producto producto = new Producto();
        producto.setIdProducto(productoDTO.getIdProducto());
        producto.setNombre(productoDTO.getNombre());
        producto.setDescripcion(productoDTO.getDescripcion());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setTipo(convertirTipoEntidad(productoDTO.getTipo()));
        producto.setDisponibilidad(convertirDisponibilidadEntidad(productoDTO.getDisponibilidad()));
        producto.setActivo(productoDTO.getActivo());
        producto.setRutaImagen(productoDTO.getRutaImagen());

        List<DetalleProductoIngrediente> detalles = new ArrayList<>();

        if (productoDTO.getDetallesIngredientes() != null) {
            for (DetalleProductoIngredienteDTO detalleDTO : productoDTO.getDetallesIngredientes()) {
                DetalleProductoIngrediente detalle = convertirDetalleEntidad(detalleDTO);
                detalle.setProducto(producto);
                detalles.add(detalle);
            }
        }

        producto.setDetallesIngredientes(detalles);
        return producto;
    }

    /**
     * Convierte una entidad {@code Producto} en un objeto {@code ProductoDTO}.
     *
     * @param producto entidad a convertir.
     * @return DTO equivalente.
     */
    private ProductoDTO convertirDTO(Producto producto) {
        if (producto == null) {
            return null;
        }

        List<DetalleProductoIngredienteDTO> detallesDTO = new ArrayList<>();

        if (producto.getDetallesIngredientes() != null) {
            for (DetalleProductoIngrediente detalle : producto.getDetallesIngredientes()) {
                detallesDTO.add(convertirDetalleDTO(detalle));
            }
        }

        return new ProductoDTO(
                producto.getIdProducto(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                convertirTipoDTO(producto.getTipo()),
                convertirDisponibilidadDTO(producto.getDisponibilidad()),
                producto.getActivo(),
                producto.getRutaImagen(),
                detallesDTO
        );
    }

    /**
     * Convierte un {@code DetalleProductoIngredienteDTO} en una entidad
     * {@code DetalleProductoIngrediente}.
     *
     * @param detalleDTO DTO a convertir.
     * @return entidad equivalente.
     */
    private DetalleProductoIngrediente convertirDetalleEntidad(DetalleProductoIngredienteDTO detalleDTO) {
        if (detalleDTO == null) {
            return null;
        }

        DetalleProductoIngrediente detalle = new DetalleProductoIngrediente();
        detalle.setIdDetalleProductoIngrediente(detalleDTO.getIdDetalleProductoIngrediente());
        detalle.setIngrediente(convertirIngredienteEntidad(detalleDTO.getIngrediente()));
        detalle.setCantidadRequerida(detalleDTO.getCantidadRequerida());
        return detalle;
    }

    /**
     * Convierte una entidad {@code DetalleProductoIngrediente} en un
     * {@code DetalleProductoIngredienteDTO}.
     *
     * @param detalle entidad a convertir.
     * @return DTO equivalente.
     */
    private DetalleProductoIngredienteDTO convertirDetalleDTO(DetalleProductoIngrediente detalle) {
        if (detalle == null) {
            return null;
        }

        return new DetalleProductoIngredienteDTO(
                detalle.getIdDetalleProductoIngrediente(),
                convertirIngredienteDTO(detalle.getIngrediente()),
                detalle.getCantidadRequerida()
        );
    }

    /**
     * Convierte un {@code IngredienteDTO} en una entidad {@code Ingrediente}.
     *
     * @param ingredienteDTO DTO a convertir.
     * @return entidad equivalente.
     */
    private Ingrediente convertirIngredienteEntidad(IngredienteDTO ingredienteDTO) {
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
     * Convierte una entidad {@code Ingrediente} en un {@code IngredienteDTO}.
     *
     * @param ingrediente entidad a convertir.
     * @return DTO equivalente.
     */
    private IngredienteDTO convertirIngredienteDTO(Ingrediente ingrediente) {
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
     * Convierte un {@code TipoProductoDTO} en un {@code TipoProducto}.
     *
     * @param tipoDTO tipo DTO a convertir.
     * @return tipo de entidad equivalente.
     */
    private TipoProducto convertirTipoEntidad(TipoProductoDTO tipoDTO) {
        if (tipoDTO == null) {
            return null;
        }

        switch (tipoDTO) {
            case PLATILLO:
                return TipoProducto.PLATILLO;
            case BEBIDA:
                return TipoProducto.BEBIDA;
            case POSTRE:
                return TipoProducto.POSTRE;
            default:
                throw new IllegalArgumentException("Tipo de producto DTO no válido: " + tipoDTO);
        }
    }

    /**
     * Convierte un {@code TipoProducto} en un {@code TipoProductoDTO}.
     *
     * @param tipo tipo de entidad a convertir.
     * @return tipo DTO equivalente.
     */
    private TipoProductoDTO convertirTipoDTO(TipoProducto tipo) {
        if (tipo == null) {
            return null;
        }

        switch (tipo) {
            case PLATILLO:
                return TipoProductoDTO.PLATILLO;
            case BEBIDA:
                return TipoProductoDTO.BEBIDA;
            case POSTRE:
                return TipoProductoDTO.POSTRE;
            default:
                throw new IllegalArgumentException("Tipo de producto no válido: " + tipo);
        }
    }

    /**
     * Convierte una {@code DisponibilidadProductoDTO} en una
     * {@code DisponibilidadProducto}.
     *
     * @param disponibilidadDTO disponibilidad DTO a convertir.
     * @return disponibilidad de entidad equivalente.
     */
    private DisponibilidadProducto convertirDisponibilidadEntidad(DisponibilidadProductoDTO disponibilidadDTO) {
        if (disponibilidadDTO == null) {
            return null;
        }

        switch (disponibilidadDTO) {
            case SI:
                return DisponibilidadProducto.SI;
            case NO:
                return DisponibilidadProducto.NO;
            default:
                throw new IllegalArgumentException("Disponibilidad DTO no válida: " + disponibilidadDTO);
        }
    }

    /**
     * Convierte una {@code DisponibilidadProducto} en una
     * {@code DisponibilidadProductoDTO}.
     *
     * @param disponibilidad disponibilidad de entidad a convertir.
     * @return disponibilidad DTO equivalente.
     */
    private DisponibilidadProductoDTO convertirDisponibilidadDTO(DisponibilidadProducto disponibilidad) {
        if (disponibilidad == null) {
            return null;
        }

        switch (disponibilidad) {
            case SI:
                return DisponibilidadProductoDTO.SI;
            case NO:
                return DisponibilidadProductoDTO.NO;
            default:
                throw new IllegalArgumentException("Disponibilidad no válida: " + disponibilidad);
        }
    }

    /**
     * Convierte una {@code UnidadMedidaDTO} en una {@code UnidadMedida}.
     *
     * @param unidadMedidaDTO unidad de medida DTO a convertir.
     * @return unidad de medida de entidad equivalente.
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
     * Convierte una {@code UnidadMedida} en una {@code UnidadMedidaDTO}.
     *
     * @param unidadMedida unidad de medida de entidad a convertir.
     * @return unidad de medida DTO equivalente.
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
}