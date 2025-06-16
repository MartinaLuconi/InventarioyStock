package com.IntegradorIO.InventarioyStock.Proveedor.dto;

import com.IntegradorIO.InventarioyStock.Proveedor.Proveedor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class DTOTablaProveedor {

    private int codigoProveedor;
    private String nombreProveedor;
    private boolean activo;
    private List<DTOArticuloTablaProveedor> articulos;

    public DTOTablaProveedor() {}

    public DTOTablaProveedor(Proveedor proveedor) {
        this.codigoProveedor = proveedor.getCodigoProveedor();
        this.nombreProveedor = proveedor.getNombreProveedor();
        this.activo = proveedor.isActivo();

        this.articulos = proveedor.getProveedorArticulos().stream().map(pa -> {
            DTOArticuloTablaProveedor dto = new DTOArticuloTablaProveedor();
            dto.setCodigoArticulo(pa.getArticulo().getCodigoArticulo());
            dto.setNombreArticulo(pa.getArticulo().getNombreArticulo());
            return dto;
        }).collect(Collectors.toList());
    }
}
