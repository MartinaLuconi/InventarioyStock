package com.IntegradorIO.InventarioyStock.Venta.dto;
import java.time.LocalDateTime;
import java.util.List;


public class VentaRequest {

    private String nombreCliente;
    private String apellidoCliente;
    private Long dniCliente;
    private List<VentaArticuloRequest> articulos;

    public VentaRequest() {}

}
