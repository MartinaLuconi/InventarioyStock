package com.IntegradorIO.InventarioyStock.EstadoOrdenCompra;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
public class EstadoOrdenCompra {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codigoEstadoOrdenCompra;
    private Timestamp fechaHoraBajaEstadoOC;
    private String nombreEstadoOC;

}
