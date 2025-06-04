package com.IntegradorIO.InventarioyStock.OrdenCompraArticulo;

import com.IntegradorIO.InventarioyStock.Articulo.Articulo;
import com.IntegradorIO.InventarioyStock.OrdenCompra.OrdenCompra;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
public class OrdenCompraArticulo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codigoOCA;

    private int cantidadOCA;
    private Timestamp fechaDesdeOCA;
    private Timestamp fechaHastaOCA;

    @ManyToOne
    @JoinColumn(name = "articulo_id")
    private Articulo articulo;

    @ManyToOne
    @JoinColumn(name = "orden_compra_id")
    private OrdenCompra ordenCompra;

}
