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
    private boolean superaPuntoPedido;
    //private  float precioUnitarioArticulo;

}
