package com.IntegradorIO.InventarioyStock.ProveedorArticulo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProveedorArticuloRepository
        extends JpaRepository<ProveedorArticulo, Integer> {

    // Opcional: buscar todas las asociaciones de un proveedor
    List<ProveedorArticulo> findByProveedorCodigoProveedor(Integer codigoProveedor);

    // Opcional: buscar predeterminado de un proveedor
    ProveedorArticulo findByProveedorCodigoProveedorAndEsPredeterminadoTrue(Integer codigoProveedor);

    // Opcional: buscar todas las asociaciones de un artículo
    List<ProveedorArticulo> findByArticulo_CodigoArticulo(Integer codigoArticulo);


}

