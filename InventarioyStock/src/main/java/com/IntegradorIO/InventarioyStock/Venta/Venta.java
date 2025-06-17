package com.IntegradorIO.InventarioyStock.Venta;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import com.IntegradorIO.InventarioyStock.VentaArticulo.VentaArticulo;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int nroVenta;
    private double DNIcliente;
    private String NombreCliente;
    private String ApellidoCliente;
    private LocalDateTime FechaVenta;
    private int cantidadVenta;

    @OneToMany(
            mappedBy   = "venta",
            cascade    = CascadeType.ALL,
            orphanRemoval = true,
            fetch      = FetchType.LAZY
    )
    private List<VentaArticulo> articulos;

    public Venta() { }
}

