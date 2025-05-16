package com.IntegradorIO.InventarioyStock.Proveedor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.sql.Timestamp;

@Entity
public class Proveedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codigoProveedor;
    private Timestamp fechaHoraBajaProveedor;
    private String nombreProveedor;
}
