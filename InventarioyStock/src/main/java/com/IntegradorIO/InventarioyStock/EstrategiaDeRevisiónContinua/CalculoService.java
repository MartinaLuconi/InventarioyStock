package com.IntegradorIO.InventarioyStock.EstrategiaDeRevisiónContinua;

import com.IntegradorIO.InventarioyStock.Articulo.Articulo;
import com.IntegradorIO.InventarioyStock.Articulo.ArticuloRepository;
import com.IntegradorIO.InventarioyStock.ProveedorArticulo.ProveedorArticulo;
import com.IntegradorIO.InventarioyStock.ProveedorArticulo.ProveedorArticuloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CalculoService {

    @Autowired
    private ProveedorArticuloRepository proveedorArticuloRepository;

    @Autowired
    private ArticuloRepository articuloRepository;

    public void recalcularYActualizar(Articulo articulo) {

        List<ProveedorArticulo> proveedores = proveedorArticuloRepository.findByArticulo(articulo);

        for (ProveedorArticulo pa : proveedores) {
            int eoq = CalculosEstrRevisionContinua.calcularEOQ(
                    articulo.getDemandaAnual(),
                    pa.getCostoPedido(),
                    articulo.getCostoAlmacenamiento()
            );
            pa.setLoteOptimo(eoq);
            pa.setEoq(eoq);

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
        articuloRepository.save(articulo);
    }
}