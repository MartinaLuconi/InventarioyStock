package com.IntegradorIO.InventarioyStock.EstrategiaDeRevisión;


public class CGIModel {
    private int demandaAnual;
    private double costoPedido;
    private double costoUnitario;
    private double costoMantenimiento;
    private double costoAlmacenamiento;
    private int eoq;
    private double cgi;

    public void setDemandaAnual(int demandaAnual) {
        this.demandaAnual = demandaAnual;
        recalcularEOQyCGI();
    }
    public void setCostoPedido(double costoPedido) {
        this.costoPedido = costoPedido;
        recalcularEOQyCGI();
    }
    public void setCostoUnitario(double costoUnitario) {
        this.costoUnitario = costoUnitario;
        recalcularEOQyCGI();
    }
    public void setCostoMantenimiento(double costoMantenimiento) {
        this.costoMantenimiento = costoMantenimiento;
        recalcularEOQyCGI();
    }
    public void setCostoAlmacenamiento(double costoAlmacenamiento) {
        this.costoAlmacenamiento = costoAlmacenamiento;
        recalcularEOQyCGI();
    }
    private void recalcularEOQyCGI() {
        this.eoq = CalculosEstrRevisionContinua.calcularEOQ(demandaAnual, costoPedido, costoAlmacenamiento);
        this.cgi = CalculosEstrRevisionContinua.calcularCGI(
                demandaAnual, eoq, costoPedido, costoUnitario, costoMantenimiento
        );
    }
    public int getEOQ() {
        return eoq;
    }
    public double getCGI() {
        return cgi;
    }
}
