// java/com/IntegradorIO/InventarioyStock/EstrategiaDeRevisión/ProveedorArticuloListener.java
package com.IntegradorIO.InventarioyStock.EstrategiaDeRevisiónContinua;

import com.IntegradorIO.InventarioyStock.ProveedorArticulo.ProveedorArticulo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProveedorArticuloListener {

    private static CalculoService calculoService;

    @Autowired
    public void setCalculoService(CalculoService service) {
        ProveedorArticuloListener.calculoService = service;
    }
    /*
    @jakarta.persistence.PrePersist
    @jakarta.persistence.PreUpdate
    public void beforeAnyUpdate(ProveedorArticulo proveedorArticulo) {
        if (calculoService != null && proveedorArticulo.getArticulo() != null) {
            calculoService.recalcularYActualizar(proveedorArticulo.getArticulo());
        }
    } */
}