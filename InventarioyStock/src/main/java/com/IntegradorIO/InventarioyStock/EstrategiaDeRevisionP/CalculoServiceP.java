// src/main/java/com/IntegradorIO/InventarioyStock/EstrategiaDeRevisionP/CalculoServiceP.java
package com.IntegradorIO.InventarioyStock.EstrategiaDeRevisionP;

import com.IntegradorIO.InventarioyStock.Articulo.Articulo;
import com.IntegradorIO.InventarioyStock.EstrategiaDeRevisión.CalculosEstrRevisionContinua;
import com.IntegradorIO.InventarioyStock.Proveedor.Proveedor;
import com.IntegradorIO.InventarioyStock.ProveedorArticulo.ProveedorArticulo;
import org.hibernate.mapping.Array;
import org.hibernate.mapping.List;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CalculoServiceP {
/*
    public void recalcularYActualizar(Articulo articulo, double periodoRevision) {
        for (ProveedorArticulo pa : articulo.getProveedorArticuloList()) {
            double Z = 1.65; // Nivel de servicio 95%
            int stockSeguridad = CalculosEstrRevisionPeriodica.calcularStockSeguridad(
                    Z,
                    articulo.getDesviacionEstandar(),
                    periodoRevision,
                    pa.getDemoraEntrega()
            );
            articulo.setStockSeguridadArticulo(stockSeguridad);

            int inventarioMaximo = CalculosEstrRevisionPeriodica.calcularInventarioMaximo(
                    articulo.getDemandaAnual(),
                    periodoRevision,
                    pa.getDemoraEntrega(),
                    articulo.getDesviacionEstandar(),
                    Z
            );
            articulo.setInventarioMaximo(inventarioMaximo);

            double inventarioPromedio = CalculosEstrRevisionPeriodica.calcularInventarioPromedio(inventarioMaximo);
            articulo.setInventarioPromedio(inventarioPromedio);

            double costoAnualMantener = CalculosEstrRevisionPeriodica.calcularCostoAnualMantener(
                    inventarioPromedio,
                    pa.getCostoMantenimiento()
            );
            pa.setCostoAnualMantener(costoAnualMantener);

            int cantidadAPedir = CalculosEstrRevisionPeriodica.calcularCantidadAPedir(
                    articulo.getDemandaAnual(),
                    periodoRevision,
                    pa.getDemoraEntrega(),
                    articulo.getDesviacionEstandar(),
                    Z,
                    articulo.getInventarioDisponible()
            );
            pa.setCantidadAPedir(cantidadAPedir);
        }
    }
*/
}
