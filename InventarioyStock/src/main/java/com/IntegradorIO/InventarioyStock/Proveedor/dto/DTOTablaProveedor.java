package com.IntegradorIO.InventarioyStock.Proveedor.dto;

import com.IntegradorIO.InventarioyStock.Proveedor.Proveedor;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class DTOTablaProveedor {
    private int codigoProveedor;
    private String nombreProveedor;
    private boolean activo;

    public DTOTablaProveedor(Proveedor proveedor) {
        this.codigoProveedor = proveedor.getCodigoProveedor();
        this.nombreProveedor = proveedor.getNombreProveedor();
        this.activo = proveedor.isActivo();
    }
}
