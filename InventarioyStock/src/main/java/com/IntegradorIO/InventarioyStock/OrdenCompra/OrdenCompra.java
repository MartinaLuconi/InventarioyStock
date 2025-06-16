package com.IntegradorIO.InventarioyStock.OrdenCompra;

import com.IntegradorIO.InventarioyStock.EstadoOrdenCompra.EstadoOrdenCompra;
import com.IntegradorIO.InventarioyStock.EstadoOrdenCompra.EstadoOrdencCompra;
import com.IntegradorIO.InventarioyStock.OrdenCompraArticulo.OrdenCompraArticulo;
import com.IntegradorIO.InventarioyStock.Proveedor.Proveedor;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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

    @OneToMany(cascade = CascadeType.ALL)
    private List<OrdenCompraArticulo> listaOrdenCompraArticulo;


}
