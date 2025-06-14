package com.IntegradorIO.InventarioyStock.OrdenCompra;

import com.IntegradorIO.InventarioyStock.Articulo.Articulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdenCompraRepository extends JpaRepository<OrdenCompra, Integer> {
    @Query("SELECT o FROM OrdenCompra o")
    List<OrdenCompra> obtenerOrdenesCompra();

    @Query("SELECT o FROM OrdenCompra o WHERE o.numeroOrdenCompra = :id")
    OrdenCompra obtenerOC(@Param("id") Integer id);
}
