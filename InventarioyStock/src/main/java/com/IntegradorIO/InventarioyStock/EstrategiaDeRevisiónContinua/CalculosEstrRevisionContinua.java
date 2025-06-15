package com.IntegradorIO.InventarioyStock.EstrategiaDeRevisiónContinua;


public class CalculosEstrRevisionContinua {

    public static double calcularCGI(
            int demandaAnual,
            int eoq,
            double costoPedido,
            double costoUnitario,
            double costoMantenimiento
    ) {
        if (eoq == 0) return 0;
        return (demandaAnual / (double) eoq) * costoPedido
                + (eoq / 2.0) * costoMantenimiento
                + demandaAnual * costoUnitario;
    }

    public static int calcularEOQ(int demandaAnual, double costoPedido, double costoAlmacenamiento) {
        return (int) Math.round(Math.sqrt((2 * demandaAnual * costoPedido) / costoAlmacenamiento));
    }

    public static int calcularStockSeguridad(double Z, double desviacionEstandar, int L) {
        return (int) Math.round(Z * desviacionEstandar * Math.sqrt(L));
    }

    public static int calcularROP(double demandaPromedio, int stockSeguridad, int L) {
        return (int) Math.round(demandaPromedio * L + stockSeguridad);
    }
}