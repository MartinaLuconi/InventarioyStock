package com.IntegradorIO.InventarioyStock.EstrategiaDeRevisionPeriodica;

import com.IntegradorIO.InventarioyStock.ProveedorArticulo.ProveedorArticulo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProveedorArticuloListenerP {

    private static CalculoServiceP calculoServiceP;

    @Autowired
    public void setCalculoServiceP(CalculoServiceP service) {
        ProveedorArticuloListenerP.calculoServiceP = service;
    }

    @jakarta.persistence.PrePersist
    @jakarta.persistence.PreUpdate
    public void beforeAnyUpdate(ProveedorArticulo proveedorArticulo) {
        if (calculoServiceP != null && proveedorArticulo != null) {
            calculoServiceP.recalcularYActualizar(proveedorArticulo);
        }
    }
}