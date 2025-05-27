package com.IntegradorIO.InventarioyStock.Proveedor.dto;

public class ProveedorArticuloRequest {
    private Integer codigoArticulo;
    private int demoraEntrega;
    private float precioUnitProveedorArticulo;
    private float costoPedido;
    private int cargoProveedorPedido;
    private boolean esPredeterminado;

    public ProveedorArticuloRequest() { }

    public Integer getCodigoArticulo() {
        return codigoArticulo;
    }

    public void setCodigoArticulo(Integer codigoArticulo) {
        this.codigoArticulo = codigoArticulo;
    }

    public int getDemoraEntrega() {
        return demoraEntrega;
    }

    public void setDemoraEntrega(int demoraEntrega) {
        this.demoraEntrega = demoraEntrega;
    }

    public float getPrecioUnitProveedorArticulo() {
        return precioUnitProveedorArticulo;
    }

    public void setPrecioUnitProveedorArticulo(float precioUnitProveedorArticulo) {
        this.precioUnitProveedorArticulo = precioUnitProveedorArticulo;
    }

    public float getCostoPedido() {
        return costoPedido;
    }

    public void setCostoPedido(float costoPedido) {
        this.costoPedido = costoPedido;
    }

    public int getCargoProveedorPedido() {
        return cargoProveedorPedido;
    }

    public void setCargoProveedorPedido(int cargoProveedorPedido) {
        this.cargoProveedorPedido = cargoProveedorPedido;
    }

    public boolean isEsPredeterminado() {
        return esPredeterminado;
    }

    public void setEsPredeterminado(boolean esPredeterminado) {
        this.esPredeterminado = esPredeterminado;
    }
}
