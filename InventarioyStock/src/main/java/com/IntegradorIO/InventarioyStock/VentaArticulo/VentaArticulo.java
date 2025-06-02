package com.IntegradorIO.InventarioyStock.VentaArticulo;

import com.IntegradorIO.InventarioyStock.Articulo.Articulo;
import com.IntegradorIO.InventarioyStock.Venta.Venta;
import jakarta.persistence.*;

@Entity
public class VentaArticulo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codVentaArticulo;
    private  int cantidadVA;


    @ManyToOne
    @JoinColumn(name = "articulo_id")
    private Articulo articulo;

    @ManyToOne
    @JoinColumn(name = "venta_id")
    private Venta venta;

    public VentaArticulo() { }

    public int getCodVentaArticulo() {
        return codVentaArticulo;
    }
    public void setCodVentaArticulo(int codVentaArticulo) {
        this.codVentaArticulo = codVentaArticulo;
    }

    public int getCantidadVA() {
        return cantidadVA;
    }
    public void setCantidadVA(int cantidadVA) {
        this.cantidadVA = cantidadVA;
    }


    public Articulo getArticulo() {
        return articulo;
    }
    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public Venta getVenta() {
        return venta;
    }
    public void setVenta(Venta venta) {
        this.venta = venta;
    }
}
