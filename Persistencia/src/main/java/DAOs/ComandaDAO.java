/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import conexion.ConexionBD;
import entidades.Cliente;
import entidades.Comanda;
import entidades.DetalleComanda;
import entidades.Mesa;
import entidades.Producto;
import enums.EstadoComanda;
import enums.EstadoMesa;
import excepciones.PersistenciaException;
import interfaces.IComandaDAO;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * Clase encargada de la persistencia de la entidad Comanda. Implementa las
 * operaciones necesarias para guardar, consultar, editar y buscar comandas
 * dentro del sistema, así como la consulta de mesas disponibles y comandas
 * activas por mesa.
 *
 * @author regina, mariana e isaac.
 */
public class ComandaDAO implements IComandaDAO {

    private static ComandaDAO instanciaComanda;

    private ComandaDAO() {

    }

    /**
     * Obtiene la instancia única de ComandaDAO.
     *
     * @return Instancia única de ComandaDAO.
     */
    public static ComandaDAO getInstance() {
        if (instanciaComanda == null) {
            instanciaComanda = new ComandaDAO();
        }
        return instanciaComanda;
    }

    /**
     * Guarda una nueva comanda en la base de datos.
     *
     * @param comanda Entidad con la información de la comanda.
     * @return Comanda guardada.
     * @throws PersistenciaException Si ocurre un error durante el guardado o si
     * la comanda no cumple con los requisitos mínimos para persistirse.
     */
    @Override
    public Comanda guardar(Comanda comanda) throws PersistenciaException {
        if (comanda == null) {
            throw new PersistenciaException("La comanda no puede ser nula.");
        }

        EntityManager em = ConexionBD.crearConexion();

        try {
            em.getTransaction().begin();

            if (comanda.getMesa() == null || comanda.getMesa().getIdMesa() == null) {
                throw new PersistenciaException("La comanda debe tener una mesa válida.");
            }

            Mesa mesaRef = em.getReference(Mesa.class, comanda.getMesa().getIdMesa());
            comanda.setMesa(mesaRef);

            if (comanda.getCliente() != null && comanda.getCliente().getId() != null) {
                Cliente clienteRef = em.getReference(Cliente.class, comanda.getCliente().getId());
                comanda.setCliente(clienteRef);
            } else {
                comanda.setCliente(null);
            }

            if (comanda.getDetalles() != null) {
                for (DetalleComanda detalle : comanda.getDetalles()) {
                    detalle.setComanda(comanda);

                    if (detalle.getProducto() == null || detalle.getProducto().getIdProducto() == null) {
                        throw new PersistenciaException("Cada detalle debe tener un producto válido.");
                    }

                    Producto productoRef = em.getReference(
                            Producto.class,
                            detalle.getProducto().getIdProducto()
                    );
                    detalle.setProducto(productoRef);
                }
            }

            em.persist(comanda);
            em.getTransaction().commit();
            return comanda;

        } catch (PersistenciaException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("Error al guardar la comanda en la base de datos.", e);
        } finally {
            em.close();
        }
    }

    /**
     * Busca una comanda por su identificador único.
     *
     * @param id Identificador único de la comanda.
     * @return Comanda encontrada.
     * @throws PersistenciaException Si ocurre un error durante la búsqueda o si
     * el identificador es nulo.
     */
    @Override
    public Comanda buscarPorId(Long id) throws PersistenciaException {
        if (id == null) {
            throw new PersistenciaException("El id de la comanda no puede ser nulo.");
        }

        EntityManager em = ConexionBD.crearConexion();

        try {
            return em.find(Comanda.class, id);
        } catch (Exception e) {
            throw new PersistenciaException("Error al buscar la comanda por ID.", e);
        } finally {
            em.close();
        }
    }

    /**
     * Edita la información de una comanda existente en la base de datos.
     *
     * @param comanda Entidad con la información actualizada de la comanda.
     * @return Comanda actualizada.
     * @throws PersistenciaException Si ocurre un error durante la edición, si
     * la comanda es nula o si no cuenta con identificador.
     */
    @Override
    public Comanda editar(Comanda comanda) throws PersistenciaException {
        if (comanda == null) {
            throw new PersistenciaException("La comanda no puede ser nula.");
        }

        if (comanda.getIdComanda() == null) {
            throw new PersistenciaException("El ID de la comanda es obligatorio para editar.");
        }

        EntityManager em = ConexionBD.crearConexion();

        try {
            em.getTransaction().begin();

            if (comanda.getMesa() == null || comanda.getMesa().getIdMesa() == null) {
                throw new PersistenciaException("La comanda debe tener una mesa válida.");
            }

            Mesa mesaRef = em.getReference(Mesa.class, comanda.getMesa().getIdMesa());
            comanda.setMesa(mesaRef);

            if (comanda.getCliente() != null && comanda.getCliente().getId() != null) {
                Cliente clienteRef = em.getReference(Cliente.class, comanda.getCliente().getId());
                comanda.setCliente(clienteRef);
            } else {
                comanda.setCliente(null);
            }

            if (comanda.getDetalles() != null) {
                for (DetalleComanda detalle : comanda.getDetalles()) {
                    detalle.setComanda(comanda);

                    if (detalle.getProducto() == null || detalle.getProducto().getIdProducto() == null) {
                        throw new PersistenciaException("Cada detalle debe tener un producto válido.");
                    }

                    Producto productoRef = em.getReference(
                            Producto.class,
                            detalle.getProducto().getIdProducto()
                    );
                    detalle.setProducto(productoRef);
                }
            }

            Comanda comandaActualizada = em.merge(comanda);
            em.getTransaction().commit();
            return comandaActualizada;

        } catch (PersistenciaException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("Error al editar la comanda en la base de datos.", e);
        } finally {
            em.close();
        }
    }

    /**
     * Busca comandas que coincidan con el filtro proporcionado.
     *
     * El filtro se aplica sobre el folio de la comanda y sobre la información
     * del cliente asociado, incluyendo nombre, apellidos y nombre completo.
     *
     * @param filtro Texto de búsqueda para filtrar comandas.
     * @return Lista de comandas que coinciden con el filtro.
     * @throws PersistenciaException Si ocurre un error durante la búsqueda.
     */
    @Override
    public List<Comanda> buscarPorFiltros(String filtro) throws PersistenciaException {

        EntityManager em = ConexionBD.crearConexion();

        try {

            String filtroBusqueda = filtro == null ? "" : filtro.trim().toLowerCase();

            String jpql = "SELECT c FROM Comanda c "
                    + "LEFT JOIN c.cliente cl "
                    + "LEFT JOIN c.mesa m "
                    + "WHERE LOWER(c.folio) LIKE :filtro "
                    + "OR LOWER(cl.nombre) LIKE :filtro "
                    + "OR LOWER(cl.apellidoPaterno) LIKE :filtro "
                    + "OR LOWER(cl.apellidoMaterno) LIKE :filtro "
                    + "OR LOWER(CONCAT(CONCAT(CONCAT(CONCAT(cl.nombre, ' '), cl.apellidoPaterno), ' '), cl.apellidoMaterno)) LIKE :filtro "
                    + "ORDER BY c.fechaHoraCreacion DESC";

            TypedQuery<Comanda> query = em.createQuery(jpql, Comanda.class);
            query.setParameter("filtro", "%" + filtroBusqueda + "%");

            return query.getResultList();

        } catch (Exception e) {
            throw new PersistenciaException("Error al buscar comandas por filtro.", e);
        } finally {
            em.close();
        }
    }

    /**
     * Obtiene la lista de mesas disponibles dentro del sistema.
     *
     * @return Lista de mesas con estado DISPONIBLE.
     * @throws PersistenciaException Si ocurre un error durante la consulta.
     */
    @Override
    public List<Mesa> obtenerMesasDisponibles() throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();

        try {
            String jpql = "SELECT m FROM Mesa m "
                    + "WHERE m.estado = :estado "
                    + "ORDER BY m.numeroMesa ASC";

            TypedQuery<Mesa> query = em.createQuery(jpql, Mesa.class);
            query.setParameter("estado", EstadoMesa.DISPONIBLE);

            return query.getResultList();

        } catch (Exception e) {
            throw new PersistenciaException("Error al obtener las mesas disponibles.", e);
        } finally {
            em.close();
        }
    }

    /**
     * Busca una comanda activa asociada a una mesa específica.
     *
     * Se considera activa una comanda con estado ABIERTA.
     *
     * @param idMesa Identificador único de la mesa.
     * @return Comanda activa encontrada, o null si no existe una comanda activa
     * asociada a la mesa.
     * @throws PersistenciaException Si ocurre un error durante la búsqueda o si
     * el identificador de la mesa es nulo.
     */
    @Override
    public Comanda buscarComandaActivaPorMesa(Long idMesa) throws PersistenciaException {
        if (idMesa == null) {
            throw new PersistenciaException("El id de la mesa no puede ser nulo.");
        }

        EntityManager em = ConexionBD.crearConexion();

        try {
            String jpql = "SELECT c FROM Comanda c "
                    + "WHERE c.mesa.idMesa = :idMesa "
                    + "AND c.estado = :estado";

            TypedQuery<Comanda> query = em.createQuery(jpql, Comanda.class);
            query.setParameter("idMesa", idMesa);
            query.setParameter("estado", EstadoComanda.ABIERTA);

            List<Comanda> resultados = query.getResultList();

            if (resultados.isEmpty()) {
                return null;
            }

            return resultados.get(0);

        } catch (Exception e) {
            throw new PersistenciaException("Error al buscar la comanda activa de la mesa.", e);
        } finally {
            em.close();
        }
    }

    /**
     * Obtiene el último folio registrado para una fecha específica.
     *
     * @param fecha Fecha a consultar.
     * @return Último folio del día, o null si no existe ninguno.
     * @throws PersistenciaException Si ocurre un error durante la consulta.
     */
    @Override
    public String obtenerUltimoFolioDelDia(LocalDate fecha) throws PersistenciaException {
        if (fecha == null) {
            throw new PersistenciaException("La fecha no puede ser nula.");
        }

        EntityManager em = ConexionBD.crearConexion();

        try {
            String prefijo = "OB-" + fecha.format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "-";

            String jpql = "SELECT c.folio FROM Comanda c "
                    + "WHERE c.folio LIKE :prefijo "
                    + "ORDER BY c.folio DESC";

            TypedQuery<String> query = em.createQuery(jpql, String.class);
            query.setParameter("prefijo", prefijo + "%");
            query.setMaxResults(1);

            List<String> resultados = query.getResultList();

            if (resultados.isEmpty()) {
                return null;
            }

            return resultados.get(0);

        } catch (Exception e) {
            throw new PersistenciaException("Error al obtener el último folio del día.", e);
        } finally {
            em.close();
        }
    }
}
