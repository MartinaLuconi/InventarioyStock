package com.IntegradorIO.InventarioyStock.VentaArticulo;

import jakarta.persistence.*;

@Entity
@Table(name = "venta_articulo")
public class VentaArticulo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codVentaArticulo;
    private  int cantidadVA;

    /*  Relaciones
    @ManyToOne
    @JoinColumn(name = "articulo_id")
    private Articulo articulo;

    @ManyToOne
    @JoinColumn(name = "venta_id")
    private Venta venta;

     */
}
