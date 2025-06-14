package com.IntegradorIO.InventarioyStock.ProveedorArticulo;

import com.IntegradorIO.InventarioyStock.Articulo.Articulo;
import com.IntegradorIO.InventarioyStock.Proveedor.Proveedor;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.engine.internal.Cascade;

import java.sql.Timestamp;

@Entity
@Getter
@Setter


@jakarta.persistence.EntityListeners({
        com.IntegradorIO.InventarioyStock.EstrategiaDeRevisionP.ProveedorArticuloListenerP.class,
        com.IntegradorIO.InventarioyStock.EstrategiaDeRevisión.ProveedorArticuloListener.class
})

public class ProveedorArticulo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codProveedorArticulo;
    private float CGIProveedorArticulo;
    private float costoPedido;
    private int demoraEntrega;
    private boolean esPredeterminado;
    private float precioUnitProveedorArticulo;
    private int cargoProveedorPedido;
    private double costoMantenimiento;
    private int loteOptimo;
    private int inventarioMaximo;
    private float nivelDeServicio;
    private float costoUnitario;
    private Timestamp fechaDesdePA;
    private Timestamp fechaHastaPA;
    private int eoq;
    private int periodoRevision;

    @ManyToOne
    @JoinColumn(name = "articulo_id")
    @JsonBackReference
    private Articulo articulo;

    /*@ManyToOne
    @JoinColumn(name = "proveedor_id")
    @JsonBackReference
    private Proveedor proveedor;*/


}
