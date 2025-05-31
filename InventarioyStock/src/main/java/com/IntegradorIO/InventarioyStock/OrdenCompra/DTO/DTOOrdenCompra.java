package com.IntegradorIO.InventarioyStock.OrdenCompra.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class DTOOrdenCompra {
    private int nroOrden;
    private int cantidad; //tamaño del lote
    private  String nombreOC;
    //falta proveedor y tamaño del lote --> datos sugeridos
    //tmb faltaría algo que referencie al artículo, que pido
}
