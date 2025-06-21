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
    private Integer codigoProveedor;

    private String nombreProveedor;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "proveedor_id")  // ← así le decimos que use esta FK
    private List<ProveedorArticulo> proveedorArticulos;


    private Timestamp fechaHoraBajaProveedor;

    /** Flag para baja lógica */
    private boolean activo = true;

    public Proveedor() {}


}
