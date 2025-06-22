package com.IntegradorIO.InventarioyStock.OrdenCompraArticulo;

import com.IntegradorIO.InventarioyStock.Articulo.Articulo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdenCompraArticuloRepository extends JpaRepository<OrdenCompraArticulo, Long> {

}
