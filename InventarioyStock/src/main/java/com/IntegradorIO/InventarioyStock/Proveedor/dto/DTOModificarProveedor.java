package com.IntegradorIO.InventarioyStock.Proveedor.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

public class DTOModificarProveedor {
    private String nombreProveedor;
    private List<DTODetalleProveedorArticulo> asociaciones;

}
