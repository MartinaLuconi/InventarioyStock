// java/com/IntegradorIO/InventarioyStock/EstrategiaDeRevisión/CalculoService.java
package com.IntegradorIO.InventarioyStock.EstrategiaDeRevisión;

import com.IntegradorIO.InventarioyStock.Articulo.Articulo;
import com.IntegradorIO.InventarioyStock.Articulo.ArticuloRepository;
import com.IntegradorIO.InventarioyStock.Proveedor.Proveedor;
import com.IntegradorIO.InventarioyStock.ProveedorArticulo.ProveedorArticulo;
import com.IntegradorIO.InventarioyStock.ProveedorArticulo.ProveedorArticuloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalculoService {

    @Autowired
    private ProveedorArticuloRepository proveedorArticuloRepository;

    @Autowired
    private ArticuloRepository articuloRepository;

    public void recalcularYActualizar(Articulo articulo) {

      /*  for (ProveedorArticulo pa : articulo.getProveedorArticuloList()) {
            int eoq = CalculosEstrRevisionContinua.calcularEOQ(
                    articulo.getDemandaAnual(),
                    pa.getCostoPedido(),
                    articulo.getCostoAlmacenamiento()
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


            int stockSeguridad = CalculosEstrRevisionContinua.calcularStockSeguridad(
                    pa.getNivelDeServicio(),
                    articulo.getDesviacionEstandar(),
                    pa.getDemoraEntrega()
            );
            articulo.setStockSeguridadArticulo(stockSeguridad);

            int rop = CalculosEstrRevisionContinua.calcularROP(
                    articulo.getDemandaAnual() / 365.0,
                    articulo.getStockSeguridadArticulo(),
                    pa.getDemoraEntrega()
            );
            articulo.setPuntoPedido(rop);

            proveedorArticuloRepository.save(pa);
        }
        articuloRepository.save(articulo);*/
    }
}