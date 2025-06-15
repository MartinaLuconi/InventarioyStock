package com.IntegradorIO.InventarioyStock.OrdenCompra.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class DTODetalleOC {
    private int cantidadArticulo; //tamaño del lote
    private String nombreArticulo;
    private  int codArticulo;
    private int idProveedor;  // esto lo agregue para asignarle el valor del proveedor determinado al realizar la venta , el dato lo saco de venta service

    //private  float precioUnitarioArticulo;
}