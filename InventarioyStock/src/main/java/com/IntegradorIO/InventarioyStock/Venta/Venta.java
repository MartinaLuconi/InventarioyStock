package com.IntegradorIO.InventarioyStock.Venta;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "venta")
@Getter
@Setter
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int nroVenta;
    private double DNIcliente;
    private Timestamp fechaHoraVenta;
    private int cantidadVenta;
    private float totalVenta;
}
