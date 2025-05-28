package com.IntegradorIO.InventarioyStock.EstrategiaDeRevisión;

public class CGIModel {
    private int demandaAnual;
    private int loteOptimo;
    private double costoPedido;
    private double costoUnitario;
    private double costoMantenimiento;
    private double cgi;

    public void setDemandaAnual(int demandaAnual) {
        this.demandaAnual = demandaAnual;
        recalcularCGI();
    }
    public void setLoteOptimo(int loteOptimo) {
        this.loteOptimo = loteOptimo;
        recalcularCGI();
    }
    public void setCostoPedido(double costoPedido) {
        this.costoPedido = costoPedido;
        recalcularCGI();
    }
    public void setCostoUnitario(double costoUnitario) {
        this.costoUnitario = costoUnitario;
        recalcularCGI();
    }
    public void setCostoMantenimiento(double costoMantenimiento) {
        this.costoMantenimiento = costoMantenimiento;
        recalcularCGI();
    }
    private void recalcularCGI() {
        this.cgi = CalculosEstrRevisionContinua.calcularCGI(
                demandaAnual, loteOptimo, costoPedido, costoUnitario, costoMantenimiento
        );
    }
    public double getCGI() {
        return cgi;
    }
}
