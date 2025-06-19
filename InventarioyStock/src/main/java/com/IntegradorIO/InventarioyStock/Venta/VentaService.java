package com.IntegradorIO.InventarioyStock.Venta;

import com.IntegradorIO.InventarioyStock.Articulo.Articulo;
import com.IntegradorIO.InventarioyStock.Articulo.ArticuloRepository;
import com.IntegradorIO.InventarioyStock.OrdenCompra.DTO.DTODetalleOC;
import com.IntegradorIO.InventarioyStock.OrdenCompra.DTO.DTOOrdenCompra;
import com.IntegradorIO.InventarioyStock.OrdenCompra.OrdenCompraService;
import com.IntegradorIO.InventarioyStock.ProveedorArticulo.ProveedorArticuloRepository;
import com.IntegradorIO.InventarioyStock.Venta.dto.DTOTablaVentas;
import com.IntegradorIO.InventarioyStock.VentaArticulo.VentaArticulo;
import com.IntegradorIO.InventarioyStock.VentaArticulo.VentaArticuloRepository;
import com.IntegradorIO.InventarioyStock.Venta.dto.VentaArticuloRequest;
import com.IntegradorIO.InventarioyStock.Venta.dto.VentaRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VentaService {

    @Autowired private VentaRepository ventaRepository;
    @Autowired private VentaArticuloRepository ventaArticuloRepository;
    @Autowired private ArticuloRepository articuloRepository;
    @Autowired private ProveedorArticuloRepository proveedorArticuloRepository;
    @Autowired private OrdenCompraService ordenCompraService;


    public Venta guardarVentaConArticulos(VentaRequest req) {


        if (req.getArticulos() == null || req.getArticulos().isEmpty()) {
            throw new IllegalArgumentException("Debe incluir al menos un artículo");
        }


        Venta v = new Venta();
        v.setDniCliente(req.getDniCliente());
        v.setFechaVenta(new Timestamp(System.currentTimeMillis()));
        v.setCantidadVenta(0);
        Venta ventaGuardada = ventaRepository.save(v);




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


            // Descontar stock
            art.setStockActualArticulo(art.getStockActualArticulo() - lar.getCantidadVA());
            articuloRepository.save(art);


            // Crear línea de venta
            VentaArticulo va = new VentaArticulo();
            va.setVenta(ventaGuardada);
            va.setArticulo(art);
            va.setCantidadVA(lar.getCantidadVA());
            ventaArticuloRepository.save(va);
            lineas.add(va);


            totalCantidad += lar.getCantidadVA();

/*
           // Lógica de generación automática de OC:
           if (art.getModeloInventario() == ModeloInventario.LOTE_FIJO &&
                   art.getStockActualArticulo() <= art.getPuntoPedido()) {


               // Buscar proveedor predeterminado
               ProveedorArticulo proveedorArticulo = proveedorArticuloRepository
                       .findByArticuloAndEsPredeterminadoTrue(art)
                       .orElseThrow(() -> new RuntimeException(
                               "No se encontró proveedor predeterminado para el artículo: " + art.getCodigoArticulo()));


               // Generar DTO de orden de compra
               DTOOrdenCompra dtoOC = new DTOOrdenCompra();
               dtoOC.setNombreOC("Reposición automática artículo: " + art.getCodigoArticulo());
               dtoOC.setIdProveedor(proveedorArticulo.getProveedor().getIdProveedor());


               DTODetalleOC detalleOC = new DTODetalleOC();
               detalleOC.setCodArticulo(art.getCodigoArticulo());
               detalleOC.setNombreArticulo(art.getNombreArticulo());
               detalleOC.setCantidadArticulo(proveedorArticulo.getEoq());  // <-- usamos el eoq


               dtoOC.setDetallesOC(List.of(detalleOC));


               // Llamada final al servicio de OrdenCompra
               ordenCompraService.crearOrdenCompra(dtoOC);




           }
           */


        }
        //buscar COSTO UNITARIO PARA CALCULAR EL TOTAL DE LA VENTA EN LA INTERMEDIA DEL ARTICULO
        float costoUnitarioArt=1;
        float totalVenta = totalCantidad*costoUnitarioArt;

        ventaGuardada.setArticulos(lineas);
        ventaGuardada.setCantidadVenta(totalCantidad);
        ventaGuardada.setTotalVenta(totalVenta);
        ventaGuardada.setDniCliente(req.getDniCliente());
        ventaGuardada.setApellidoCliente(req.getApellidoCliente());
        ventaGuardada.setNombreCliente(req.getNombreCliente());
        return ventaRepository.save(ventaGuardada);
    }


    public List<DTOTablaVentas> obtenerVentas() throws Exception {
        try {
            List<Venta> ventas = ventaRepository.findAll();
            return ventas.stream()
                    .map(DTOTablaVentas::new)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


}
