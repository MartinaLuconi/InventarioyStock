package com.IntegradorIO.InventarioyStock.EstrategiaDeRevisionP;

import com.IntegradorIO.InventarioyStock.Articulo.Articulo;

public class ArticuloListenerP {

    private static CalculoServiceP calculoServiceP;

    public static void setCalculoServiceP(CalculoServiceP service) {
        calculoServiceP = service;
    }

    @jakarta.persistence.PrePersist
    @jakarta.persistence.PreUpdate
    public void beforeAnyUpdate(Articulo articulo) {
        if (calculoServiceP != null && articulo != null) {
            calculoServiceP.recalcularYActualizar(articulo);
        }
    }
}