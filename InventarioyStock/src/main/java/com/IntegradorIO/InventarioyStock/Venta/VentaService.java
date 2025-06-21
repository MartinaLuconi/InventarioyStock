package com.IntegradorIO.InventarioyStock.Venta;

import com.IntegradorIO.InventarioyStock.Articulo.Articulo;
import com.IntegradorIO.InventarioyStock.Articulo.ArticuloRepository;
import com.IntegradorIO.InventarioyStock.Articulo.ModeloInventario;
import com.IntegradorIO.InventarioyStock.OrdenCompra.DTO.DTODetalleOC;
import com.IntegradorIO.InventarioyStock.OrdenCompra.DTO.DTOOrdenCompra;
import com.IntegradorIO.InventarioyStock.OrdenCompra.DTO.DTOTablaOrdenCompra;
import com.IntegradorIO.InventarioyStock.OrdenCompra.OrdenCompraService;
import com.IntegradorIO.InventarioyStock.Proveedor.Proveedor;
import com.IntegradorIO.InventarioyStock.Proveedor.ProveedorRepository;
import com.IntegradorIO.InventarioyStock.ProveedorArticulo.ProveedorArticulo;
import com.IntegradorIO.InventarioyStock.ProveedorArticulo.ProveedorArticuloRepository;
import com.IntegradorIO.InventarioyStock.Venta.dto.DTOTablaVentas;
import com.IntegradorIO.InventarioyStock.VentaArticulo.VentaArticulo;
import com.IntegradorIO.InventarioyStock.VentaArticulo.VentaArticuloRepository;
import com.IntegradorIO.InventarioyStock.Venta.dto.VentaArticuloRequest;
import com.IntegradorIO.InventarioyStock.Venta.dto.VentaRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.IntegradorIO.InventarioyStock.EstadoOrdenCompra.EstadoOrdencCompra;



import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VentaService {

    @Autowired private VentaRepository ventaRepository;
    @Autowired private VentaArticuloRepository ventaArticuloRepository;
    @Autowired private ArticuloRepository articuloRepository;
    @Autowired private ProveedorArticuloRepository proveedorArticuloRepository;
    @Autowired private OrdenCompraService ordenCompraService;
    @Autowired private ProveedorRepository proveedorRepository;



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


        // Guardar venta primero (porque los artículos tienen FK a venta)
        Venta ventaGuardada = ventaRepository.save(v);
        //ventaGuardada.setArticulos(lineas);

        //autegeneracio oc lf
        for (VentaArticulo va : lineas) {
            Articulo art = va.getArticulo();

            // 1. Verificar modelo de inventario
            if (art.getModeloInventario() != ModeloInventario.LOTE_FIJO) continue;
            System.out.println("Modelo del artículo: " + art.getModeloInventario());

            // 2. Verificar si el stock está en o por debajo del punto de pedido
            if (art.getStockActualArticulo() > art.getPuntoPedido()) continue;
            System.out.println("Stock actual: " + art.getStockActualArticulo() + " / Punto pedido: " + art.getPuntoPedido());

            boolean existeOC = false;
            System.out.println("Artículo cumple condiciones para evaluación de OC");
            try {
                List<DTOTablaOrdenCompra> listaOC = ordenCompraService.obtenerOrdenesCompra();

                existeOC = listaOC.stream()
                        .filter(oc -> oc.getEstadoOC() == EstadoOrdencCompra.PENDIENTE || oc.getEstadoOC() == EstadoOrdencCompra.ENVIADA)
                        .anyMatch(oc -> {
                            try {
                                DTOOrdenCompra ocDetallada = ordenCompraService.mostrarDatosOC(oc.getNroOrdenCompra());
                                return ocDetallada.getDetallesOC().stream()
                                        .anyMatch(det -> det.getCodArticulo() == art.getCodigoArticulo());
                            } catch (Exception e) {
                                System.err.println("No se pudo consultar OC " + oc.getNroOrdenCompra() + ": " + e.getMessage());
                                return false;
                            }

                        });


            } catch (Exception e) {
                System.err.println("Error al consultar las órdenes de compra: " + e.getMessage());
            }
            if (!existeOC) {
                // Buscar proveedor predeterminado para el artículo desde Proveedor → ProveedorArticulo
                Proveedor proveedor = proveedorRepository.findAll().stream()
                        .filter(p -> p.getProveedorArticulos().stream()
                                .anyMatch(pa ->
                                        pa.isEsPredeterminado() &&
                                                pa.getArticulo().getCodigoArticulo() == art.getCodigoArticulo()
                                )
                        )
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("No se encontró proveedor predeterminado para el artículo " + art.getCodigoArticulo()));

                // Crear detalle de la orden de compra
                DTODetalleOC detalleOC = new DTODetalleOC();
                detalleOC.setCodArticulo(art.getCodigoArticulo());
                detalleOC.setNombreArticulo(art.getNombreArticulo());


                Optional<ProveedorArticulo> proveedorArticuloOpt = proveedorArticuloRepository.findAll().stream()
                        .filter(pa -> pa.isEsPredeterminado() &&
                                pa.getArticulo().getCodigoArticulo() == art.getCodigoArticulo())
                        .findFirst();

                if (proveedorArticuloOpt.isPresent()) {
                    int eoq = proveedorArticuloOpt.get().getEoq();
                    detalleOC.setCantidadArticulo(eoq);
                } else {
                    throw new RuntimeException("No se encontró proveedor-artículo predeterminado para el artículo " + art.getCodigoArticulo());
                }


                detalleOC.setSuperaPuntoPedido(true);

                // Crear la orden de compra (DTO)
                DTOOrdenCompra nuevaOC = new DTOOrdenCompra();
                nuevaOC.setCodProveedor(proveedor.getCodigoProveedor());
                nuevaOC.setNombreOC("Reposición automática artículo " + art.getNombreArticulo());
                nuevaOC.setDetallesOC(List.of(detalleOC));
                System.out.println("✅ Se va a crear la OC automática");
                try {
                    ordenCompraService.crearOrdenCompra(nuevaOC);
                    System.out.println("✅ Orden de compra automática creada para artículo " + art.getCodigoArticulo());
                } catch (Exception e) {
                    System.err.println("❌ Error al crear la orden de compra automática: " + e.getMessage());
                }


            }

        }
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
