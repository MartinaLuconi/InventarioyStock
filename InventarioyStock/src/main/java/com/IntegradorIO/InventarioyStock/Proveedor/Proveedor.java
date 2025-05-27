package com.IntegradorIO.InventarioyStock.Proveedor;

import com.IntegradorIO.InventarioyStock.ProveedorArticulo.ProveedorArticulo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@Setter
public class Proveedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codigoProveedor;
    private Timestamp fechaHoraBajaProveedor;
    private String nombreProveedor;

    @OneToMany(mappedBy = "proveedor")
    private List<ProveedorArticulo> proveedorArticulos;
}
