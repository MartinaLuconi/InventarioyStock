package com.IntegradorIO.InventarioyStock.OrdenCompra;

import com.IntegradorIO.InventarioyStock.Articulo.Articulo;
import com.IntegradorIO.InventarioyStock.Articulo.ArticuloRepository;
import com.IntegradorIO.InventarioyStock.Articulo.DTO.DTOTablaArticulos;
import com.IntegradorIO.InventarioyStock.Articulo.ModeloInventario;
import com.IntegradorIO.InventarioyStock.EstadoOrdenCompra.EstadoOrdenCompra;
import com.IntegradorIO.InventarioyStock.EstadoOrdenCompra.EstadoOrdenCompraRepository;
import com.IntegradorIO.InventarioyStock.EstadoOrdenCompra.EstadoOrdencCompra;
import com.IntegradorIO.InventarioyStock.OrdenCompra.DTO.DTODetalleOC;
import com.IntegradorIO.InventarioyStock.OrdenCompra.DTO.DTOOrdenCompra;
import com.IntegradorIO.InventarioyStock.OrdenCompra.DTO.DTOTablaOrdenCompra;
import com.IntegradorIO.InventarioyStock.OrdenCompraArticulo.OrdenCompraArticulo;
import com.IntegradorIO.InventarioyStock.OrdenCompraArticulo.OrdenCompraArticuloRepository;
import com.IntegradorIO.InventarioyStock.ProveedorArticulo.ProveedorArticulo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrdenCompraService {
    @Autowired
    private OrdenCompraRepository ordenCompraRepository;
    @Autowired
    private  EstadoOrdenCompraRepository estadoOrdenCompraRepository;
    @Autowired
    private OrdenCompraArticuloRepository ordenCompraArticuloRepository;

    @Autowired
    private ArticuloRepository articuloRepository;
    public List<DTOTablaOrdenCompra> obtenerOrdenesCompra() throws Exception{
        List<DTOTablaOrdenCompra> tablaOC = new ArrayList<>();
        try{
            List<OrdenCompra> ordenCompraList = ordenCompraRepository.findAll();
            for (OrdenCompra oc : ordenCompraList){
                DTOTablaOrdenCompra dtoFilaTabla = new DTOTablaOrdenCompra();
                dtoFilaTabla.setNroOrdenCompra(oc.getNumeroOrdenCompra());
                dtoFilaTabla.setNombreOC(oc.getNombreOrdenCompra());
                dtoFilaTabla.setNombreProveedor(oc.getProveedor().getNombreProveedor());
                dtoFilaTabla.setEstadoOC(oc.getEstadoOrdenCompra().getNombreEstado());
                List<OrdenCompraArticulo> ocaList = oc.getListaOrdenCompraArticulo();
               //encuentra la orden actual relacionada
                    OrdenCompraArticulo oca = ocaList.stream()
                            .max(Comparator.comparing(OrdenCompraArticulo::getFechaDesdeOCA))
                            .orElse(null);
                    dtoFilaTabla.setFechaOrden(oca.getFechaDesdeOCA());
                    tablaOC.add(dtoFilaTabla);

            }
            return  tablaOC;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }


    }

    //buscar una orden de compra por codigo
    public OrdenCompra obtenerOC(int nroOrden) throws Exception {
        try {
            Optional<OrdenCompra> ordenCompraOptional = ordenCompraRepository.findById(nroOrden);
            return ordenCompraOptional.get();
        } catch (Exception e) {
            throw new Exception(e.getMessage()); //No se encontro un oc con ese codigo
        }
    }

    //alta OC
    public OrdenCompra crearOrdenCompra(DTOOrdenCompra dtoOC) throws Exception{

        EstadoOrdenCompra estadoOrdenCompra = new EstadoOrdenCompra();
        estadoOrdenCompra.setFechaHoraBajaEstadoOC(null);
        estadoOrdenCompra.setNombreEstado(EstadoOrdencCompra.PENDIENTE); //todas empiezan en pendiente
        estadoOrdenCompraRepository.save(estadoOrdenCompra);

        OrdenCompra oc = new OrdenCompra();
            oc.setNombreOrdenCompra(dtoOC.getNombreOC());
            oc.setNumeroOrdenCompra(dtoOC.getNroOrden());
            //verifica que no exista el codigo
            if(ordenCompraRepository.existsById(dtoOC.getNroOrden())){
                throw new Exception("Ya existe una orden de compra con este numero de orden");
            }
            oc.setEstadoOrdenCompra(estadoOrdenCompra); //relacion con estado

        List<OrdenCompraArticulo> ocaList = new ArrayList<>();
        List<DTODetalleOC> detallesOCList = dtoOC.getDetallesOC(); //aca vienen los datos de los articulos
        for (DTODetalleOC detalle : detallesOCList){
            //busco articulos que vienen en la orden de compra
            Articulo articulo = articuloRepository.obtenerArticulo(detalle.getCodArticulo());

            //verifica que sea modelo de lote fijo
            if (articulo.getModeloInventario()== ModeloInventario.LOTE_FIJO){
                //compara el stock con el punto de pedido, si no llega al PP, tira un error
                int cantidadPedidoOC = detalle.getCantidadArticulo();
                int stockTotal = articulo.getStockActualArticulo() + cantidadPedidoOC;
                if (stockTotal < articulo.getPuntoPedido()) { //compara con el punto de pedido
                    throw new Exception("La cantidad pedida no supera el punto de pedido del modelo lote fijo. Ingrese nuevamente la cantidad");
                }
            }

            //creo clase que tiene el detalle
            OrdenCompraArticulo ordenCompraArticulo = new OrdenCompraArticulo();
            ordenCompraArticulo.setArticulo(articulo); //reliono el articulo
           // ordenCompraArticulo.setOrdenCompra(oc); //relaciono con la OC
            ordenCompraArticulo.setCantidadOCA(detalle.getCantidadArticulo()); //lote
            ordenCompraArticulo.setFechaDesdeOCA(new Timestamp(System.currentTimeMillis())); //fecha de creación
            ordenCompraArticulo.setFechaHastaOCA(null);
            ocaList.add(ordenCompraArticulo);
            ordenCompraArticuloRepository.save(ordenCompraArticulo);
        }
            oc.setListaOrdenCompraArticulo(ocaList);
            ordenCompraRepository.save(oc);
        return  oc;
    }

    //modificar la orden
    public OrdenCompra modificarOrdenCompra(int nroOrden,DTOOrdenCompra dtoOC) throws Exception{

        OrdenCompra ocModificada = obtenerOC(nroOrden);

        if (ocModificada.getEstadoOrdenCompra().getNombreEstado()==EstadoOrdencCompra.PENDIENTE) { //solo modifica si esta pendiente

            ocModificada.setNombreOrdenCompra(dtoOC.getNombreOC());
            ocModificada.setNumeroOrdenCompra(dtoOC.getNroOrden()); //esto en realidad no se deberia

        List<OrdenCompraArticulo> detallesOCViejos= ocModificada.getListaOrdenCompraArticulo();
        for (OrdenCompraArticulo oca : detallesOCViejos) { //por cada detalle
            for (DTODetalleOC detalleOC : dtoOC.getDetallesOC()) {  //por cada dto que tenemos con los datos
                //pasar del dto  a la entidad
                if (detalleOC.getCodArticulo() == oca.getArticulo().getCodigoArticulo()) {
                    oca.setCantidadOCA(detalleOC.getCantidadArticulo());
                    oca.setArticulo(articuloRepository.obtenerArticulo(detalleOC.getCodArticulo()));
                    //oca.setOrdenCompra(ocModificada);
                    ordenCompraArticuloRepository.save(oca);
                }
            }

        }

            ordenCompraRepository.save(ocModificada);
        }else{
            throw new Exception("No se puede modificar la orden. Ya no se encuentra  PENDIENTE");
        }
        return  ocModificada;
    }

    public DTOOrdenCompra mostrarDatosOC(int nroOrden) throws Exception {
        DTOOrdenCompra datosOrdenCompra = new DTOOrdenCompra();
        //buscar la orden
        OrdenCompra ordenCompra = obtenerOC(nroOrden);

        //lleno el dto
        datosOrdenCompra.setNroOrden(nroOrden);
        datosOrdenCompra.setNombreOC(ordenCompra.getNombreOrdenCompra());
        List <OrdenCompraArticulo> listaDetalles = ordenCompra.getListaOrdenCompraArticulo();
        List <DTODetalleOC> detalleOCS=new ArrayList<>();
        for (OrdenCompraArticulo oca : listaDetalles){
            DTODetalleOC detalleOC = new DTODetalleOC();
            detalleOC.setCantidadArticulo(oca.getCantidadOCA());
            detalleOC.setNombreArticulo(detalleOC.getNombreArticulo());
            detalleOC.setCodArticulo(detalleOC.getCodArticulo());
            detalleOCS.add(detalleOC);
        }

        return datosOrdenCompra;
    }

    //sugerir proveedor predeterminado
    public void sugerirProveedorPredetertminado(){

    }

    //sugerir lote

    //GESTION DE ESTADOS

    //cancelar solo si esta en estado pendiente
    public void cancelarOC(int nroOrden) throws Exception {
        OrdenCompra oc = obtenerOC(nroOrden); //buscarla por id
            EstadoOrdenCompra estadoActual = oc.getEstadoOrdenCompra();//buscar el estado actual relacionado
            EstadoOrdencCompra nombreEstado= estadoActual.getNombreEstado() ;

            if (nombreEstado == EstadoOrdencCompra.PENDIENTE){
                estadoActual.setNombreEstado(EstadoOrdencCompra.CANCELADO);
                //se ponerle fecha baja
            }else {
                throw new Exception("No se puede cancelar la orden porque está en estado:"+nombreEstado);
            }

    }
    //envio manual de la OC
    public void enviarOC(int nroOrden)throws Exception{
        OrdenCompra oc = obtenerOC(nroOrden); //buscarla por id
        EstadoOrdenCompra estadoActual = oc.getEstadoOrdenCompra(); //lee el estado actual
        if (estadoActual.getNombreEstado() == EstadoOrdencCompra.PENDIENTE){ //mira q sea pendiente
            //cambiar estado a enviada
            oc.getEstadoOrdenCompra().setNombreEstado(EstadoOrdencCompra.ENVIADA);
            estadoOrdenCompraRepository.save(estadoActual);
        } else {
            throw new Exception("Solo se puede cambiar a enviada si esta en estado PENDIENTE");
        }
    }

    //finalizar la OC

    public void finalizarOC(int nroOrden) throws Exception{
        OrdenCompra oc = obtenerOC(nroOrden); //buscarla por id
        EstadoOrdenCompra estadoActual = oc.getEstadoOrdenCompra(); //lee el estado actual

        //VERIFICAR QUE SE ENVIO
        if (estadoActual.getNombreEstado() == EstadoOrdencCompra.ENVIADA) {
            estadoActual.setNombreEstado(EstadoOrdencCompra.FINALIZADO); //cambio a finalizado
            estadoOrdenCompraRepository.save(estadoActual);
            List<OrdenCompraArticulo> ocaList = oc.getListaOrdenCompraArticulo();
            OrdenCompraArticulo ocaActual = ocaList.stream()
                    .max(Comparator.comparing(OrdenCompraArticulo::getFechaDesdeOCA))
                    .orElse(null);
            ocaActual.setFechaHastaOCA(new Timestamp(System.currentTimeMillis())); //le ponemos fin fecha hasta
            ordenCompraArticuloRepository.save(ocaActual);
        }
        // actualizar el inventario

        int ingresoArticulos= oc.getCantidadOrdenCompra();
        //busco el articulo de la OC y ahí hago stockActual+ingresoArticulos, dsp guardo
        List< OrdenCompraArticulo > detallesListOC = oc.getListaOrdenCompraArticulo();
        for (OrdenCompraArticulo detalle :detallesListOC ){
            Articulo articuloPedido = detalle.getArticulo(); //busco el articulo de la OC
            int stockActual= articuloPedido.getStockActualArticulo();//leo stock actual
            int nuevoStock= stockActual+ingresoArticulos; //calculo nuevo stock con el reingreso
            detalle.getArticulo().setStockActualArticulo(nuevoStock); //se piso el stock viejo
            articuloRepository.save(articuloPedido);//guardo el cambio
        }



    }



}
