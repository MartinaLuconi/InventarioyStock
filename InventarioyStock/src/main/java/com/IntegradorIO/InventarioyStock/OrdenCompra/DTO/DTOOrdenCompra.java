package com.IntegradorIO.InventarioyStock.OrdenCompra.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class DTOOrdenCompra {
    private int nroOrden;
    private float precioTotal; //podría no estar tranquilamente
    private  String nombreOC;
    private List<DTODetalleOC> detallesOC;
    //falta proveedor y tamaño del lote --> datos sugeridos


}
