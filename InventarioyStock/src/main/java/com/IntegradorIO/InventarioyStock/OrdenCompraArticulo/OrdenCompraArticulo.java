package com.IntegradorIO.InventarioyStock.OrdenCompraArticulo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "orden_compra_articulo")
@Getter
@Setter
public class OrdenCompraArticulo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codigoOCA;
    private int cantidadOCA;

    /*  Relaciones
    @ManyToOne
    @JoinColumn(name = "articulo_id")
    private Articulo articulo;

    @ManyToOne
    @JoinColumn(name = "ordencompra_id")
    private OrdenCompra ordencompra;
*/
}
