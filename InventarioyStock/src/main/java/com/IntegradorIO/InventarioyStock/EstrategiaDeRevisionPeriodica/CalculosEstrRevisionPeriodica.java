package com.IntegradorIO.InventarioyStock.EstrategiaDeRevisionPeriodica;

public class CalculosEstrRevisionPeriodica {

    // Cantidad a pedir q = Demanda durante el periodo de revisión + Demanda durante el lead time + Stock de seguridad - Inventario disponible
    public static int calcularCantidadAPedir(
            int demandaAnual,
            double periodoRevision,
            int demoraEntrega,
            double desviacionEstandar,
            double Z,
            int inventarioDisponible
    ) {
        double demandaDiaria = demandaAnual / 365.0;
        double demandaPeriodo = demandaDiaria * periodoRevision;
        double demandaLeadTime = demandaDiaria * demoraEntrega;
        int stockSeguridad = calcularStockSeguridad(Z, desviacionEstandar, periodoRevision, demoraEntrega);
        return (int) Math.round(demandaPeriodo + demandaLeadTime + stockSeguridad - inventarioDisponible);
    }

    // Stock de seguridad para revisión periódica
    public static int calcularStockSeguridad(
            double Z,
            double desviacionEstandar,
            double periodoRevision,
            int demoraEntrega
    ) {
        double L = periodoRevision + demoraEntrega;
        return (int) Math.round(Z * desviacionEstandar * Math.sqrt(L));
    }

    // Inventario máximo = Demanda durante el periodo de revisión + Demanda durante el lead time + Stock de seguridad
    public static int calcularInventarioMaximo(
            int demandaAnual,
            double periodoRevision,
            int demoraEntrega,
            double desviacionEstandar,
            double Z
    ) {
        double demandaDiaria = demandaAnual / 365.0;
        double demandaPeriodo = demandaDiaria * periodoRevision;
        double demandaLeadTime = demandaDiaria * demoraEntrega;
        int stockSeguridad = calcularStockSeguridad(Z, desviacionEstandar, periodoRevision, demoraEntrega);
        return (int) Math.round(demandaPeriodo + demandaLeadTime + stockSeguridad);
    }

    // Inventario promedio = Inventario máximo / 2
    public static double calcularInventarioPromedio(int inventarioMaximo) {
        return inventarioMaximo / 2.0;
    }

    // Costo anual de mantener inventario
    public static double calcularCostoAnualMantener(
            double inventarioPromedio,
            double costoMantenimiento
    ) {
        return inventarioPromedio * costoMantenimiento;
    }
}