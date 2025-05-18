package com.IntegradorIO.InventarioyStock.InventarioArticulo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "inventario_articulo")
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
