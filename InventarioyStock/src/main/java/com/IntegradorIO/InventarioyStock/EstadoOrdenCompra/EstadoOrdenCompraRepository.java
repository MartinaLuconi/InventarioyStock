package com.IntegradorIO.InventarioyStock.EstadoOrdenCompra;

import com.IntegradorIO.InventarioyStock.Articulo.Articulo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EstadoOrdenCompraRepository extends JpaRepository<EstadoOrdenCompra, Long> {
    boolean existsByArticuloAndNombreEstadoIn(Articulo articulo, List<EstadoOrdencCompra> nombreEstado);}
