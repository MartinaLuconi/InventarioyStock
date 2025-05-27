package com.IntegradorIO.InventarioyStock.Proveedor.dto;

import java.util.List;

public class ProveedorRequest {
    private String nombreProveedor;
    private List<ProveedorArticuloRequest> asociaciones;

    public ProveedorRequest() { }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public List<ProveedorArticuloRequest> getAsociaciones() {
        return asociaciones;
    }

    public void setAsociaciones(List<ProveedorArticuloRequest> asociaciones) {
        this.asociaciones = asociaciones;
    }
}
