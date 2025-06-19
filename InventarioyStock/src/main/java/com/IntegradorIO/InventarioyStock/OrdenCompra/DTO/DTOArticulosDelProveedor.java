package com.IntegradorIO.InventarioyStock.OrdenCompra.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class DTOArticulosDelProveedor {
    private  int codArticulo;
    private String nombreArticulo;
}
