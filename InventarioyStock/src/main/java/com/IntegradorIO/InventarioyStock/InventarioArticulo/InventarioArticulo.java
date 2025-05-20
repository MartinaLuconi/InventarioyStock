package com.IntegradorIO.InventarioyStock.InventarioArticulo;

import com.IntegradorIO.InventarioyStock.Articulo.Articulo;
import com.IntegradorIO.InventarioyStock.Inventario.Inventario;
import jakarta.persistence.*;
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

    @ManyToOne
    @JoinColumn(name = "articulo_id")
    private Articulo articulo;

    @ManyToOne
    @JoinColumn(name = "inventario_id")
    private Inventario inventario;
}
