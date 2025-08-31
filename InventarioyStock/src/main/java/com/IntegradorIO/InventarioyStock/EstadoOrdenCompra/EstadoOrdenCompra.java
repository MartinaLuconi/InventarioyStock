package com.IntegradorIO.InventarioyStock.EstadoOrdenCompra;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "estado_orden_compra")
public class EstadoOrdenCompra {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codigoEstadoOrdenCompra;
    private Timestamp fechaHoraBajaEstadoOC;
    private String nombreEstadoOC;

}
