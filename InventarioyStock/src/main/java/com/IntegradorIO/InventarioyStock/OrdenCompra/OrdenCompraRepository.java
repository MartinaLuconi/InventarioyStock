package com.IntegradorIO.InventarioyStock.OrdenCompra;

import com.IntegradorIO.InventarioyStock.EstadoOrdenCompra.EstadoOrdencCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdenCompraRepository extends JpaRepository<OrdenCompra, Integer> {

    /**
     * Devuelve true si existe al menos una orden de compra
     * para el proveedor dado en estado PENDIENTE o EN_CURSO.
     *
     * @param codigoProveedor el ID del proveedor
     * @param estados lista de estados a comprobar (por ejemplo PENDIENTE y EN_CURSO)
     */
    boolean existsByProveedorCodigoProveedorAndEstadoOrdenCompraIn(
            Integer codigoProveedor,
            List<EstadoOrdencCompra> estados
    );
}

