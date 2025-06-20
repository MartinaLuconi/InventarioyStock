package com.IntegradorIO.InventarioyStock.Venta;

import com.IntegradorIO.InventarioyStock.Articulo.Articulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface VentaRepository extends JpaRepository<Venta, Integer> {
    @Query("SELECT v FROM Venta v WHERE v.nroVenta = :id")
    Venta obtenerVenta(@Param("id") Integer id);

    @Query("SELECT v FROM Venta v")
    List<Venta> obtenerVentas();
}
