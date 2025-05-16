package com.IntegradorIO.InventarioyStock.OrdenCompra;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class OrdenCompra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int numeroOrdenCompra;
    private String nombreOrdenCompra;
    private int cantidadOrdenCompra;
}
