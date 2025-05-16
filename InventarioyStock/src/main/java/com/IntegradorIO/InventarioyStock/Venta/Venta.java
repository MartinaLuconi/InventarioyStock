package com.IntegradorIO.InventarioyStock.Venta;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.sql.Timestamp;

@Entity
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int nroVenta;
    private double DNIcliente;
    private Timestamp fechaHoraVenta;
    private int cantidadVenta;
    private float totalVenta;
}
