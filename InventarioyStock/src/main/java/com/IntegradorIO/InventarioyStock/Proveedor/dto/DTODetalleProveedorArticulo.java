package com.IntegradorIO.InventarioyStock.Proveedor.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class DTODetalleProveedorArticulo {
    private Integer codigoArticulo;
    private String nombreArticulo;
    private int demoraEntrega;
    private float precioUnitProveedorArticulo;
    private float costoPedido;
    private  int loteOptimo;
    private int costoMantenimiento;
    private boolean esPredeterminado;
    private float nivelDeServicio;
    private int periodoRevision;
    private int inventarioMaximo;
}
