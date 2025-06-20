package com.IntegradorIO.InventarioyStock.OrdenCompra;

import com.IntegradorIO.InventarioyStock.Articulo.Articulo;
import com.IntegradorIO.InventarioyStock.Articulo.ArticuloRepository;
import com.IntegradorIO.InventarioyStock.Articulo.DTO.DTOTablaArticulos;
import com.IntegradorIO.InventarioyStock.Articulo.ModeloInventario;
import com.IntegradorIO.InventarioyStock.EstadoOrdenCompra.EstadoOrdenCompra;
import com.IntegradorIO.InventarioyStock.EstadoOrdenCompra.EstadoOrdenCompraRepository;
import com.IntegradorIO.InventarioyStock.EstadoOrdenCompra.EstadoOrdencCompra;
import com.IntegradorIO.InventarioyStock.OrdenCompra.DTO.*;
import com.IntegradorIO.InventarioyStock.OrdenCompraArticulo.OrdenCompraArticulo;
import com.IntegradorIO.InventarioyStock.OrdenCompraArticulo.OrdenCompraArticuloRepository;
import com.IntegradorIO.InventarioyStock.Proveedor.Proveedor;
import com.IntegradorIO.InventarioyStock.Proveedor.ProveedorRepository;
import com.IntegradorIO.InventarioyStock.ProveedorArticulo.ProveedorArticulo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrdenCompraService {
    @Autowired
    private OrdenCompraRepository ordenCompraRepository;
    @Autowired
    private EstadoOrdenCompraRepository estadoOrdenCompraRepository;
    @Autowired
    private OrdenCompraArticuloRepository ordenCompraArticuloRepository;
    @Autowired
    private ProveedorRepository proveedorRepository;


    @Autowired
    private ArticuloRepository articuloRepository;

    public List<DTOTablaOrdenCompra> obtenerOrdenesCompra() throws Exception {
        List<DTOTablaOrdenCompra> tablaOC = new ArrayList<>();
        try {
            List<OrdenCompra> ordenCompraList = ordenCompraRepository.findAll();
            for (OrdenCompra oc : ordenCompraList) {
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
            return tablaOC;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }


    }
    //BUSCAR ORDENES FINALIZADAS
    public List<DTOTablaOrdenCompra> obtenerOrdenesCompraFinalizadas() throws Exception {
        List<DTOTablaOrdenCompra> tablaOC = new ArrayList<>();
        try {
            List<OrdenCompra> ordenCompraList = ordenCompraRepository.findAll();
            for (OrdenCompra oc : ordenCompraList) {
                if (oc.getEstadoOrdenCompra().getNombreEstado()==EstadoOrdencCompra.FINALIZADO) {

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
            }
            return tablaOC;
        } catch (Exception e) {
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
   /* Metodo anterior que usaba el Nro de Orden de Compra (lo dejo por las dudas)
   public OrdenCompra crearOrdenCompra(DTOOrdenCompra dtoOC) throws Exception{

        EstadoOrdenCompra estadoOrdenCompra = new EstadoOrdenCompra();
        estadoOrdenCompra.setFechaHoraBajaEstadoOC(null);
        estadoOrdenCompra.setNombreEstado(EstadoOrdencCompra.PENDIENTE); //todas empiezan en pendiente
        estadoOrdenCompraRepository.save(estadoOrdenCompra);
        // Buscar proveedor por código
        Proveedor proveedor = proveedorRepository.findById(dtoOC.getCodProveedor())
                .orElseThrow(() -> new Exception("Proveedor no encontrado con ID: " + dtoOC.getCodProveedor()));


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
    }*/
    //metodo que agrego Caro para que ande el Alta de la OC
    public Map<String, Object> crearOrdenCompra(DTOOrdenCompra dtoOC) throws Exception {
        // Buscar proveedor por ID
        Optional<Proveedor> proveedorOptional = proveedorRepository.findById(dtoOC.getCodProveedor());
        if (proveedorOptional.isEmpty()) {
            throw new Exception("No se encontró el proveedor con código: " + dtoOC.getCodProveedor());
        }
        //Verificar que no exista una OC pendiente
        List<OrdenCompra> ocList = ordenCompraRepository.obtenerOrdenesCompra();
        for (OrdenCompra oca: ocList){
            if (oca.getEstadoOrdenCompra().getNombreEstado()==EstadoOrdencCompra.PENDIENTE) {//veo que este pendiente
                //si la orden esta pendiete, veo a que proveedor se la hace
               int codProv= oca.getProveedor().getCodigoProveedor();
                if (codProv==dtoOC.getCodProveedor()){ // si coincide el proveedor con el del dto
                    System.out.println("Ya existe una orden de compra PENDIENTE hacia el proveedor "+oca.getProveedor().getNombreProveedor());
                    throw new Exception("Ya existe una orden de compra PENDIENTE hacia el proveedor "+oca.getProveedor().getNombreProveedor()+"Modifique la orden número"+ oca.getNumeroOrdenCompra()+" para hacer el pedido");

                }
        }
        }



        Proveedor proveedor = proveedorOptional.get();

        // Crear estado "PENDIENTE"
        EstadoOrdenCompra estadoOC = new EstadoOrdenCompra();
        estadoOC.setNombreEstado(EstadoOrdencCompra.PENDIENTE);
        estadoOC.setFechaHoraBajaEstadoOC(null);
        estadoOrdenCompraRepository.save(estadoOC);

        // Crear la orden de compra
        OrdenCompra oc = new OrdenCompra();
        oc.setNombreOrdenCompra(dtoOC.getNombreOC());
        oc.setProveedor(proveedor);
        oc.setEstadoOrdenCompra(estadoOC);

        boolean advertencia = false;
        // Lista de detalles
        List<OrdenCompraArticulo> ocaList = new ArrayList<>();
        for (DTODetalleOC detalle : dtoOC.getDetallesOC()) {
            Articulo articulo = articuloRepository.obtenerArticulo(detalle.getCodArticulo());
            //llamada a funcion sugerencia
            //sugerirProveedorPredetertminado(articulo.getCodigoArticulo());
            // Validación si es lote fijo
            if (articulo.getModeloInventario() == ModeloInventario.LOTE_FIJO) {
                int cantidad = detalle.getCantidadArticulo();
                int stockTotal = articulo.getStockActualArticulo() + cantidad;
                if (stockTotal < articulo.getPuntoPedido()) {
                    advertencia = true; // ⚠️ Marcar advertencia, pero no interrumpir
                }
            }

            //validar que el articulo pertenezca a los del proveedor

           List<ProveedorArticulo> palist= proveedor.getProveedorArticulos(); //leo intemedias del proveedor seleccionado
           List <Articulo> artRelacionados = new ArrayList<>();
            for (ProveedorArticulo pa: palist){//por cada intermedia
                Articulo artRelacionadoProv = pa.getArticulo(); //leo el articulo de la intermedia
                if (artRelacionadoProv.getCodigoArticulo() == detalle.getCodArticulo()){ //comparo cod de intermedia y dto
                    artRelacionados.add(artRelacionadoProv);
                }
            }
            if (artRelacionados.isEmpty()){
                throw new Exception("No hay articulos relacionados para este proveedor");
            }

            // Crear relación artículo <-> orden
            OrdenCompraArticulo oca = new OrdenCompraArticulo();
            oca.setArticulo(articulo);
            oca.setCantidadOCA(detalle.getCantidadArticulo());
            oca.setFechaDesdeOCA(new Timestamp(System.currentTimeMillis()));
            oca.setFechaHastaOCA(null);
            ocaList.add(oca);
        }

        // Asociar lista de artículos a la orden
        oc.setListaOrdenCompraArticulo(ocaList);

        // Guardar la orden (se guarda todo por cascade)
        ordenCompraRepository.save(oc);

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("orden", oc);
        respuesta.put("advertencia", advertencia);
        return respuesta;
    }

    //con este bloque solo actualiza el codigo pero si cambiabas el nombre por ejemplo no (en vez de pedir caramelos, chocolates)
    //modificar la orden
    /*public OrdenCompra modificarOrdenCompra(int nroOrden,DTOOrdenCompra dtoOC) throws Exception{

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
    }*/
    //Modificacion Caro, para que se pudadiese modificar el articulo, y se actualicen bien los datos
    public OrdenCompra modificarOrdenCompra(int nroOrden, DTOOrdenCompra dtoOC) throws Exception {
        OrdenCompra ocModificada = obtenerOC(nroOrden);

        if (ocModificada.getEstadoOrdenCompra().getNombreEstado() != EstadoOrdencCompra.PENDIENTE) {
            throw new Exception("No se puede modificar la orden. Ya no se encuentra PENDIENTE");
        }

        // Cambiar nombre
        ocModificada.setNombreOrdenCompra(dtoOC.getNombreOC());

        // Eliminar detalles viejos
        List<OrdenCompraArticulo> detallesViejos = ocModificada.getListaOrdenCompraArticulo();
        for (OrdenCompraArticulo detalle : detallesViejos) {
            ordenCompraArticuloRepository.delete(detalle);
        }

        // Crear nuevos detalles
        List<OrdenCompraArticulo> nuevosDetalles = new ArrayList<>();
        for (DTODetalleOC detalleDTO : dtoOC.getDetallesOC()) {
            Articulo articulo = articuloRepository.obtenerArticulo(detalleDTO.getCodArticulo());

            OrdenCompraArticulo nuevoDetalle = new OrdenCompraArticulo();
            nuevoDetalle.setArticulo(articulo);
            nuevoDetalle.setCantidadOCA(detalleDTO.getCantidadArticulo());
            nuevoDetalle.setFechaDesdeOCA(new Timestamp(System.currentTimeMillis()));
            nuevoDetalle.setFechaHastaOCA(null);
            ordenCompraArticuloRepository.save(nuevoDetalle);
            nuevosDetalles.add(nuevoDetalle);
        }

        // Asociar nuevos detalles
        ocModificada.setListaOrdenCompraArticulo(nuevosDetalles);
        ordenCompraRepository.save(ocModificada);

        return ocModificada;
    }


    public DTOOrdenCompra mostrarDatosOC(int nroOrden) throws Exception {
        DTOOrdenCompra datosOrdenCompra = new DTOOrdenCompra();
        //buscar la orden
        OrdenCompra ordenCompra = obtenerOC(nroOrden);

        //lleno el dto
        datosOrdenCompra.setNroOrden(nroOrden);
        datosOrdenCompra.setNombreOC(ordenCompra.getNombreOrdenCompra());
        datosOrdenCompra.setCodProveedor(ordenCompra.getProveedor().getCodigoProveedor());
        List<OrdenCompraArticulo> listaDetalles = ordenCompra.getListaOrdenCompraArticulo();
        List<DTODetalleOC> detalleOCS = new ArrayList<>();

        for (OrdenCompraArticulo oca : listaDetalles) {
            DTODetalleOC detalleOC = new DTODetalleOC();
            detalleOC.setCantidadArticulo(oca.getCantidadOCA());
            detalleOC.setNombreArticulo(oca.getArticulo().getNombreArticulo());
            detalleOC.setCodArticulo(oca.getArticulo().getCodigoArticulo());

            detalleOCS.add(detalleOC);
        }
        datosOrdenCompra.setDetallesOC(detalleOCS);
        return datosOrdenCompra;
    }

    //sugerir proveedor predeterminado
    public DTOProveedorPredet sugerirProveedorPredetertminado(int codArticulo) {
        //busca los que estan activos
        List<Proveedor> proveedorList = proveedorRepository.findByActivoTrue();
        //por cada activo lee q articulo da
        for (Proveedor p :proveedorList){
            List<ProveedorArticulo> paList = p.getProveedorArticulos(); //para leer el articulo va por la intermedia
            for (ProveedorArticulo pa : paList){
                if (pa.getArticulo().getCodigoArticulo() == codArticulo) { //ve que sea el articulo seleccionado
                    if (pa.isEsPredeterminado()) { //mira que sea el predeterminado
                        DTOProveedorPredet proveedorPredet = new DTOProveedorPredet();
                        proveedorPredet.setNombreProveedorPredeterminado(p.getNombreProveedor());
                        proveedorPredet.setCodigoProveedor(p.getCodigoProveedor());
                        return  proveedorPredet;
                        //System.out.println("El proveedor prederterminado es " + proveedorPredet.getNombreProveedorPredeterminado()); //lanza un mensaje
                    }
                }
            }

        }
        return  null;
    }
    public List<DTOArticulosDelProveedor> ArticulosPorProveedor(int codProveedor){
        Optional<Proveedor> p = proveedorRepository.findById(codProveedor);
        List<ProveedorArticulo> paList= p.get().getProveedorArticulos();

        List<DTOArticulosDelProveedor> articuloList = new ArrayList<>();
        for (ProveedorArticulo pa:paList){
            DTOArticulosDelProveedor aDTO = new DTOArticulosDelProveedor();
            aDTO.setCodArticulo(pa.getArticulo().getCodigoArticulo());
            aDTO.setNombreArticulo(pa.getArticulo().getNombreArticulo());
            articuloList.add(aDTO);
        }
        return articuloList;

    }

    //filtrar proveedores por articulo y activos para desplegable
    public List<DTOProveedorPredet> filtrarProveedorParaArticulos(int codArticulo) {
        List<Proveedor> proveedorList = proveedorRepository.findByActivoTrue(); // que este dado de alta
        List<DTOProveedorPredet> listProveedoresPorArticulo = new ArrayList<>();
        //por cada activo lee q articulo da
        for (Proveedor p : proveedorList) {
            List<ProveedorArticulo> paList = p.getProveedorArticulos(); //para leer el articulo va por la intermedia
            for (ProveedorArticulo pa : paList) {
                if (pa.getArticulo().getCodigoArticulo() == codArticulo) { //verifica que le pertenezca el articulo
                    DTOProveedorPredet dtoProveedorPredet = new DTOProveedorPredet();
                    dtoProveedorPredet.setNombreProveedorPredeterminado(p.getNombreProveedor());
                    dtoProveedorPredet.setCodigoProveedor(p.getCodigoProveedor());
                    listProveedoresPorArticulo.add(dtoProveedorPredet);
                }
            }
        }
        return listProveedoresPorArticulo;
    }

    //sugerir lote



    //GESTION DE ESTADOS

    //cancelar solo si esta en estado pendiente
    public void cancelarOC(int nroOrden) throws Exception {
        OrdenCompra oc = obtenerOC(nroOrden); //buscarla por id
        EstadoOrdenCompra estadoActual = oc.getEstadoOrdenCompra();//buscar el estado actual relacionado
        EstadoOrdencCompra nombreEstado = estadoActual.getNombreEstado();

        if (nombreEstado == EstadoOrdencCompra.PENDIENTE) {
            estadoActual.setNombreEstado(EstadoOrdencCompra.CANCELADO);
            //se ponerle fecha baja
            estadoOrdenCompraRepository.save(estadoActual); // guardar cambio
        } else {
            throw new Exception("No se puede cancelar la orden porque está en estado:" + nombreEstado);
        }

    }

    //envio manual de la OC
    public void enviarOC(int nroOrden) throws Exception {
        OrdenCompra oc = obtenerOC(nroOrden); //buscarla por id
        EstadoOrdenCompra estadoActual = oc.getEstadoOrdenCompra(); //lee el estado actual
        if (estadoActual.getNombreEstado() == EstadoOrdencCompra.PENDIENTE) { //mira q sea pendiente
            //cambiar estado a enviada
            oc.getEstadoOrdenCompra().setNombreEstado(EstadoOrdencCompra.ENVIADA);
            estadoOrdenCompraRepository.save(estadoActual);
        } else {
            throw new Exception("Solo se puede cambiar a enviada si esta en estado PENDIENTE");
        }
    }

    //finalizar la OC

    public void finalizarOC(int nroOrden) throws Exception {
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


            // actualizar el inventario

           // int ingresoArticulos = oc.getCantidadOrdenCompra();
            //busco el articulo de la OC y ahí hago stockActual+ingresoArticulos, dsp guardo
            List<OrdenCompraArticulo> detallesListOC = oc.getListaOrdenCompraArticulo();
            for (OrdenCompraArticulo detalle : detallesListOC) {
                Articulo articuloPedido = detalle.getArticulo(); //busco el articulo de la OC
                int ingresoArticulos = detalle.getCantidadOCA();
                int stockActual = articuloPedido.getStockActualArticulo();//leo stock actual
                int nuevoStock = stockActual + ingresoArticulos; //calculo nuevo stock con el reingreso
                articuloPedido.setStockActualArticulo(nuevoStock); //se piso el stock viejo
                articuloRepository.save(articuloPedido);//guardo el cambio
            }

        }


    }
}





