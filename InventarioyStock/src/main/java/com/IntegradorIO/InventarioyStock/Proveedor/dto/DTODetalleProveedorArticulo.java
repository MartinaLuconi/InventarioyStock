package com.IntegradorIO.InventarioyStock.Proveedor.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DTODetalleProveedorArticulo {
    private Integer codigoArticulo;
    private int demoraEntrega;
    private float precioUnitProveedorArticulo;
    private float costoPedido;
    private  int loteOptimo;
    private int costoMantenimiento;
    private boolean esPredeterminado;
    private float nivelDeServicio;
}
