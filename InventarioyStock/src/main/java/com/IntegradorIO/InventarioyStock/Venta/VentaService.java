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

    int totalCantidad = 0;
    List<VentaArticulo> lineas = new ArrayList<>();

    // Validación + armado de objetos
    for (VentaArticuloRequest lar : req.getArticulos()) {
        Articulo art = articuloRepository.findById(lar.getCodigoArticulo())
                .orElseThrow(() -> new IllegalArgumentException("Artículo no encontrado: " + lar.getCodigoArticulo()));

        if (lar.getCantidadVA() > art.getStockActualArticulo()) {
            throw new IllegalStateException("Stock insuficiente para artículo " + art.getCodigoArticulo());
        }

        // Descontar stock
        art.setStockActualArticulo(art.getStockActualArticulo() - lar.getCantidadVA());
        articuloRepository.save(art);

        // Crear línea de venta
        VentaArticulo va = new VentaArticulo();
        va.setArticulo(art);
        va.setCantidadVA(lar.getCantidadVA());
        lineas.add(va);

        totalCantidad += lar.getCantidadVA();
    }

    // Solo en este punto guardamos la venta y los artículos
    Venta v = new Venta();
    v.setDniCliente(req.getDniCliente());
    v.setApellidoCliente(req.getApellidoCliente());
    v.setNombreCliente(req.getNombreCliente());
    v.setFechaVenta(new Timestamp(System.currentTimeMillis()));
    v.setCantidadVenta(totalCantidad);

    float porcentajeVenta = 1.02F;
    float totalVenta = totalCantidad * porcentajeVenta;
    v.setTotalVenta(totalVenta);

    v.setArticulos(lineas);

//    // Guardar artículos con FK ya válida
//    for (VentaArticulo va : lineas) {
//        ventaArticuloRepository.save(va);
//    }
    // Guardar venta primero (porque los artículos tienen FK a venta)
    Venta ventaGuardada = ventaRepository.save(v);
    ventaGuardada.setArticulos(lineas);
    return ventaGuardada;
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

    public VentaRequest mostrarDetalleVentas(int nroVenta) throws Exception {
        VentaRequest dtoVenta = new VentaRequest();
        Venta ventaDatos = ventaRepository.obtenerVenta(nroVenta);
            dtoVenta.setNombreCliente(ventaDatos.getNombreCliente());
            dtoVenta.setDniCliente(ventaDatos.getDniCliente());
            dtoVenta.setApellidoCliente(ventaDatos.getApellidoCliente());

            List<VentaArticulo> vaList = ventaDatos.getArticulos();
            List<VentaArticuloRequest> dtoListaVA=new ArrayList<>();
            for (VentaArticulo va : vaList){
                VentaArticuloRequest dtoVA = new VentaArticuloRequest();
                dtoVA.setCantidadVA(va.getCantidadVA());
                dtoVA.setCodigoArticulo(va.getArticulo().getCodigoArticulo());
                dtoVA.setNombreArticulo(va.getArticulo().getNombreArticulo());
                dtoListaVA.add(dtoVA);
            }
        dtoVenta.setArticulos(dtoListaVA);

        return dtoVenta;

    }



}
