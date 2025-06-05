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
    private List<DTODetalleProveedorArticulo> articulos;


    public DTOTablaProveedor(Proveedor proveedor) {
        this.codigoProveedor = proveedor.getCodigoProveedor();
        this.nombreProveedor = proveedor.getNombreProveedor();
        this.activo = proveedor.isActivo();
        this.articulos = proveedor.getProveedorArticulos().stream().map(pa -> {
            DTODetalleProveedorArticulo dto = new DTODetalleProveedorArticulo();
            dto.setCodigoArticulo(pa.getArticulo().getCodigoArticulo());
            dto.setDemoraEntrega(pa.getDemoraEntrega());
            dto.setPrecioUnitProveedorArticulo(pa.getPrecioUnitProveedorArticulo());
            dto.setCostoPedido(pa.getCostoPedido());
            dto.setCargoProveedorPedido(pa.getCargoProveedorPedido());
            dto.setEsPredeterminado(pa.isEsPredeterminado());
            return dto;
        }).collect(Collectors.toList());
    }

}

