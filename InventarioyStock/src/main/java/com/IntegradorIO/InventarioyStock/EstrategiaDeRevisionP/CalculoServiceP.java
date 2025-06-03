// src/main/java/com/IntegradorIO/InventarioyStock/EstrategiaDeRevisionP/CalculoServiceP.java
package com.IntegradorIO.InventarioyStock.EstrategiaDeRevisionP;

import com.IntegradorIO.InventarioyStock.Articulo.Articulo;
import com.IntegradorIO.InventarioyStock.EstrategiaDeRevisión.CalculosEstrRevisionContinua;
import com.IntegradorIO.InventarioyStock.ProveedorArticulo.ProveedorArticulo;
import org.springframework.stereotype.Service;

@Service
public class CalculoServiceP {

    public void recalcularYActualizar(Articulo articulo, double periodoRevision) {
        for (ProveedorArticulo pa : articulo.getProveedorArticuloList()) {

            int stockSeguridad = CalculosEstrRevisionPeriodica.calcularStockSeguridad(
                    pa.getNivelDeServicio(),
                    articulo.getDesviacionEstandar(),
                    periodoRevision,
                    pa.getDemoraEntrega()
            );
            articulo.setStockSeguridadArticulo(stockSeguridad);

            int inventarioMaximo = CalculosEstrRevisionPeriodica.calcularInventarioMaximo(
                    articulo.getDemandaAnual(),
                    pa.getPeriodoRevision(),
                    pa.getDemoraEntrega(),
                    articulo.getDesviacionEstandar(),
                    pa.getNivelDeServicio()
            );
            pa.setInventarioMaximo(inventarioMaximo);

            double inventarioPromedio = CalculosEstrRevisionPeriodica.calcularInventarioPromedio(inventarioMaximo);
            //pa.setInventarioPromedio(inventarioPromedio);

            double costoAnualMantener = CalculosEstrRevisionPeriodica.calcularCostoAnualMantener(
                    inventarioPromedio,
                    pa.getCostoMantenimiento()
            );
            //pa.setCostoAnualMantener(costoAnualMantener);

            int q = CalculosEstrRevisionPeriodica.calcularCantidadAPedir(
                    articulo.getDemandaAnual(),
                    periodoRevision,
                    pa.getDemoraEntrega(),
                    articulo.getDesviacionEstandar(),
                    pa.getNivelDeServicio(),
                    articulo.getStockActualArticulo()
            );
         //   pa.setCantidadAPedir(cantidadAPedir);

            double cgi = CalculosEstrRevisionContinua.calcularCGI(
                    articulo.getDemandaAnual(),
                    q,
                    pa.getCostoPedido(),
                    pa.getCostoUnitario(),
                    pa.getCostoMantenimiento()
            );
            pa.setCGIProveedorArticulo((float) cgi);
        }
    }

}
