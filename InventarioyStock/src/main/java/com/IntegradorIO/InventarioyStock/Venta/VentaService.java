package com.IntegradorIO.InventarioyStock.Venta;

import com.IntegradorIO.InventarioyStock.Articulo.Articulo;
import com.IntegradorIO.InventarioyStock.Articulo.ArticuloRepository;
import com.IntegradorIO.InventarioyStock.VentaArticulo.VentaArticulo;
import com.IntegradorIO.InventarioyStock.VentaArticulo.VentaArticuloRepository;
import com.IntegradorIO.InventarioyStock.Venta.dto.VentaArticuloRequest;
import com.IntegradorIO.InventarioyStock.Venta.dto.VentaRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class VentaService {

    @Autowired private VentaRepository ventaRepository;
    @Autowired private VentaArticuloRepository ventaArticuloRepository;
    @Autowired private ArticuloRepository articuloRepository;

    /**
     * Alta de venta (MVP):
     * - Valida líneas no vacías.
     * - Resta stock.
     * - Guarda cabecera y líneas (solo cantidad).
     */
    public Venta guardarVentaConArticulos(VentaRequest req) {
        // 1) Validación mínima
        if (req.getArticulos() == null || req.getArticulos().isEmpty()) {
            throw new IllegalArgumentException("Debe incluir al menos un artículo");
        }

        // 2) Crear cabecera
        Venta v = new Venta();
        v.setDNIcliente(req.getDNIcliente());
        v.setFechaHoraVenta(new Timestamp(System.currentTimeMillis()));
        // cantidadVenta la calculamos tras procesar líneas
        v.setCantidadVenta(0);
        Venta ventaGuardada = ventaRepository.save(v);

        // 3) Procesar líneas
        int totalCantidad = 0;
        List<VentaArticulo> lineas = new ArrayList<>();
        for (VentaArticuloRequest lar : req.getArticulos()) {
            Articulo art = articuloRepository.findById(lar.getCodigoArticulo())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Artículo no encontrado: " + lar.getCodigoArticulo()));

            if (lar.getCantidadVA() > art.getStockActualArticulo()) {
                throw new IllegalStateException(
                        "Stock insuficiente para artículo " + art.getCodigoArticulo());
            }

            // Restar stock
            art.setStockActualArticulo(art.getStockActualArticulo() - lar.getCantidadVA());
            articuloRepository.save(art);

            // Crear línea sin precio
            VentaArticulo va = new VentaArticulo();
            va.setVenta(ventaGuardada);
            va.setArticulo(art);
            va.setCantidadVA(lar.getCantidadVA());
            lineas.add(ventaArticuloRepository.save(va));

            totalCantidad += lar.getCantidadVA();
        }

        // 4) Actualizar cantidadVenta y guardar de nuevo
        ventaGuardada.setArticulos(lineas);
        ventaGuardada.setCantidadVenta(totalCantidad);
        return ventaRepository.save(ventaGuardada);
    }
}
