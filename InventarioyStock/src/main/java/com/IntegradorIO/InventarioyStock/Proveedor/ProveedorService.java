package com.IntegradorIO.InventarioyStock.Proveedor;

import com.IntegradorIO.InventarioyStock.Articulo.Articulo;
import com.IntegradorIO.InventarioyStock.Articulo.ArticuloRepository;
import com.IntegradorIO.InventarioyStock.Articulo.ModeloInventario;
import com.IntegradorIO.InventarioyStock.EstadoOrdenCompra.EstadoOrdencCompra;
import com.IntegradorIO.InventarioyStock.EstrategiaDeRevisionPeriodica.CalculoServiceP;
import com.IntegradorIO.InventarioyStock.EstrategiaDeRevisiónContinua.CalculoService;
import com.IntegradorIO.InventarioyStock.OrdenCompra.OrdenCompra;
import com.IntegradorIO.InventarioyStock.OrdenCompra.OrdenCompraRepository;
import com.IntegradorIO.InventarioyStock.Proveedor.dto.*;
import com.IntegradorIO.InventarioyStock.ProveedorArticulo.ProveedorArticulo;
import com.IntegradorIO.InventarioyStock.ProveedorArticulo.ProveedorArticuloRepository;
import com.IntegradorIO.InventarioyStock.ProveedorArticulo.ProveedorArticuloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

import java.sql.Timestamp;

@Service
public class ProveedorService {

    @Autowired private ProveedorRepository proveedorRepository;
    @Autowired private ProveedorArticuloRepository proveedorArticuloRepository;
    @Autowired private ArticuloRepository articuloRepository;
    @Autowired private OrdenCompraRepository ordenCompraRepository;



    @Autowired
    private CalculoServiceP calculoServiceP;

    @Autowired
    private CalculoService calculoService;

    /** Listar solo proveedores activos */
    public List<DTOTablaProveedor> obtenerProveedores() {
        return proveedorRepository.findAll().stream()
                .map(DTOTablaProveedor::new)
                .collect(Collectors.toList());
    }


    //encuentra solo proveedor activo
    public Optional<Proveedor> obtenerProveedor(Integer codigoProveedor) {
        return proveedorRepository.findById(codigoProveedor)
                .filter(Proveedor::isActivo);
    }

    public Proveedor guardarProveedor(DTONuevoProveedor dto) throws Exception{
        // 1) Convertir el dto en entidad Proveedor
        Proveedor entidad = new Proveedor();
        entidad.setNombreProveedor(dto.getNombreProveedor());
        //verificar que no hay un proveedor que se llame igual
        List<Proveedor> proveedorList=proveedorRepository.findAll();
        for (Proveedor p : proveedorList){
           String nombreExistenteProveedor= p.getNombreProveedor();
           if (Objects.equals(dto.getNombreProveedor(), nombreExistenteProveedor)){
               throw new Exception("El proveedor "+nombreExistenteProveedor+" ya existe. Ingrese otro nombre");
           }

        }
        entidad.setActivo(true);
        //verificar que solo uno sea predeterminado

        proveedorRepository.save(entidad);

        // 3) Si el dto trae asociaciones a artículos, crearlas ahora

        List<ProveedorArticulo> proveedorArticuloList = new ArrayList<>();
        List<DTODetalleProveedorArticulo> detallesList = dto.getAsociaciones();
            for (DTODetalleProveedorArticulo detalle :detallesList ) {

                // 3.1) Buscar el Artículo correspondiente
                Articulo art = articuloRepository.obtenerArticulo(detalle.getCodigoArticulo());


                // 3.2) Crear y poblar la entidad ProveedorArticulo
                ProveedorArticulo pa = new ProveedorArticulo();
                pa.setArticulo(art); //relacion a articulo
                pa.setDemoraEntrega(detalle.getDemoraEntrega());
                pa.setCostoUnitario(detalle.getPrecioUnitProveedorArticulo());
                pa.setCostoPedido(detalle.getCostoPedido());
                pa.setEsPredeterminado(false);
                pa.setFechaDesdePA(new Timestamp(System.currentTimeMillis()));
                pa.setFechaHastaPA(null);
                pa.setLoteOptimo(detalle.getLoteOptimo());
                pa.setCostoMantenimiento(detalle.getCostoMantenimiento());
                pa.setNivelDeServicio(detalle.getNivelDeServicio());
                pa.setPeriodoRevision(detalle.getPeriodoRevision());
                pa.setInventarioMaximo(detalle.getInventarioMaximo());
                proveedorArticuloList.add(pa);


                proveedorArticuloRepository.save(pa);
                // Selecciona el servicio según el modelo de inventario y reclacula los valores necesarios antes de guardar
                ModeloInventario modelo = art.getModeloInventario();
                if (modelo == ModeloInventario.LOTE_FIJO) {
                    // Calcular y asignar valores específicos para la estrategia de revisión continua
                    calculoService.recalcularYActualizar(pa.getArticulo());
                } else if (modelo == ModeloInventario.TIEMPO_FIJO) {
                    // Calcular y asignar valores específicos para la estrategia de revisión periódica
                    calculoServiceP.recalcularYActualizar(pa.getArticulo());
                }

            }
            entidad.setProveedorArticulos(proveedorArticuloList);

            proveedorRepository.save(entidad);


            // 4) Devolver el proveedor ya persistido (con su ID y sus asociaciones)
            return entidad;

    }


    public Proveedor modificarProveedor(Integer codigoProveedor, DTOModificarProveedor dto) {
        // 1) Recuperar proveedor
        Proveedor existente = proveedorRepository.findById(codigoProveedor)
                .filter(Proveedor::isActivo)
                .orElseThrow(() -> new IllegalArgumentException("Proveedor no encontrado o inactivo"));

        // 2) Actualizar datos básicos
        existente.setNombreProveedor(dto.getNombreProveedor());
        // (Si tuvieras más campos en Proveedor, asignarlos aquí)

        // 3) Cargar asociaciones actuales desde BD
        List<ProveedorArticulo> asociadasActuales = existente.getProveedorArticulos();
               // proveedorArticuloRepository.findByProveedorCodigoProveedor(codigoProveedor);

        // 4) Construir un mapa para “buscar por código de artículo” rápidamente
        //    Clave: codigoArticulo; Valor: ProveedorArticulo existente
        Map<Integer, ProveedorArticulo> mapaActuales = new HashMap<>();
        for (ProveedorArticulo pa : asociadasActuales) {
            mapaActuales.put(pa.getArticulo().getCodigoArticulo(), pa);
        }

        // 5) Procesar la lista que viene en el DTO
        //    Creamos una lista donde iremos guardando (o actualizando) las asociaciones finales
        List<ProveedorArticulo> listaFinal = new ArrayList<>();
        List<DTODetalleProveedorArticulo> detalleList = dto.getAsociaciones();
        for (DTODetalleProveedorArticulo detalle : detalleList) {
            Integer codigoArt = detalle.getCodigoArticulo();

            // 5a) Chequeo que el Artículo exista en BD
            Articulo art = articuloRepository.findById(codigoArt)
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Artículo no encontrado: " + codigoArt));

            if (mapaActuales.containsKey(codigoArt)) {
                // 5b) Ya existía esa asociación en BD: la “actualizo”
                ProveedorArticulo paExistente = mapaActuales.get(codigoArt);

                paExistente.setDemoraEntrega(detalle.getDemoraEntrega());
                paExistente.setCostoUnitario(detalle.getPrecioUnitProveedorArticulo());
                paExistente.setCostoPedido(detalle.getCostoPedido());
                paExistente.setCostoMantenimiento(detalle.getCostoMantenimiento());
                paExistente.setPeriodoRevision(detalle.getPeriodoRevision());
                paExistente.setLoteOptimo(detalle.getLoteOptimo());
                paExistente.setNivelDeServicio(detalle.getNivelDeServicio());
                paExistente.setInventarioMaximo(detalle.getInventarioMaximo());
                //paExistente.setEsPredeterminado(detalle.isEsPredeterminado());

                // La guardamos en la lista final para no borrarla posteriormente
                listaFinal.add(paExistente);

                // Marcamos que ya procesamos esta asociación, para que no sea eliminada
                mapaActuales.remove(codigoArt);

            } else {
                // 5c) No existía: creamos un nuevo ProveedorArticulo
                ProveedorArticulo nuevo = new ProveedorArticulo();
                nuevo.setArticulo(art);
                nuevo.setFechaDesdePA(new Timestamp(System.currentTimeMillis()));
                nuevo.setDemoraEntrega(detalle.getDemoraEntrega());
                nuevo.setCostoUnitario(detalle.getPrecioUnitProveedorArticulo());
                nuevo.setCostoPedido(detalle.getCostoPedido());
                nuevo.setNivelDeServicio(detalle.getNivelDeServicio());
                nuevo.setLoteOptimo(detalle.getLoteOptimo());
                nuevo.setPeriodoRevision(detalle.getPeriodoRevision());
                nuevo.setCostoMantenimiento(detalle.getCostoMantenimiento());
                nuevo.setInventarioMaximo(detalle.getInventarioMaximo());
                nuevo.setEsPredeterminado(false);

                listaFinal.add(nuevo);
            }
        }

        // 6) Aquellos que quedaron en mapaActuales eran asociaciones antiguas
        //    que ya no aparecen en el DTO: las debemos eliminar
        //    (o dar de baja lógica, según tu modelo). Aquí las borramos físicamente:
        if (!mapaActuales.isEmpty()) {
            proveedorArticuloRepository.deleteAll(mapaActuales.values());
        }

        // 7) Guardar todas las asociaciones “nuevas y actualizadas”
        //    Como las instancias pueden ser nuevas o modificadas, saveAll las persiste correctamente.
        proveedorArticuloRepository.saveAll(listaFinal);

        // 8) Asignar la lista final al proveedor (opcional si tu relación es bidireccional)
        existente.setProveedorArticulos(listaFinal);

        // 9) Finalmente salvar el proveedor con sus cambios
        return proveedorRepository.save(existente);
    }


    /**
     * Alta de proveedor con asociación mínima.
     * @throws IllegalArgumentException si no viene ninguna asociación.
     */
    public Proveedor guardarConAsociaciones(DTONuevoProveedor req) {
        if (req.getAsociaciones() == null || req.getAsociaciones().isEmpty()) {
            throw new IllegalArgumentException("Debe proporcionar al menos una asociación con artículo");
        }


        // 2) Crear asociaciones
        List<ProveedorArticulo> lista = new ArrayList<>();
        for (DTODetalleProveedorArticulo paReq : req.getAsociaciones()) {
            Articulo art = articuloRepository.findById(paReq.getCodigoArticulo())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Artículo no encontrado: " + paReq.getCodigoArticulo()));

            ProveedorArticulo pa = new ProveedorArticulo();
            pa.setArticulo(art);
            pa.setDemoraEntrega(paReq.getDemoraEntrega());
            pa.setCostoUnitario(paReq.getPrecioUnitProveedorArticulo());
            pa.setCostoPedido(paReq.getCostoPedido());
            pa.setFechaDesdePA(new Timestamp(System.currentTimeMillis()));
            pa.setEsPredeterminado(false);
            pa.setCostoMantenimiento(pa.getCostoMantenimiento());
            pa.setNivelDeServicio(paReq.getNivelDeServicio());
            pa.setPeriodoRevision(paReq.getPeriodoRevision());
            pa.setLoteOptimo(paReq.getLoteOptimo());
            pa.setInventarioMaximo(pa.getInventarioMaximo());

            lista.add(pa);
        }
        proveedorArticuloRepository.saveAll(lista);

        // 1) Crear Proveedor
        Proveedor p = new Proveedor();
        p.setNombreProveedor(req.getNombreProveedor());
        p.setActivo(true);
        p.setProveedorArticulos(lista);
        Proveedor guardado = proveedorRepository.save(p);


        return guardado;
    }

    /**
     * Baja lógica de proveedor con validaciones:
     * - No permitir si es predeterminado en un artículo.
     * - No permitir si hay órdenes PENDIENTE o CONFIRMADO.
     */
    public void bajaProveedor(Integer codigoProveedor) {
        Proveedor proveedor = proveedorRepository.findById(codigoProveedor)
                .orElseThrow(() -> new IllegalArgumentException("Proveedor no encontrado o ya inactivo"));
        if (!proveedor.isActivo()){
            throw new IllegalArgumentException("El proveedor ya está inactivo");
        }

        // Validación: verificar si es predeterminado en algún artículo
        boolean esPredeterminadoEnAlguno = proveedor.getProveedorArticulos()
                .stream()
                .anyMatch(ProveedorArticulo::isEsPredeterminado);

        if (esPredeterminadoEnAlguno) {
            throw new ResponseStatusException
           ( HttpStatus.CONFLICT,
                    "No se puede dar de baja: proveedor predeterminado en algún artículo");
        }
        //1) Verificar predeterminado
       /* ProveedorArticulo pred = proveedorArticuloRepository
       List<ProveedorArticulo> pred = proveedorArticuloRepository
                .findByProveedorCodigoProveedorAndEsPredeterminadoTrue(codigoProveedor);*/

        //if (pred != null) {
       /* List<ProveedorArticulo> paList = proveedor.getProveedorArticulos();
        for (ProveedorArticulo pa : paList){
            if (pa.isEsPredeterminado()){ //verifica q no sea predeterminado
                // if (!pred.isEmpty()) {
                throw new IllegalStateException(
                        "No se puede dar de baja: proveedor predeterminado en un artículo"
                );
            }
        } */


        // 2) Verificar órdenes activas (PENDIENTE o CONFIRMADO)
       /* boolean tieneOrdenesActivas = ordenCompraRepository
                .existsByProveedorCodigoProveedorAndEstadoOrdenCompraIn(
                        codigoProveedor,
                        List.of(EstadoOrdencCompra.PENDIENTE, EstadoOrdencCompra.CONFIRMADO)
                );


        if (tieneOrdenesActivas) {
            throw new IllegalStateException(
                    "No se puede dar de baja: existen Órdenes de Compra pendientes o confirmadas"
            );
        }*/

        List<OrdenCompra> ocList = ordenCompraRepository.obtenerOrdenesCompra();
        List<OrdenCompra> ocPendientesConfirmadas=new ArrayList<>();
        for (OrdenCompra oc: ocList){
            if (oc.getProveedor().getCodigoProveedor() ==proveedor.getCodigoProveedor()){
                EstadoOrdencCompra estadoActual = oc.getEstadoOrdenCompra().getNombreEstado();
                if (estadoActual==EstadoOrdencCompra.PENDIENTE || estadoActual==EstadoOrdencCompra.CONFIRMADO){
                    ocPendientesConfirmadas.add(oc); //armar una lista con las ordenes P o C
                }
            }

        }

        if (!ocPendientesConfirmadas.isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "No se puede dar de baja: existen Órdenes de Compra pendientes o confirmadas"
            );
        }

        ProveedorArticulo ultimaIntermedia = proveedor.getProveedorArticulos().stream()
                .max(Comparator.comparing(ProveedorArticulo::getFechaDesdePA))
                .orElse(null);

        ultimaIntermedia.setFechaHastaPA(new Timestamp(System.currentTimeMillis()));
       proveedorArticuloRepository.save(ultimaIntermedia);
        // 3) Marcar baja lógica
        proveedor.setActivo(false);
        proveedor.setFechaHoraBajaProveedor(new Timestamp(System.currentTimeMillis()));
        proveedorRepository.save(proveedor);



    }

    /** Listar asociaciones Proveedor–Artículo por proveedor */
    //El front espera un dto
    public List<DTODetalleProveedorArticulo> obtenerArticulosPorProveedor(Integer codigoProveedor) {
        List<DTODetalleProveedorArticulo> dtoList = new ArrayList<>();

        Proveedor proveedor = proveedorRepository.findById(codigoProveedor)
                .filter(Proveedor::isActivo)
                .orElseThrow(() -> new IllegalArgumentException("Proveedor no encontrado o inactivo"));

        List<ProveedorArticulo> paList = proveedor.getProveedorArticulos();

        for (ProveedorArticulo pa : paList) {
            Articulo articulo = pa.getArticulo();

            DTODetalleProveedorArticulo dto = new DTODetalleProveedorArticulo();
            dto.setCodigoArticulo(articulo.getCodigoArticulo());
            dto.setDemoraEntrega(pa.getDemoraEntrega());
            dto.setPrecioUnitProveedorArticulo(pa.getCostoUnitario());
            dto.setCostoPedido(pa.getCostoPedido());
            dto.setLoteOptimo(pa.getLoteOptimo());
            dto.setCostoMantenimiento((int) pa.getCostoMantenimiento()); // si es double y el DTO lo espera como int
            dto.setEsPredeterminado(pa.isEsPredeterminado());
            dto.setNivelDeServicio(pa.getNivelDeServicio());
            dto.setInventarioMaximo(pa.getInventarioMaximo());


            dtoList.add(dto);
        }

        return dtoList;
    }



}
