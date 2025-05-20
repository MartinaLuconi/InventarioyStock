package com.IntegradorIO.InventarioyStock.OrdenCompra;

import com.IntegradorIO.InventarioyStock.EstadoOrdenCompra.EstadoOrdenCompra;
import com.IntegradorIO.InventarioyStock.Proveedor.Proveedor;
import jakarta.persistence.*;
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

    @ManyToOne
    @JoinColumn(name = "proveedor_id")
    private Proveedor proveedor;

    @ManyToOne
    @JoinColumn(name = "estado_orden_compra_id")
    private EstadoOrdenCompra estadoOrdenCompra;

}
