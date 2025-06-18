package com.IntegradorIO.InventarioyStock.VentaArticulo;

import com.IntegradorIO.InventarioyStock.Articulo.Articulo;
import com.IntegradorIO.InventarioyStock.Venta.Venta;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class VentaArticulo {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codVentaArticulo;
    private int cantidadVA;

    @ManyToOne
    @JoinColumn(name = "articulo_id")
    private Articulo articulo;


    @ManyToOne
    @JoinColumn(name = "venta_id")
    private Venta venta;


    public VentaArticulo() {}
}
