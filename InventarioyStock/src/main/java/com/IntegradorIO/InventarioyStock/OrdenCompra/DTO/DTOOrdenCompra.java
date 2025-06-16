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
    //private float precioTotal; //podría no estar tranquilamente
    private  String nombreOC;
    private List<DTODetalleOC> detallesOC;
    private int codProveedor;
    //falta tamaño del lote --> datos sugeridos
    public int getCodProveedor() {
        return codProveedor;
    }

    public void setCodProveedor(int codProveedor) {
        this.codProveedor = codProveedor;
    }


}
