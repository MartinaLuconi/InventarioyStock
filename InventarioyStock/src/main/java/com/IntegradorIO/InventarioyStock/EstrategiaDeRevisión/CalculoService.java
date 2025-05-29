// java/com/IntegradorIO/InventarioyStock/EstrategiaDeRevisión/CalculoService.java
package com.IntegradorIO.InventarioyStock.EstrategiaDeRevisión;

import com.IntegradorIO.InventarioyStock.Articulo.Articulo;
import com.IntegradorIO.InventarioyStock.ProveedorArticulo.ProveedorArticulo;
import org.springframework.stereotype.Service;

@Service
public class CalculoService {

    public void recalcularYActualizar(Articulo articulo) {
        for (ProveedorArticulo pa : articulo.getProveedorArticuloList()) {
            int eoq = CalculosEstrRevisionContinua.calcularEOQ(
                    articulo.getDemandaAnual(),
                    pa.getCostoPedido(),
                    pa.getCostoAlmacenamiento()
            );
            pa.setLoteOptimo(eoq);

            double cgi = CalculosEstrRevisionContinua.calcularCGI(
                    articulo.getDemandaAnual(),
                    eoq,
                    pa.getCostoPedido(),
                    pa.getCostoUnitario(),
                    pa.getCostoMantenimiento()
            );
            pa.setCGIProveedorArticulo((float) cgi);

            int rop = CalculosEstrRevisionContinua.calcularROP(
                    articulo.getDemandaAnual() / 365.0,
                    articulo.getStockSeguridadArticulo(),
                    pa.getDemoraEntrega()
            );
            articulo.setPuntoPedido(rop);
        }
    }
}