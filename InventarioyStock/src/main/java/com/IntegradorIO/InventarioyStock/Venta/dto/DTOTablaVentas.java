package com.IntegradorIO.InventarioyStock.Venta.dto;


import com.IntegradorIO.InventarioyStock.Venta.Venta;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


import java.sql.Timestamp;
import java.time.LocalDateTime;


@Data
@Getter
@Setter
public class DTOTablaVentas {


    private int nroVenta;
    private String nombreCliente;
    private String apellidoCliente;
    private Long dniCliente;
    private Timestamp fechaVenta;
    private int cantidadVenta;


    public DTOTablaVentas(Venta venta) {
        this.nroVenta = venta.getNroVenta();
        this.nombreCliente = venta.getNombreCliente();
        this.apellidoCliente = venta.getApellidoCliente();
        this.dniCliente = venta.getDniCliente();
        this.fechaVenta = venta.getFechaVenta();
        this.cantidadVenta = venta.getCantidadVenta();
    }
}
