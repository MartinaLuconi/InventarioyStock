package com.IntegradorIO.InventarioyStock.Venta;
import com.IntegradorIO.InventarioyStock.VentaArticulo.VentaArticulo;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.List;


@Entity
@Getter
@Setter
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int nroVenta;
    private Long dniCliente;
    private String nombreCliente;
    private String apellidoCliente;
    private Timestamp fechaVenta;
    private int cantidadVenta;
    private float totalVenta;


    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "venta_id")
    private List<VentaArticulo> articulos;


    public Venta() { }
}


