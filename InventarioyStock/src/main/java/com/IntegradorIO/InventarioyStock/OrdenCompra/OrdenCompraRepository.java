package com.IntegradorIO.InventarioyStock.OrdenCompra;

import com.IntegradorIO.InventarioyStock.Articulo.Articulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.IntegradorIO.InventarioyStock.EstadoOrdenCompra.EstadoOrdencCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdenCompraRepository extends JpaRepository<OrdenCompra, Integer> {
    @Query("SELECT o FROM OrdenCompra o")
    List<OrdenCompra> obtenerOrdenesCompra();

    @Query("SELECT o FROM OrdenCompra o WHERE o.numeroOrdenCompra = :id")
    OrdenCompra obtenerOC(@Param("id") Integer id);

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

