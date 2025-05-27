package com.IntegradorIO.InventarioyStock.Proveedor;

import com.IntegradorIO.InventarioyStock.ProveedorArticulo.ProveedorArticulo;
import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigoProveedor;

    private String nombreProveedor;
    private Timestamp fechaHoraBajaProveedor;

    /** Flag para baja lógica */
    private boolean activo = true;

    @OneToMany(
            mappedBy = "proveedor",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<ProveedorArticulo> asociaciones;

    public Proveedor() {}

    public Integer getCodigoProveedor() {
        return codigoProveedor;
    }
    public void setCodigoProveedor(Integer codigoProveedor) {
        this.codigoProveedor = codigoProveedor;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }
    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public Timestamp getFechaHoraBajaProveedor() {
        return fechaHoraBajaProveedor;
    }
    public void setFechaHoraBajaProveedor(Timestamp fechaHoraBajaProveedor) {
        this.fechaHoraBajaProveedor = fechaHoraBajaProveedor;
    }

    public boolean isActivo() {
        return activo;
    }
    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public List<ProveedorArticulo> getAsociaciones() {
        return asociaciones;
    }
    public void setAsociaciones(List<ProveedorArticulo> asociaciones) {
        this.asociaciones = asociaciones;
    }
}
