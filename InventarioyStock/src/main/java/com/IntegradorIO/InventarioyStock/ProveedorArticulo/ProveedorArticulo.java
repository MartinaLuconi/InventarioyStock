package com.IntegradorIO.InventarioyStock.ProveedorArticulo;

import com.IntegradorIO.InventarioyStock.Articulo.Articulo;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

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


}
