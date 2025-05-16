package com.IntegradorIO.InventarioyStock.VentaArticulo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class VentaArticulo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codVentaArticulo;
    private  int cantidadVA;

}
