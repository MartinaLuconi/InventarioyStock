package com.IntegradorIO.InventarioyStock.ProveedorArticulo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProveedorArticuloRepository extends JpaRepository<ProveedorArticulo, Integer> {

    //busca relaciones proveedor articulo
    List<ProveedorArticulo> findByArticulo_CodigoArticulo(int codigoArticulo);
}
