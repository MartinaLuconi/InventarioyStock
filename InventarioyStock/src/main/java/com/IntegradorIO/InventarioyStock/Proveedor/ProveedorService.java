package com.IntegradorIO.InventarioyStock.Proveedor;

import com.IntegradorIO.InventarioyStock.Articulo.Articulo;
import com.IntegradorIO.InventarioyStock.Articulo.ArticuloRepository;
import com.IntegradorIO.InventarioyStock.EstadoOrdenCompra.EstadoOrdencCompra;
import com.IntegradorIO.InventarioyStock.OrdenCompra.OrdenCompraRepository;
import com.IntegradorIO.InventarioyStock.Proveedor.dto.DTODetalleProveedorArticulo;
import com.IntegradorIO.InventarioyStock.Proveedor.dto.DTOModificarProveedor;
import com.IntegradorIO.InventarioyStock.Proveedor.dto.DTONuevoProveedor;
import com.IntegradorIO.InventarioyStock.Proveedor.dto.DTOTablaProveedor;
import com.IntegradorIO.InventarioyStock.ProveedorArticulo.ProveedorArticulo;
import com.IntegradorIO.InventarioyStock.ProveedorArticulo.ProveedorArticuloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import java.sql.Timestamp;

@Service
public class ProveedorService {

    @Autowired private ProveedorRepository proveedorRepository;
    @Autowired private ProveedorArticuloRepository proveedorArticuloRepository;
    @Autowired private ArticuloRepository articuloRepository;
    @Autowired private OrdenCompraRepository ordenCompraRepository;

    /** Listar solo proveedores activos */
    public List<DTOTablaProveedor> obtenerProveedores() {

        //return proveedorRepository.findByActivoTrue()
        return proveedorRepository.findAll()
                .stream()
                .map(DTOTablaProveedor::new)
                .collect(Collectors.toList());
    }

    public Optional<Proveedor> obtenerProveedor(Integer codigoProveedor) {
        return proveedorRepository.findById(codigoProveedor)
                .filter(Proveedor::isActivo);
    }

    public Proveedor guardarProveedor(DTONuevoProveedor dto) throws Exception{
        // 1) Convertir el dto en entidad Proveedor
        Proveedor entidad = new Proveedor();
        entidad.setNombreProveedor(dto.getNombreProveedor());
        entidad.setActivo(true);
        proveedorRepository.save(entidad);

        // 3) Si el dto trae asociaciones a artículos, crearlas ahora


            for (DTODetalleProveedorArticulo detalle : dto.getAsociaciones()) {
                List<ProveedorArticulo> proveedorArticuloList = new ArrayList<>();
                // 3.1) Buscar el Artículo correspondiente
                Articulo art = articuloRepository.obtenerArticulo(detalle.getCodigoArticulo());


                // 3.2) Crear y poblar la entidad ProveedorArticulo
                ProveedorArticulo pa = new ProveedorArticulo();
                pa.setArticulo(art); //relacion a articulo
                pa.setDemoraEntrega(detalle.getDemoraEntrega());
                pa.setCostoUnitario(detalle.getPrecioUnitProveedorArticulo());
                pa.setCostoPedido(detalle.getCostoPedido());
                pa.setEsPredeterminado(detalle.isEsPredeterminado());
                pa.setCostoPedido(detalle.getCostoPedido());
                pa.setFechaDesdePA(new Timestamp(System.currentTimeMillis()));
                pa.setFechaHastaPA(null);
                pa.setLoteOptimo(detalle.getLoteOptimo());
                pa.setCostoMantenimiento(detalle.getCostoMantenimiento());
                proveedorArticuloRepository.save(pa);
                proveedorArticuloList.add(pa);
                entidad.setProveedorArticulos(proveedorArticuloList);
            }

            proveedorRepository.save(entidad);


            // 4) Devolver el proveedor ya persistido (con su ID y sus asociaciones)
            return entidad;

    }


    public Proveedor modificarProveedor(Integer codigoProveedor, DTOModificarProveedor dto) {
        // 1) Recuperar proveedor
        Proveedor existente = proveedorRepository.findById(codigoProveedor)
                .filter(Proveedor::isActivo)
                .orElseThrow(() -> new IllegalArgumentException("Proveedor no encontrado o inactivo"));

        // 2) Actualizar datos básicos
        existente.setNombreProveedor(dto.getNombreProveedor());
        // (Si tuvieras más campos en Proveedor, asignarlos aquí)

        // 3) Cargar asociaciones actuales desde BD
        List<ProveedorArticulo> asociadasActuales = existente.getProveedorArticulos();
               // proveedorArticuloRepository.findByProveedorCodigoProveedor(codigoProveedor);

        // 4) Construir un mapa para “buscar por código de artículo” rápidamente
        //    Clave: codigoArticulo; Valor: ProveedorArticulo existente
        Map<Integer, ProveedorArticulo> mapaActuales = new HashMap<>();
        for (ProveedorArticulo pa : asociadasActuales) {
            mapaActuales.put(pa.getArticulo().getCodigoArticulo(), pa);
        }

        // 5) Procesar la lista que viene en el DTO
        //    Creamos una lista donde iremos guardando (o actualizando) las asociaciones finales
        List<ProveedorArticulo> listaFinal = new ArrayList<>();

        for (DTODetalleProveedorArticulo detalle : dto.getAsociaciones()) {
            Integer codigoArt = detalle.getCodigoArticulo();

            // 5a) Chequeo que el Artículo exista en BD
            Articulo art = articuloRepository.findById(codigoArt)
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Artículo no encontrado: " + codigoArt));

            if (mapaActuales.containsKey(codigoArt)) {
                // 5b) Ya existía esa asociación en BD: la “actualizo”
                ProveedorArticulo paExistente = mapaActuales.get(codigoArt);

                paExistente.setDemoraEntrega(detalle.getDemoraEntrega());
                paExistente.setCostoUnitario(detalle.getPrecioUnitProveedorArticulo());
                paExistente.setCostoPedido(detalle.getCostoPedido());
                paExistente.setEsPredeterminado(detalle.isEsPredeterminado());

                // La guardamos en la lista final para no borrarla posteriormente
                listaFinal.add(paExistente);

                // Marcamos que ya procesamos esta asociación, para que no sea eliminada
                mapaActuales.remove(codigoArt);

            } else {
                // 5c) No existía: creamos un nuevo ProveedorArticulo
                ProveedorArticulo nuevo = new ProveedorArticulo();
                nuevo.setArticulo(art);
                nuevo.setDemoraEntrega(detalle.getDemoraEntrega());
                nuevo.setCostoUnitario(detalle.getPrecioUnitProveedorArticulo());
                nuevo.setCostoPedido(detalle.getCostoPedido());
                nuevo.setCostoPedido(detalle.getCargoProveedorPedido());
                nuevo.setEsPredeterminado(detalle.isEsPredeterminado());

                listaFinal.add(nuevo);
            }
        }

        // 6) Aquellos que quedaron en mapaActuales eran asociaciones antiguas
        //    que ya no aparecen en el DTO: las debemos eliminar
        //    (o dar de baja lógica, según tu modelo). Aquí las borramos físicamente:
        if (!mapaActuales.isEmpty()) {
            proveedorArticuloRepository.deleteAll(mapaActuales.values());
        }

        // 7) Guardar todas las asociaciones “nuevas y actualizadas”
        //    Como las instancias pueden ser nuevas o modificadas, saveAll las persiste correctamente.
        proveedorArticuloRepository.saveAll(listaFinal);

        // 8) Asignar la lista final al proveedor (opcional si tu relación es bidireccional)
        existente.setProveedorArticulos(listaFinal);

        // 9) Finalmente salvar el proveedor con sus cambios
        return proveedorRepository.save(existente);
    }

    /**
     * Alta de proveedor con asociación mínima.
     * @throws IllegalArgumentException si no viene ninguna asociación.
     */
    public Proveedor guardarConAsociaciones(DTONuevoProveedor req) {
        if (req.getAsociaciones() == null || req.getAsociaciones().isEmpty()) {
            throw new IllegalArgumentException("Debe proporcionar al menos una asociación con artículo");
        }


        // 2) Crear asociaciones
        List<ProveedorArticulo> lista = new ArrayList<>();
        for (DTODetalleProveedorArticulo paReq : req.getAsociaciones()) {
            Articulo art = articuloRepository.findById(paReq.getCodigoArticulo())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Artículo no encontrado: " + paReq.getCodigoArticulo()));

            ProveedorArticulo pa = new ProveedorArticulo();
            pa.setArticulo(art);
            pa.setDemoraEntrega(paReq.getDemoraEntrega());
            pa.setCostoUnitario(paReq.getPrecioUnitProveedorArticulo());
            pa.setCostoPedido(paReq.getCostoPedido());
            pa.setCostoPedido(paReq.getCargoProveedorPedido());
            pa.setEsPredeterminado(paReq.isEsPredeterminado());

            lista.add(pa);
        }
        proveedorArticuloRepository.saveAll(lista);

        // 1) Crear Proveedor
        Proveedor p = new Proveedor();
        p.setNombreProveedor(req.getNombreProveedor());
        p.setActivo(true);
        p.setProveedorArticulos(lista);
        Proveedor guardado = proveedorRepository.save(p);


        return guardado;
    }

    /**
     * Baja lógica de proveedor con validaciones:
     * - No permitir si es predeterminado en un artículo.
     * - No permitir si hay órdenes PENDIENTE o CONFIRMADO.
     */
    public void bajaProveedor(Integer codigoProveedor) {
        Proveedor p = proveedorRepository.findById(codigoProveedor)
                .filter(Proveedor::isActivo)
                .orElseThrow(() -> new IllegalArgumentException("Proveedor no encontrado o ya inactivo"));

        // 1) Verificar predeterminado
        //ProveedorArticulo pred = proveedorArticuloRepository
       /* List<ProveedorArticulo> pred = proveedorArticuloRepository
                .findByProveedorCodigoProveedorAndEsPredeterminadoTrue(codigoProveedor);

        //if (pred != null) {
        if (!pred.isEmpty()) {
            throw new IllegalStateException(
                    "No se puede dar de baja: proveedor predeterminado en un artículo"
            );
        }*/

        // 2) Verificar órdenes activas (PENDIENTE o CONFIRMADO)
        boolean tieneOrdenesActivas = ordenCompraRepository
                .existsByProveedorCodigoProveedorAndEstadoOrdenCompraIn(
                        codigoProveedor,
                        Arrays.asList(EstadoOrdencCompra.PENDIENTE, EstadoOrdencCompra.CONFIRMADO)  // <- usa EstadoOrden
                );

        if (tieneOrdenesActivas) {
            throw new IllegalStateException(
                    "No se puede dar de baja: existen Órdenes de Compra pendientes o confirmadas"
            );
        }

        // 3) Marcar baja lógica
        p.setActivo(false);
        p.setFechaHoraBajaProveedor(new Timestamp(System.currentTimeMillis()));
        proveedorRepository.save(p);
    }

    /** Listar asociaciones Proveedor–Artículo por proveedor */
    public List<Articulo> obtenerArticulosPorProveedor(Integer codigoProveedor) {
        List<Articulo> articulosProveedorList = new ArrayList<>();
        Proveedor p = proveedorRepository.findById(codigoProveedor)
                .filter(Proveedor::isActivo)
                .orElseThrow(() -> new IllegalArgumentException("Proveedor no encontrado o inactivo"));

        List<ProveedorArticulo> paList = p.getProveedorArticulos();
        for (ProveedorArticulo pa : paList){
            Articulo articuloDelProveedor= pa.getArticulo();
            articulosProveedorList.add(articuloDelProveedor);
        }
        return  articulosProveedorList;
        //return proveedorArticuloRepository.findArticulosConArticuloPorProveedor(codigoProveedor);

    }
}
