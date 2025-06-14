package com.IntegradorIO.InventarioyStock.Articulo;

import com.IntegradorIO.InventarioyStock.Articulo.DTO.DTODetalleArticulo;
import com.IntegradorIO.InventarioyStock.Articulo.DTO.DTOModificarArticulo;
import com.IntegradorIO.InventarioyStock.Articulo.DTO.DTONuevoArticulo;
import com.IntegradorIO.InventarioyStock.Articulo.DTO.DTOTablaArticulos;
import com.IntegradorIO.InventarioyStock.EstadoOrdenCompra.EstadoOrdenCompraRepository;
import com.IntegradorIO.InventarioyStock.EstadoOrdenCompra.EstadoOrdencCompra;
import com.IntegradorIO.InventarioyStock.EstrategiaDeRevisionP.CGIModelP;
import com.IntegradorIO.InventarioyStock.EstrategiaDeRevisión.CGIModel;
import com.IntegradorIO.InventarioyStock.EstrategiaDeRevisión.CalculosEstrRevisionContinua;
import com.IntegradorIO.InventarioyStock.Proveedor.Proveedor;
import com.IntegradorIO.InventarioyStock.ProveedorArticulo.ProveedorArticulo;
import com.IntegradorIO.InventarioyStock.ProveedorArticulo.ProveedorArticuloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticuloService  {
    @Autowired
    private ArticuloRepository articuloRepository;

    @Autowired
    private ProveedorArticuloRepository proveedorArticuloRepository;

    @Autowired
    private EstadoOrdenCompraRepository estadoOrdenCompraRepository;

    //lista los articulos
    public List<DTOTablaArticulos> obtenerArticulos() throws Exception {
        try {
            List<Articulo> articuloStock = articuloRepository.findAll();
            return articuloStock.stream()
                    .map(DTOTablaArticulos::new)
                    .collect(Collectors.toList());

        }catch (Exception e){
           // System.out.println("No se encontraron artículos");
           // return null;
            throw new Exception(e.getMessage());
        }
    }

    //busca un articulo en particular
    public Articulo obtenerArticulo(int codigoArticulo) throws Exception{
        try {
            Optional<Articulo> articuloOptional = articuloRepository.findById(codigoArticulo);
            return articuloOptional.get(); //lo pone como art.get();
        }catch (Exception e){
            throw new Exception(e.getMessage()); //No se encontro un articulo con ese codigo
        }

    }

    //mostrar detalle Articulo
    public DTODetalleArticulo mostrarDetalle(int codigoArticulo){
        Articulo articuloEncontrado = articuloRepository.obtenerArticulo(codigoArticulo);
        //List<ProveedorArticulo> paeList = articuloEncontrado.getProveedorArticuloList();
        //ProveedorArticulo pae = paeList.stream()
         //       .max(Comparator.comparing(ProveedorArticulo::getFechaDesdePA))
         //       .orElse(null);
        DTODetalleArticulo dtoMostrar = new DTODetalleArticulo();
                dtoMostrar.setNombreArticulo(articuloEncontrado.getNombreArticulo());
                dtoMostrar.setDescripcion(articuloEncontrado.getDescripcion());
                dtoMostrar.setStockReal(articuloEncontrado.getStockActualArticulo());
                dtoMostrar.setStockSeguridad(articuloEncontrado.getStockSeguridadArticulo());
                dtoMostrar.setDemandaAnual(articuloEncontrado.getDemandaAnual());
                dtoMostrar.setCostoAlmacenamiento(articuloEncontrado.getCostoAlmacenamiento());
                dtoMostrar.setModeloElegido(articuloEncontrado.getModeloInventario());
                dtoMostrar.setPuntoPedido(articuloEncontrado.getPuntoPedido());

                //dtoMostrar.setDemoraEntrega(pae.getDemoraEntrega());
               // dtoMostrar.setInventarioMax(pae.getInventarioMaximo());
                dtoMostrar.setDesviacionEstandar(articuloEncontrado.getDesviacionEstandar());
               // dtoMostrar.setPrecioUnitario(pae.getCostoUnitario());
        return dtoMostrar;
    }

    public Articulo guardarArticulo(DTONuevoArticulo dtoNuevoArticulo) throws Exception {
        try {
            // paso datos del dto a la entidad Articulo
            Articulo articulo = new Articulo();
            articulo.setDescripcion(dtoNuevoArticulo.getDescripcion());
            articulo.setNombreArticulo(dtoNuevoArticulo.getNombreArticulo());
            articulo.setStockActualArticulo(dtoNuevoArticulo.getStockReal());
            articulo.setFechaHoraBajaArticulo(null);
            articulo.setStockSeguridadArticulo(dtoNuevoArticulo.getStockSeguridad());
            articulo.setPuntoPedido(dtoNuevoArticulo.getPuntoPedido());
            articulo.setModeloInventario(dtoNuevoArticulo.getModeloElegido());
            articulo.setDemandaAnual(dtoNuevoArticulo.getDemandaAnual());
            articulo.setDesviacionEstandar(dtoNuevoArticulo.getDesviacionEstandar());

            // guardo primero el artículo para que tenga ID
            articuloRepository.save(articulo);

            // por cada proveedor asignado, crear y guardar una instancia ProveedorArticulo
            List<Proveedor> proveedorList = dtoNuevoArticulo.getProveedoresAsignados();
            for (Proveedor p : proveedorList) {
                ProveedorArticulo pa = new ProveedorArticulo(); // CREAR nueva instancia aquí
                pa.setArticulo(articulo);
                pa.setProveedor(p);

                pa.setCostoPedido(dtoNuevoArticulo.getCostoPedido());
                pa.setPrecioUnitProveedorArticulo(dtoNuevoArticulo.getPrecioUnitario());
                pa.setDemoraEntrega(dtoNuevoArticulo.getDemoraEntrega());
                pa.setCostoMantenimiento(dtoNuevoArticulo.getCostoMantener());
                pa.setCostoAlmacenamiento(dtoNuevoArticulo.getCostoAlmacenamiento());
                pa.setLoteOptimo(dtoNuevoArticulo.getLoteOptimo());
                pa.setInventarioMaximo(dtoNuevoArticulo.getInventarioMax());
                pa.setFechaDesdePA(new Timestamp(System.currentTimeMillis()));

                proveedorArticuloRepository.save(pa);
            }

            return articulo;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


    // Modificar un artículo existente
    public Articulo modificarArticulo(int codigoArticulo, DTOModificarArticulo articuloModificado) throws Exception{

        try {
            Articulo articulo = articuloRepository.obtenerArticulo(codigoArticulo);

            articulo.setNombreArticulo(articuloModificado.getNombreArticulo());
            articulo.setDescripcion(articuloModificado.getDescripcion());
            articulo.setStockActualArticulo(articuloModificado.getStockReal());
            articulo.setStockSeguridadArticulo(articuloModificado.getStockSeguridad());
            articulo.setPuntoPedido(articuloModificado.getPuntoPedido());
            articulo.setModeloInventario(articuloModificado.getModeloElegido());
            articulo.setDemandaAnual(articuloModificado.getDemandaAnual());
            articulo.setDesviacionEstandar(articuloModificado.getDesviacionEstandar());
            articulo.setCostoAlmacenamiento(articuloModificado.getCostoAlmacenamiento());
            articuloRepository.save(articulo);

            return articulo;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }




    public boolean eliminarArticulo(int codigoArticulo) throws Exception {
        try {
            Optional<Articulo> articuloOptional = articuloRepository.findById(codigoArticulo);
            if (articuloOptional.isPresent()) {
                Articulo articulo = articuloOptional.get();

                // Verificar órdenes de compra pendientes o enviadas
                boolean tieneOrdenPendienteOEnviada = estadoOrdenCompraRepository
                        .existsByArticuloAndNombreEstadoIn(articulo, List.of(EstadoOrdencCompra.PENDIENTE, EstadoOrdencCompra.ENVIADA));
                if (tieneOrdenPendienteOEnviada) {
                    throw new Exception("No se puede dar de baja el artículo porque tiene órdenes de compra pendientes o enviadas.");
                }

                // Verificar stock
                if (articulo.getStockActualArticulo() > 0) {
                    throw new Exception("No se puede dar de baja el artículo porque tiene stock disponible.");
                }

                // Realizar baja lógica
                articulo.setEstadoArticulo(EstadoArticulo.NO_DISPONIBLE);
                articulo.setFechaHoraBajaArticulo(new java.sql.Timestamp(System.currentTimeMillis()));
                articuloRepository.save(articulo);
                return true;
            } else {
                throw new Exception("El artículo con el código " + codigoArticulo + " no existe.");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    //listar proveedores de un articulo

    public List<Proveedor> listarProveedores(int codArticulo) throws Exception{
       // try {

            List<Proveedor> listaProveedores = new ArrayList<>();

            //busco el articulo
        //    Articulo a = articuloRepository.obtenerArticulo(codArticulo);

            //leo intermedias de ese articulo
         //   List<ProveedorArticulo> palist = a.getProveedorArticuloList();
         //   for (ProveedorArticulo pa : palist) {
                //meto proveedores en lista
          //      listaProveedores.add(pa.getProveedor());
         //   }

            return listaProveedores;
       // }catch (Exception e){
      //      throw new Exception(e.getMessage());
      //  }
    }

    //listar productos faltantes

    public List<DTOTablaArticulos> listarArticulosFaltantes () throws Exception{
        List<Articulo> articulosFaltantes = new ArrayList<>();
        List<DTOTablaArticulos> articulosFaltantesDTO = new ArrayList<>();
        //busco todos los articulos
        List<Articulo> aList =articuloRepository.obtenerArticulos();
        for (Articulo a : aList){
           int stockActual = a.getStockActualArticulo();
           int stockSerguridad = a.getStockSeguridadArticulo();
           if (stockActual < stockSerguridad ){
               articulosFaltantes.add(a);
           }
        }
        //Caso de que no hay articulos faltantes
        if (articulosFaltantes.isEmpty()){
            throw new Exception("No hay artículos faltantes");
        } else {
            for (Articulo a : articulosFaltantes) {
                DTOTablaArticulos dto = new DTOTablaArticulos(a);
                dto.setCodigoArticulo(a.getCodigoArticulo());
                dto.setNombreArticulo(a.getNombreArticulo());
                dto.setDescripcion(a.getDescripcion());
                dto.setFechaHoraBajaArticulo(a.getFechaHoraBajaArticulo());
                articulosFaltantesDTO.add(dto);
            }
        }
        return articulosFaltantesDTO;

    }

    //listar productos a reponer

    public List<DTOTablaArticulos> listarArticulosReponer () throws Exception{
        List<Articulo> articulosReponerL = new ArrayList<>();
        List<DTOTablaArticulos> articulosReponerDTO = new ArrayList<>();
        //busco todos los articulos
        List<Articulo> aList =articuloRepository.obtenerArticulos();

        //armo lista de articulos a reponer
        for (Articulo a: aList) {
            //leer el stock actual
            int stockA = a.getStockActualArticulo();
            //lee el punto de pedido
            int pp = a.getPuntoPedido();
            //si el stock es menor al punto de pedido --> por debajo del PP
            if (stockA<=pp){
                articulosReponerL.add(a);
            }
        }

        //armo tabla con lista. Esto tmb pordría ir en el if anterior
        for (Articulo a : articulosReponerL) {
            DTOTablaArticulos dto = new DTOTablaArticulos(a);
            dto.setCodigoArticulo(a.getCodigoArticulo());
            dto.setNombreArticulo(a.getNombreArticulo());
            dto.setDescripcion(a.getDescripcion());
            dto.setFechaHoraBajaArticulo(a.getFechaHoraBajaArticulo());
            articulosReponerDTO.add(dto);
        }

        return articulosReponerDTO;
    }

// Para calcular el (CGI) (ROP) y StockSeguridad para un artículo y su proveedor Modeelo LOTE_FIJO








    public double calcularCGIArticulo(Articulo articulo, ProveedorArticulo proveedorArticulo, int periodoRevision, int demoraEntrega, double desviacionEstandar, double Z) {
        if (articulo.getModeloInventario() == ModeloInventario.LOTE_FIJO) {
            CGIModel model = new CGIModel();
            model.setDemandaAnual(articulo.getDemandaAnual());
            model.setCostoPedido(proveedorArticulo.getCostoPedido());
            model.setCostoUnitario(proveedorArticulo.getCostoUnitario());
            model.setCostoMantenimiento(proveedorArticulo.getCostoMantenimiento());
            model.setCostoAlmacenamiento(articulo.getCostoAlmacenamiento());
            return model.getCGI();
        } else if (articulo.getModeloInventario() == ModeloInventario.TIEMPO_FIJO) {
            CGIModelP modelP = new CGIModelP();
            modelP.setDemandaAnual(articulo.getDemandaAnual());
            modelP.setPeriodoRevision(periodoRevision);
            modelP.setDemoraEntrega(demoraEntrega);
            modelP.setDesviacionEstandar(desviacionEstandar);
            modelP.setZ(Z);
            modelP.setCostoPedido(proveedorArticulo.getCostoPedido());
            modelP.setCostoUnitario(proveedorArticulo.getCostoUnitario());
            modelP.setCostoMantenimiento(proveedorArticulo.getCostoMantenimiento());
            // Puedes setear otros parámetros si es necesario
            return modelP.getCGI();
        } else {
            throw new IllegalArgumentException("Modelo de inventario no soportado");

        }
    }

        public int calcularEOQArticulo(Articulo articulo, ProveedorArticulo proveedorArticulo) {
        return CalculosEstrRevisionContinua.calcularEOQ(
                articulo.getDemandaAnual(),
                proveedorArticulo.getCostoPedido(),
                articulo.getCostoAlmacenamiento()
        );
    }

    public int calcularStockSeguridad(double Z, double desviacionEstandar, int L) {
        return CalculosEstrRevisionContinua.calcularStockSeguridad(Z, desviacionEstandar, L);
    }

    public int calcularROP(double demandaPromedio, int stockSeguridad, int L) {
        return CalculosEstrRevisionContinua.calcularROP(demandaPromedio, stockSeguridad, L);
    }


}
