package com.IntegradorIO.InventarioyStock.Proveedor;

import com.IntegradorIO.InventarioyStock.ProveedorArticulo.ProveedorArticulo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Entity
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigoProveedor;

    private String nombreProveedor;

    @OneToMany
    private List<ProveedorArticulo> proveedorArticulos;


    private Timestamp fechaHoraBajaProveedor;

    /** Flag para baja lógica */
    private boolean activo = true;

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


    public List<ProveedorArticulo> getProveedorArticulos() {
        return proveedorArticulos;
    }

    public void setProveedorArticulos(List<ProveedorArticulo> proveedorArticulos) {
        this.proveedorArticulos = proveedorArticulos;
    }
}
