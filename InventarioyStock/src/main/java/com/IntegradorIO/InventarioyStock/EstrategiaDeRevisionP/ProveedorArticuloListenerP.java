package com.IntegradorIO.InventarioyStock.EstrategiaDeRevisionP;

import com.IntegradorIO.InventarioyStock.ProveedorArticulo.ProveedorArticulo;

public class ProveedorArticuloListenerP {

    private static CalculoServiceP calculoServiceP;


    public static void setCalculoServiceP(CalculoServiceP service) {
        calculoServiceP = service;
    }

    @jakarta.persistence.PrePersist
    @jakarta.persistence.PreUpdate
    public void beforeAnyUpdate(ProveedorArticulo proveedorArticulo) {
        if (calculoServiceP != null && proveedorArticulo != null) {
            calculoServiceP.recalcularYActualizar(proveedorArticulo);
        }
    }
}