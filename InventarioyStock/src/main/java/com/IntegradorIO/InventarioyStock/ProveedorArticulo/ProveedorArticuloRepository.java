package com.IntegradorIO.InventarioyStock.ProveedorArticulo;

import com.IntegradorIO.InventarioyStock.Articulo.Articulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface ProveedorArticuloRepository
        extends JpaRepository<ProveedorArticulo, Integer> {
    List<ProveedorArticulo> findByArticulo(Articulo articulo);
    // Opcional: buscar todas las asociaciones de un proveedor
    //List<ProveedorArticulo> findByProveedorCodigoProveedor(Integer codigoProveedor);

    // Opcional: buscar predeterminado de un proveedor
    //ProveedorArticulo findByProveedorCodigoProveedorAndEsPredeterminadoTrue(Integer codigoProveedor);
    //List<ProveedorArticulo> findByProveedorCodigoProveedorAndEsPredeterminadoTrue(Integer codigoProveedor);

    // Opcional: buscar todas las asociaciones de un artículo
    //List<ProveedorArticulo> findByArticulo_CodigoArticulo(Integer codigoArticulo);

    // NUEVO: Buscar asociaciones de un proveedor y traer el artículo con JOIN FETCH
    //@Query("SELECT pa FROM ProveedorArticulo pa JOIN FETCH pa.articulo WHERE pa.proveedor.codigoProveedor = :codigoProveedor")
   // List<ProveedorArticulo> findArticulosConArticuloPorProveedor(@Param("codigoProveedor") Integer codigoProveedor);
}




