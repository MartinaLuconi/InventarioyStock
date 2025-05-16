package com.IntegradorIO.InventarioyStock.InventarioArticulo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

public class InventarioArticulo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int nroInventarioArticulo;
    private int contadorInventarioArticulo;
    private int inventarioMaximo;
    private  float precioInventarioArticulo;
    private int stockSeguridad;
}
