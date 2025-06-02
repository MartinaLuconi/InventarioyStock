package com.IntegradorIO.InventarioyStock.Venta.dto;
import java.util.List;

public class VentaRequest {

    /** DNI del cliente que realiza la venta */
    private Long DNIcliente;
    /** Líneas de venta: artículo + cantidad */
    private List<VentaArticuloRequest> articulos;

    public VentaRequest() {}

    public Long getDNIcliente() {
        return DNIcliente;
    }
    public void setDNIcliente(Long DNIcliente) {
        this.DNIcliente = DNIcliente;
    }

    public List<VentaArticuloRequest> getArticulos() {
        return articulos;
    }
    public void setArticulos(List<VentaArticuloRequest> articulos) {
        this.articulos = articulos;
    }
}
