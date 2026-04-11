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
 * Implementa la lógica de negocio de la entidad Producto.
 * Convierte entre DTO y entidad, valida reglas de negocio y delega
 * las operaciones a la capa de persistencia.
 *
 * @author regina, mariana e isaac
 */
public class ProductoBO implements IProductoBO {

    private static ProductoBO instanciaProductoBO;
    private IProductoDAO productoDAO;

    private ProductoBO() {
        this.productoDAO = ProductoDAO.getInstance();
    }

    public static ProductoBO getInstance() {
        if (instanciaProductoBO == null) {
            instanciaProductoBO = new ProductoBO();
        }
        return instanciaProductoBO;
    }

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
     * El producto está disponible si todos sus ingredientes tienen stock suficiente.
     *
     * @param producto Producto a evaluar.
     * @return SI si todos los ingredientes tienen stock suficiente, NO en caso contrario.
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
