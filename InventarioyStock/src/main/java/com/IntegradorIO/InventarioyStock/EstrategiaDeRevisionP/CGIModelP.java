package com.IntegradorIO.InventarioyStock.EstrategiaDeRevisionP;

public class CGIModelP {
    private int demandaAnual;
    private int periodoRevision;
    private int demoraEntrega;
    private double desviacionEstandar;
    private double Z;
    private int stockActualArticulo;
    private int stockSeguridadArticulo;
    private double costoPedido;
    private double costoUnitario;
    private double costoMantenimiento;
    private int loteOptimo;
    private double cgi;
    private int cantidadAPedir; // q

    public void setDemandaAnual(int demandaAnual) {
        this.demandaAnual = demandaAnual;
        recalcular();
    }
    public void setPeriodoRevision(int periodoRevision) {
        this.periodoRevision = periodoRevision;
        recalcular();
    }
    public void setDemoraEntrega(int demoraEntrega) {
        this.demoraEntrega = demoraEntrega;
        recalcular();
    }
    public void setDesviacionEstandar(double desviacionEstandar) {
        this.desviacionEstandar = desviacionEstandar;
        recalcular();
    }
    public void setZ(double Z) {
        this.Z = Z;
        recalcular();
    }
    public void setStockActualArticulo(int stockActualArticulo) {
        this.stockActualArticulo = stockActualArticulo;
        recalcular();
    }
    public void setStockSeguridadArticulo(int stockSeguridadArticulo) {
        this.stockSeguridadArticulo = stockSeguridadArticulo;
        recalcular();
    }
    public void setCostoPedido(double costoPedido) {
        this.costoPedido = costoPedido;
        recalcular();
    }
    public void setCostoUnitario(double costoUnitario) {
        this.costoUnitario = costoUnitario;
        recalcular();
    }
    public void setCostoMantenimiento(double costoMantenimiento) {
        this.costoMantenimiento = costoMantenimiento;
        recalcular();
    }

    private void recalcular() {
        this.loteOptimo = CalculosEstrRevisionPeriodica.calcularLoteOptimo(
                demandaAnual, periodoRevision, demoraEntrega, costoPedido, costoMantenimiento
        );
        this.cgi = CalculosEstrRevisionPeriodica.calcularCGI(
                demandaAnual, loteOptimo, costoPedido, costoUnitario, costoMantenimiento
        );
        this.cantidadAPedir = CalculosEstrRevisionPeriodica.calcularCantidadAPedir(
                demandaAnual, periodoRevision, demoraEntrega, desviacionEstandar, Z, stockActualArticulo
        );
    }

    public int getLoteOptimo() {
        return loteOptimo;
    }
    public double getCGI() {
        return cgi;
    }
    public int getCantidadAPedir() {
        return cantidadAPedir;
    }
}