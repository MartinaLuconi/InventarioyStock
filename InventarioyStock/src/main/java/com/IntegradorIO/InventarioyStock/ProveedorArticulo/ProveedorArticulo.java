package com.IntegradorIO.InventarioyStock.ProveedorArticulo;

import com.IntegradorIO.InventarioyStock.Articulo.Articulo;
import com.IntegradorIO.InventarioyStock.Proveedor.Proveedor;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ProveedorArticulo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codProveedorArticulo;
    private float CGIProveedorArticulo;
    private float costoPedido;
    private int demoraEntrega;
    private boolean esPredeterminado;
    private float precioUnitProveedorArticulo;
    private int cargoProveedorPedido;

    @ManyToOne
    @JoinColumn(name = "articulo_id")
    private Articulo articulo;

    @ManyToOne
    @JoinColumn(name = "proveedor_id")
    private Proveedor proveedor;



}
