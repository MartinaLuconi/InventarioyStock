package com.IntegradorIO.InventarioyStock.Proveedor;

import com.IntegradorIO.InventarioyStock.Articulo.Articulo;
import com.IntegradorIO.InventarioyStock.Articulo.ArticuloRepository;
import com.IntegradorIO.InventarioyStock.EstadoOrdenCompra.EstadoOrdencCompra;
import com.IntegradorIO.InventarioyStock.OrdenCompra.OrdenCompraRepository;
import com.IntegradorIO.InventarioyStock.ProveedorArticulo.ProveedorArticulo;
import com.IntegradorIO.InventarioyStock.ProveedorArticulo.ProveedorArticuloRepository;
import com.IntegradorIO.InventarioyStock.Proveedor.dto.ProveedorArticuloRequest;
import com.IntegradorIO.InventarioyStock.Proveedor.dto.ProveedorRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ProveedorService {

    @Autowired private ProveedorRepository proveedorRepository;
    @Autowired private ProveedorArticuloRepository proveedorArticuloRepository;
    @Autowired private ArticuloRepository articuloRepository;
    @Autowired private OrdenCompraRepository ordenCompraRepository;

    /** Listar solo proveedores activos */
    public List<Proveedor> obtenerProveedores() {
        return proveedorRepository.findByActivoTrue();
    }

    public Optional<Proveedor> obtenerProveedor(Integer codigoProveedor) {
        return proveedorRepository.findById(codigoProveedor)
                .filter(Proveedor::isActivo);
    }

    public Proveedor guardarProveedor(Proveedor proveedor) {
        proveedor.setActivo(true);
        return proveedorRepository.save(proveedor);
    }

    public Proveedor modificarProveedor(Integer codigoProveedor, Proveedor proveedorModificado) {
        return proveedorRepository.findById(codigoProveedor)
                .filter(Proveedor::isActivo)
                .map(p -> {
                    proveedorModificado.setCodigoProveedor(codigoProveedor);
                    proveedorModificado.setActivo(true);
                    return proveedorRepository.save(proveedorModificado);
                }).orElse(null);
    }

    /**
     * Alta de proveedor con asociación mínima.
     * @throws IllegalArgumentException si no viene ninguna asociación.
     */
    public Proveedor guardarConAsociaciones(ProveedorRequest req) {
        if (req.getAsociaciones() == null || req.getAsociaciones().isEmpty()) {
            throw new IllegalArgumentException("Debe proporcionar al menos una asociación con artículo");
        }

        // 1) Crear Proveedor
        Proveedor p = new Proveedor();
        p.setNombreProveedor(req.getNombreProveedor());
        p.setActivo(true);
        Proveedor guardado = proveedorRepository.save(p);

        // 2) Crear asociaciones
        List<ProveedorArticulo> lista = new ArrayList<>();
        for (ProveedorArticuloRequest paReq : req.getAsociaciones()) {
            Articulo art = articuloRepository.findById(paReq.getCodigoArticulo())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Artículo no encontrado: " + paReq.getCodigoArticulo()));

            ProveedorArticulo pa = new ProveedorArticulo();
            pa.setProveedor(guardado);
            pa.setArticulo(art);
            pa.setDemoraEntrega(paReq.getDemoraEntrega());
            pa.setPrecioUnitProveedorArticulo(paReq.getPrecioUnitProveedorArticulo());
            pa.setCostoPedido(paReq.getCostoPedido());
            pa.setCargoProveedorPedido(paReq.getCargoProveedorPedido());
            pa.setEsPredeterminado(paReq.isEsPredeterminado());

            lista.add(pa);
        }
        proveedorArticuloRepository.saveAll(lista);

        guardado.setProveedorArticulos(lista);
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
        ProveedorArticulo pred = proveedorArticuloRepository
                .findByProveedorCodigoProveedorAndEsPredeterminadoTrue(codigoProveedor);
        if (pred != null) {
            throw new IllegalStateException(
                    "No se puede dar de baja: proveedor predeterminado en un artículo"
            );
        }

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
    public List<ProveedorArticulo> obtenerArticulosPorProveedor(Integer codigoProveedor) {
        Proveedor p = proveedorRepository.findById(codigoProveedor)
                .filter(Proveedor::isActivo)
                .orElseThrow(() -> new IllegalArgumentException("Proveedor no encontrado o inactivo"));

        return proveedorArticuloRepository.findByProveedorCodigoProveedor(codigoProveedor);
    }
}
