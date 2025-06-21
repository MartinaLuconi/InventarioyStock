package com.IntegradorIO.InventarioyStock.Articulo.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class DTOProveedoresPorArticulo {
    private String nombreProveedor;
    private Integer codProveedor;
    private boolean isPredeterminado;
    private int codigoArticulo;
    private int codProveedorArticulo;
}
