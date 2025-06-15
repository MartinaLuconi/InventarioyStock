// java/com/IntegradorIO/InventarioyStock/EstrategiaDeRevisión/ArticuloListener.java
package com.IntegradorIO.InventarioyStock.EstrategiaDeRevisiónContinua;

import com.IntegradorIO.InventarioyStock.Articulo.Articulo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ArticuloListener {

    private static CalculoService calculoService;

    @Autowired
    public void setCalculoService(CalculoService service) {
        ArticuloListener.calculoService = service;
    }

    @jakarta.persistence.PrePersist
    @jakarta.persistence.PreUpdate
    public void beforeAnyUpdate(Articulo articulo) {
        if (calculoService != null) {
            calculoService.recalcularYActualizar(articulo);
        }
    }
}