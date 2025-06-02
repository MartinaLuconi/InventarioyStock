package com.IntegradorIO.InventarioyStock.Proveedor.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class DTONuevoProveedor {

    private String nombreProveedor;
    private List<DTODetalleProveedorArticulo> asociaciones;
    private boolean activo;

    private Integer codigoArticulo;
    private int demoraEntrega;
    private float precioUnitProveedorArticulo;
    private float costoPedido;
    private int cargoProveedorPedido;
    private boolean esPredeterminado;
}
