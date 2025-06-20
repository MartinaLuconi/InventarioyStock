package com.IntegradorIO.InventarioyStock.Venta.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VentaArticuloRequest {

    /** Código del artículo a vender */
    private int codigoArticulo;
    private String nombreArticulo;
    /** Cantidad de unidades vendidas */
    private int cantidadVA;


    public VentaArticuloRequest() {}

    public Integer getCodigoArticulo() {
        return codigoArticulo;
    }

    public void setCodigoArticulo(Integer codigoArticulo) {
        this.codigoArticulo = codigoArticulo;
    }

    public int getCantidadVA() {
        return cantidadVA;
    }
    public void setCantidadVA(int cantidadVA) {
        this.cantidadVA = cantidadVA;
    }
}

