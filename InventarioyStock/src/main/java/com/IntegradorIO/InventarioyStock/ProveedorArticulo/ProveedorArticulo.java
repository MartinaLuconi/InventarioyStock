package com.IntegradorIO.InventarioyStock.ProveedorArticulo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
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
}
