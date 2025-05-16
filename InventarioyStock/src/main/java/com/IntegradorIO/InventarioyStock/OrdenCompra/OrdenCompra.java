package com.IntegradorIO.InventarioyStock.OrdenCompra;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class OrdenCompra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int numeroOrdenCompra;
    private String nombreOrdenCompra;
    private int cantidadOrdenCompra;
}
