package com.IntegradorIO.InventarioyStock.Proveedor;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "proveedor")
@Getter
@Setter
public class Proveedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codigoProveedor;
    private Timestamp fechaHoraBajaProveedor;
    private String nombreProveedor;
}
