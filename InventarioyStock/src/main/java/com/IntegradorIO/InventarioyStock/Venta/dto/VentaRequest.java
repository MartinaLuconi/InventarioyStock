package com.IntegradorIO.InventarioyStock.Venta.dto;

import java.time.LocalDateTime;
import java.util.List;

public class VentaRequest {

    /** ID de la venta (para modificación, si lo necesitás en el futuro) */
    private Long idVenta;

    /** Nombre del cliente */
    private String nombreCliente;

    /** Apellido del cliente */
    private String apellidoCliente;

    /** DNI del cliente */
    private Long DNIcliente;

    /** Fecha de la venta */
    private LocalDateTime fechaVenta;

    /** Artículos vendidos */
    private List<VentaArticuloRequest> articulos;

    public VentaRequest() {}

    public Long getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(Long idVenta) {
        this.idVenta = idVenta;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getApellidoCliente() {
        return apellidoCliente;
    }

    public void setApellidoCliente(String apellidoCliente) {
        this.apellidoCliente = apellidoCliente;
    }

    public Long getDNIcliente() {
        return DNIcliente;
    }

    public void setDNIcliente(Long DNIcliente) {
        this.DNIcliente = DNIcliente;
    }

    public LocalDateTime getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(LocalDateTime fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public List<VentaArticuloRequest> getArticulos() {
        return articulos;
    }

    public void setArticulos(List<VentaArticuloRequest> articulos) {
        this.articulos = articulos;
    }
}
