package com.IntegradorIO.InventarioyStock.OrdenCompra;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "orden_compra")
@Getter
@Setter
public class OrdenCompra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int numeroOrdenCompra;
    private String nombreOrdenCompra;
    private int cantidadOrdenCompra;
}
