package com.IntegradorIO.InventarioyStock.EstrategiaDeRevisionPeriodica;

import com.IntegradorIO.InventarioyStock.Articulo.Articulo;
import com.IntegradorIO.InventarioyStock.ProveedorArticulo.ProveedorArticulo;
import com.IntegradorIO.InventarioyStock.ProveedorArticulo.ProveedorArticuloRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CalculoServiceP {

    private final ProveedorArticuloRepository proveedorArticuloRepository;

    public CalculoServiceP(ProveedorArticuloRepository proveedorArticuloRepository) {
        this.proveedorArticuloRepository = proveedorArticuloRepository;
    }

    public void recalcularYActualizar(Articulo articulo) {
        List<ProveedorArticulo> proveedores = proveedorArticuloRepository.findByArticulo(articulo);

        for (ProveedorArticulo pa : proveedores) {
            int stockSeguridad = CalculosEstrRevisionPeriodica.calcularStockSeguridad(
                    pa.getNivelDeServicio(),
                    articulo.getDesviacionEstandar(),
                    pa.getPeriodoRevision(),
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

            double costoAnualMantener = CalculosEstrRevisionPeriodica.calcularCostoAnualMantener(
                    inventarioPromedio,
                    pa.getCostoMantenimiento()
            );

            int q = CalculosEstrRevisionPeriodica.calcularCantidadAPedir(
                    articulo.getDemandaAnual(),
                    pa.getPeriodoRevision(),
                    pa.getDemoraEntrega(),
                    articulo.getDesviacionEstandar(),
                    pa.getNivelDeServicio(),
                    articulo.getStockActualArticulo()
            );
            pa.setLoteOptimo(q);
            pa.setEoq(q);

            double cgi = CalculosEstrRevisionPeriodica.calcularCGI(
                    articulo.getDemandaAnual(),
                    q,
                    pa.getCostoPedido(),
                    pa.getCostoUnitario(),
                    pa.getCostoMantenimiento()
            );
            pa.setCGIProveedorArticulo((float) cgi);
        }
    }

    public void recalcularYActualizar(ProveedorArticulo proveedorArticulo) {
        // Implementar si es necesario
    }
}