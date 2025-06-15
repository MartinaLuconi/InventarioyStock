package com.IntegradorIO.InventarioyStock.Proveedor;

import com.IntegradorIO.InventarioyStock.OrdenCompra.OrdenCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {
    @Query("SELECT p FROM Proveedor p WHERE p.codigoProveedor = :id")
    Proveedor obtenerProveedor(@Param("id") Integer id);
}
