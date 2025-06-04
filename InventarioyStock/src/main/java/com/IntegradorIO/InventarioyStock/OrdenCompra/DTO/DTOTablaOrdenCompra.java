package com.IntegradorIO.InventarioyStock.OrdenCompra.DTO;

import com.IntegradorIO.InventarioyStock.EstadoOrdenCompra.EstadoOrdencCompra;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Data
@Getter
@Setter
public class DTOTablaOrdenCompra {
    private  int nroOrdenCompra;
    private String nombreOC;
    private Timestamp fechaOrden; //agregar FD Y FH DESDE INTERMEDIA
    private String nombreProveedor;
    private EstadoOrdencCompra estadoOC;
    //se podría agregar el total tmb

}
