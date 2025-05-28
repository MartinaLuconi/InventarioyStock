package com.IntegradorIO.InventarioyStock.EstrategiaDeRevisión;

public class CalculosEstrRevisionContinua {

    public static double calcularCGI(
            int demandaAnual,
            int loteOptimo,
            double costoPedido,
            double costoUnitario,
            double costoMantenimiento
    ) {
        if (loteOptimo == 0) return 0;
        return (demandaAnual / (double) loteOptimo) * costoPedido
                + (loteOptimo / 2.0) * costoMantenimiento
                + demandaAnual * costoUnitario;
    }
}
