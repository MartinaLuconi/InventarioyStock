package com.IntegradorIO.InventarioyStock.Articulo;

import com.IntegradorIO.InventarioyStock.EstadoOrdenCompra.EstadoOrdenCompraRepository;
import com.IntegradorIO.InventarioyStock.EstadoOrdenCompra.EstadoOrdencCompra;
import com.IntegradorIO.InventarioyStock.Proveedor.Proveedor;
import com.IntegradorIO.InventarioyStock.ProveedorArticulo.ProveedorArticulo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticuloService  {
    @Autowired
    private ArticuloRepository articuloRepository;

    @Autowired
    private EstadoOrdenCompraRepository estadoOrdenCompraRepository;

    //lista los articulos
    public List<Articulo> obtenerArticulos() throws Exception {
        try {
            List<Articulo> articuloStock = articuloRepository.findAll();
            return articuloStock;
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
    // para todos los cambios
    public Articulo guardarArticulo(Articulo articulo) throws Exception{
        try {
            articulo = articuloRepository.save(articulo);
            return articulo;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }

    }

    // Modificar un artículo existente
    public Articulo modificarArticulo(int codigoArticulo, Articulo articuloModificado) throws Exception{

        try {
            Articulo articulo = articuloRepository.obtenerArticulo(codigoArticulo);

            articulo.setNombreArticulo(articuloModificado.getNombreArticulo());
            articulo.setDescripcion(articuloModificado.getDescripcion());
            articulo.setStockActualArticulo(articuloModificado.getStockActualArticulo());
            articulo.setStockSeguridadArticulo(articuloModificado.getStockSeguridadArticulo());

            articulo=articuloRepository.save(articulo);
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
                if (articulo.getStock() > 0) {
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
        try {

            List<Proveedor> listaProveedores = new ArrayList<>();

            //busco el articulo
            Articulo a = articuloRepository.obtenerArticulo(codArticulo);

            //leo intermedias de ese articulo
            List<ProveedorArticulo> palist = a.getProveedorArticuloList();
            for (ProveedorArticulo pa : palist) {
                //meto proveedores en lista
                listaProveedores.add(pa.getProveedor());
            }

            return listaProveedores;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    //listar productos faltantes

    public List<Articulo> listarArticulosFaltantes () throws Exception{
        List<Articulo> articulosFaltantes = new ArrayList<>();
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
        }
        return articulosFaltantes;

    }

    //listar productos a reponer

    public List<Articulo> listarArticulosReponer () throws Exception{
        List<Articulo> articulosReponerL = new ArrayList<>();
        //busco todos los articulos
        List<Articulo> aList =articuloRepository.obtenerArticulos();

        //armo lista de articulos a reponer
        for (Articulo a: aList) {
            //leer el stock actual
            int stockA = a.getStockActualArticulo();
            //lee el punto de pedido
            int pp = 2; //a.getPuntoPedido();
            //si el stock es menor al punto de pedido --> por debajo del PP
            if (stockA<=pp){
                articulosReponerL.add(a);
            }
        }
        return articulosReponerL;
    }





}
