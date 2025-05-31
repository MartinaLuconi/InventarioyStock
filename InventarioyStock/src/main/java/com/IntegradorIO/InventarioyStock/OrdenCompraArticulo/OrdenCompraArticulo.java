package com.IntegradorIO.InventarioyStock.OrdenCompraArticulo;

import com.IntegradorIO.InventarioyStock.Articulo.Articulo;
import com.IntegradorIO.InventarioyStock.OrdenCompra.OrdenCompra;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class OrdenCompraArticulo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codigoOCA;

    private int cantidadOCA;

    @ManyToOne
    @JoinColumn(name = "articulo_id")
    private Articulo articulo;

    @ManyToOne
    @JoinColumn(name = "orden_compra_id")
    private OrdenCompra ordenCompra;

}
