/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BOs;

import DAOs.ComandaDAO;
import dtos.ComandaDTO;
import dtos.DetalleComandaDTO;
import enumsDTO.EstadoComandaDTO;
import enumsDTO.EstadoMesaDTO;
import dtos.MesaDTO;
import entidades.Cliente;
import entidades.Comanda;
import entidades.DetalleComanda;
import entidades.Mesa;
import entidades.Producto;
import enums.EstadoComanda;
import enums.EstadoMesa;
import excepciones.NegocioException;
import excepciones.PersistenciaException;
import interfaces.IComandaBO;
import interfaces.IComandaDAO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import validadores.ValidadorComanda;

/**
 * Clase que implementa la lógica de negocio de la entidad Comanda. Se encarga
 * de validar, registrar, consultar, editar, entregar, cancelar y buscar
 * comandas dentro del sistema, así como de obtener las mesas disponibles.
 *
 * @author regina, mariana e isaac.
 */
public class ComandaBO implements IComandaBO {

    private static ComandaBO instanciaComandaBO;
    private final IComandaDAO comandaDAO;

    private ComandaBO() {
        this.comandaDAO = ComandaDAO.getInstance();
    }

    /**
     * Obtiene la instancia única de ComandaBO.
     *
     * @return Instancia única de ComandaBO.
     */
    public static ComandaBO getInstance() {
        if (instanciaComandaBO == null) {
            instanciaComandaBO = new ComandaBO();
        }
        return instanciaComandaBO;
    }

    /**
     * Guarda una nueva comanda en el sistema.
     *
     * @param comandaDTO DTO con la información de la comanda.
     * @return Comanda guardada.
     * @throws NegocioException Si ocurre un error durante el guardado.
     */
    @Override
    public ComandaDTO guardar(ComandaDTO comandaDTO) throws NegocioException {
        try {
            if (comandaDTO.getFechaHoraCreacion() == null) {
                comandaDTO.setFechaHoraCreacion(LocalDateTime.now());
            }

            if (comandaDTO.getEstado() == null) {
                comandaDTO.setEstado(EstadoComandaDTO.ABIERTA);
            }

            if (comandaDTO.getFolio() == null || comandaDTO.getFolio().trim().isEmpty()) {
                comandaDTO.setFolio(generarFolioAutomatico(comandaDTO.getFechaHoraCreacion().toLocalDate()));
            }

            ValidadorComanda.validar(comandaDTO);

            Comanda comandaExistente = comandaDAO.buscarComandaActivaPorMesa(comandaDTO.getIdMesa());
            if (comandaExistente != null) {
                throw new NegocioException("La mesa seleccionada ya cuenta con una comanda activa.");
            }

            Comanda comanda = convertirEntidad(comandaDTO);
            Comanda comandaGuardada = comandaDAO.guardar(comanda);

            return convertirDTO(comandaGuardada);

        } catch (PersistenciaException e) {
            throw new NegocioException("Error al guardar la comanda.", e);
        }
    }

    /**
     * Genera automáticamente el folio de una comanda con el formato
     * OB-YYYYMMDD-XXX.
     *
     * @param fecha Fecha base para generar el folio.
     * @return Folio generado.
     * @throws PersistenciaException Si ocurre un error al consultar el último
     * folio.
     */
    private String generarFolioAutomatico(LocalDate fecha) throws PersistenciaException {
        String fechaTexto = fecha.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String ultimoFolio = comandaDAO.obtenerUltimoFolioDelDia(fecha);

        int consecutivo = 1;

        if (ultimoFolio != null && !ultimoFolio.trim().isEmpty()) {
            String[] partes = ultimoFolio.split("-");

            if (partes.length == 3) {
                try {
                    consecutivo = Integer.parseInt(partes[2]) + 1;
                } catch (NumberFormatException e) {
                    consecutivo = 1;
                }
            }
        }

        return "OB-" + fechaTexto + "-" + String.format("%03d", consecutivo);
    }

    /**
     * Busca una comanda por su identificador único.
     *
     * @param id Identificador único de la comanda.
     * @return Comanda encontrada.
     * @throws NegocioException Si ocurre un error durante la búsqueda.
     */
    @Override
    public ComandaDTO buscarPorId(Long id) throws NegocioException {
        try {
            Comanda comanda = comandaDAO.buscarPorId(id);

            if (comanda == null) {
                throw new NegocioException("No se encontró la comanda solicitada.");
            }

            return convertirDTO(comanda);

        } catch (PersistenciaException e) {
            throw new NegocioException("Error al buscar la comanda por ID.", e);
        }
    }

    /**
     * Edita la información de una comanda existente.
     *
     * @param comandaDTO DTO con la información actualizada de la comanda.
     * @return Comanda actualizada.
     * @throws NegocioException Si ocurre un error durante la edición.
     */
    @Override
    public ComandaDTO editar(ComandaDTO comandaDTO) throws NegocioException {
        try {
            ValidadorComanda.validar(comandaDTO);

            Comanda comandaActual = comandaDAO.buscarPorId(comandaDTO.getIdComanda());
            if (comandaActual == null) {
                throw new NegocioException("No se encontró la comanda que se desea editar.");
            }

            if (comandaActual.getEstado() == EstadoComanda.ENTREGADA
                    || comandaActual.getEstado() == EstadoComanda.CANCELADA) {
                throw new NegocioException("No se puede editar una comanda entregada o cancelada.");
            }

            Comanda comandaActivaMesa = comandaDAO.buscarComandaActivaPorMesa(comandaDTO.getIdMesa());
            if (comandaActivaMesa != null
                    && !comandaActivaMesa.getIdComanda().equals(comandaDTO.getIdComanda())) {
                throw new NegocioException("La mesa seleccionada ya cuenta con otra comanda activa.");
            }

            Comanda comanda = convertirEntidad(comandaDTO);
            Comanda comandaEditada = comandaDAO.editar(comanda);

            return convertirDTO(comandaEditada);

        } catch (PersistenciaException e) {
            throw new NegocioException("Error al editar la comanda.", e);
        }
    }

    /**
     * Marca una comanda como entregada dentro del sistema.
     *
     * @param id Identificador único de la comanda.
     * @return Comanda actualizada con estado entregada.
     * @throws NegocioException Si ocurre un error durante la operación.
     */
    @Override
    public ComandaDTO entregar(Long id) throws NegocioException {
        try {
            Comanda comanda = comandaDAO.buscarPorId(id);

            if (comanda == null) {
                throw new NegocioException("No se encontró la comanda solicitada.");
            }

            if (comanda.getEstado() == EstadoComanda.ENTREGADA) {
                throw new NegocioException("La comanda ya se encuentra entregada.");
            }

            if (comanda.getEstado() == EstadoComanda.CANCELADA) {
                throw new NegocioException("No se puede entregar una comanda cancelada.");
            }

            comanda.setEstado(EstadoComanda.ENTREGADA);

            if (comanda.getMesa() != null) {
                comanda.getMesa().setEstado(EstadoMesa.DISPONIBLE);
            }

            Comanda comandaActualizada = comandaDAO.editar(comanda);
            return convertirDTO(comandaActualizada);

        } catch (PersistenciaException e) {
            throw new NegocioException("Error al entregar la comanda.", e);
        }
    }

    /**
     * Marca una comanda como cancelada dentro del sistema.
     *
     * @param id Identificador único de la comanda.
     * @return Comanda actualizada con estado cancelada.
     * @throws NegocioException Si ocurre un error durante la operación.
     */
    @Override
    public ComandaDTO cancelar(Long id) throws NegocioException {
        try {
            Comanda comanda = comandaDAO.buscarPorId(id);

            if (comanda == null) {
                throw new NegocioException("No se encontró la comanda solicitada.");
            }

            if (comanda.getEstado() == EstadoComanda.CANCELADA) {
                throw new NegocioException("La comanda ya se encuentra cancelada.");
            }

            if (comanda.getEstado() == EstadoComanda.ENTREGADA) {
                throw new NegocioException("No se puede cancelar una comanda entregada.");
            }

            comanda.setEstado(EstadoComanda.CANCELADA);

            if (comanda.getMesa() != null) {
                comanda.getMesa().setEstado(EstadoMesa.DISPONIBLE);
            }

            Comanda comandaActualizada = comandaDAO.editar(comanda);
            return convertirDTO(comandaActualizada);

        } catch (PersistenciaException e) {
            throw new NegocioException("Error al cancelar la comanda.", e);
        }
    }

    /**
     * Busca comandas que coincidan con el filtro proporcionado.
     *
     * @param filtro Texto de búsqueda para filtrar comandas.
     * @return Lista de comandas que coinciden con el filtro.
     * @throws NegocioException Si ocurre un error durante la búsqueda.
     */
    @Override
    public List<ComandaDTO> buscarPorFiltros(String filtro) throws NegocioException {
        try {
            List<Comanda> comandas = comandaDAO.buscarPorFiltros(filtro);
            List<ComandaDTO> comandasDTO = new ArrayList<>();

            for (Comanda comanda : comandas) {
                comandasDTO.add(convertirDTO(comanda));
            }

            return comandasDTO;

        } catch (PersistenciaException e) {
            throw new NegocioException("Error al buscar comandas por filtro.", e);
        }
    }

    /**
     * Obtiene la lista de mesas disponibles dentro del sistema.
     *
     * @return Lista de mesas disponibles.
     * @throws NegocioException Si ocurre un error durante la consulta.
     */
    @Override
    public List<MesaDTO> obtenerMesasDisponibles() throws NegocioException {
        try {
            List<Mesa> mesas = comandaDAO.obtenerMesasDisponibles();
            List<MesaDTO> mesasDTO = new ArrayList<>();

            for (Mesa mesa : mesas) {
                mesasDTO.add(convertirMesaDTO(mesa));
            }

            return mesasDTO;

        } catch (PersistenciaException e) {
            throw new NegocioException("Error al obtener las mesas disponibles.", e);
        }
    }

    /**
     * Convierte una entidad Comanda a su representación DTO.
     *
     * @param comanda Entidad Comanda a convertir.
     * @return DTO con la información de la comanda.
     */
    private ComandaDTO convertirDTO(Comanda comanda) {
        List<DetalleComandaDTO> detallesDTO = new ArrayList<>();

        if (comanda.getDetalles() != null) {
            for (DetalleComanda detalle : comanda.getDetalles()) {
                DetalleComandaDTO detalleDTO = new DetalleComandaDTO(
                        detalle.getIdDetalleComanda(),
                        detalle.getCantidad(),
                        detalle.getComentarioEspecial(),
                        detalle.getPrecio(),
                        detalle.getSubtotal(),
                        detalle.getProducto() != null ? detalle.getProducto().getIdProducto() : null,
                        detalle.getProducto() != null ? detalle.getProducto().getNombre() : null
                );
                detallesDTO.add(detalleDTO);
            }
        }

        Long idMesa = null;
        Integer numeroMesa = null;
        if (comanda.getMesa() != null) {
            idMesa = comanda.getMesa().getIdMesa();
            numeroMesa = comanda.getMesa().getNumeroMesa();
        }

        Long idCliente = null;
        String nombreCliente = null;
        if (comanda.getCliente() != null) {
            idCliente = comanda.getCliente().getId();
            nombreCliente = comanda.getCliente().getNombre()
                    + " " + comanda.getCliente().getApellidoPaterno()
                    + " " + comanda.getCliente().getApellidoMaterno();
        }

        return new ComandaDTO(
                comanda.getIdComanda(),
                comanda.getFolio(),
                comanda.getFechaHoraCreacion(),
                EstadoComandaDTO.valueOf(comanda.getEstado().name()),
                comanda.getTotalVenta(),
                idMesa,
                numeroMesa,
                idCliente,
                nombreCliente,
                detallesDTO
        );
    }

    /**
     * Convierte un DTO de Comanda a su entidad correspondiente.
     *
     * @param comandaDTO DTO de la comanda.
     * @return Entidad Comanda.
     */
    private Comanda convertirEntidad(ComandaDTO comandaDTO) {
        Comanda comanda = new Comanda();
        comanda.setIdComanda(comandaDTO.getIdComanda());
        comanda.setFolio(comandaDTO.getFolio());
        comanda.setFechaHoraCreacion(comandaDTO.getFechaHoraCreacion());
        comanda.setEstado(EstadoComanda.valueOf(comandaDTO.getEstado().name()));
        comanda.setTotalVenta(comandaDTO.getTotalVenta());

        Mesa mesa = new Mesa();
        mesa.setIdMesa(comandaDTO.getIdMesa());
        mesa.setNumeroMesa(comandaDTO.getNumeroMesa());
        mesa.setEstado(EstadoMesa.OCUPADA);
        comanda.setMesa(mesa);

        if (comandaDTO.getIdCliente() != null) {
            Cliente cliente = new Cliente();
            cliente.setId(comandaDTO.getIdCliente());
            comanda.setCliente(cliente);
        }

        List<DetalleComanda> detalles = new ArrayList<>();

        if (comandaDTO.getDetalles() != null) {
            for (DetalleComandaDTO detalleDTO : comandaDTO.getDetalles()) {
                DetalleComanda detalle = new DetalleComanda();
                detalle.setIdDetalleComanda(detalleDTO.getIdDetalleComanda());
                detalle.setCantidad(detalleDTO.getCantidad());
                detalle.setComentarioEspecial(detalleDTO.getComentarioEspecial());
                detalle.setPrecio(detalleDTO.getPrecio());
                detalle.setSubtotal(detalleDTO.getSubtotal());

                Producto producto = new Producto();
                producto.setIdProducto(detalleDTO.getIdProducto());
                detalle.setProducto(producto);

                detalle.setComanda(comanda);
                detalles.add(detalle);
            }
        }

        comanda.setDetalles(detalles);

        return comanda;
    }

    /**
     * Convierte una entidad Mesa a su representación DTO.
     *
     * @param mesa Entidad Mesa a convertir.
     * @return DTO con la información de la mesa.
     */
    private MesaDTO convertirMesaDTO(Mesa mesa) {
        return new MesaDTO(
                mesa.getIdMesa(),
                mesa.getNumeroMesa(),
                EstadoMesaDTO.valueOf(mesa.getEstado().name())
        );
    }
}
