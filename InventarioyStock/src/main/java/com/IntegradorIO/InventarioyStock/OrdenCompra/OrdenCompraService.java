package com.IntegradorIO.InventarioyStock.OrdenCompra;

import com.IntegradorIO.InventarioyStock.EstadoOrdenCompra.EstadoOrdenCompra;
import com.IntegradorIO.InventarioyStock.EstadoOrdenCompra.EstadoOrdenCompraRepository;
import com.IntegradorIO.InventarioyStock.EstadoOrdenCompra.EstadoOrdencCompra;
import com.IntegradorIO.InventarioyStock.OrdenCompra.DTO.DTOOrdenCompra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrdenCompraService {
    @Autowired
    private OrdenCompraRepository ordenCompraRepository;
    @Autowired
    private  EstadoOrdenCompraRepository estadoOrdenCompraRepository;

    //buscar una orden de compra por codigo
    public OrdenCompra obtenerOC(int nroOrden) throws Exception {
        try {
            Optional<OrdenCompra> ordenCompraOptional = ordenCompraRepository.findById(nroOrden);
            return ordenCompraOptional.get(); //lo pone como art.get();
        } catch (Exception e) {
            throw new Exception(e.getMessage()); //No se encontro un articulo con ese codigo
        }
    }

    //alta OC
    public OrdenCompra crearOrdenCompra(DTOOrdenCompra dtoOC) throws Exception{
        EstadoOrdenCompra estadoOrdenCompra = new EstadoOrdenCompra();
        estadoOrdenCompra.setFechaHoraBajaEstadoOC(null);
        estadoOrdenCompra.setNombreEstado(EstadoOrdencCompra.PENDIENTE); //todas empiezan en pendiente
        estadoOrdenCompraRepository.save(estadoOrdenCompra);

        OrdenCompra oc = new OrdenCompra();
            oc.setCantidadOrdenCompra(dtoOC.getCantidad());
            oc.setNombreOrdenCompra(dtoOC.getNombreOC());
            oc.setNumeroOrdenCompra(dtoOC.getCantidad());
            oc.setEstadoOrdenCompra(estadoOrdenCompra);
            //verificar que no este pendiente o enviada ya

        ordenCompraRepository.save(oc);
        return  oc;
    }

    //modificar la orden
    public OrdenCompra modificarOrdenCompra(int nroOrden,DTOOrdenCompra dtoOC) throws Exception{
        OrdenCompra ocModificada = obtenerOC(nroOrden);
            ocModificada.setNombreOrdenCompra(dtoOC.getNombreOC());
            ocModificada.setCantidadOrdenCompra(dtoOC.getCantidad());
            ocModificada.setNumeroOrdenCompra(nroOrden); //esto en realidad no
        if (ocModificada.getEstadoOrdenCompra().getNombreEstado()==EstadoOrdencCompra.PENDIENTE) { //solo modifica si esta pendiente
            ordenCompraRepository.save(ocModificada);
        }else{
            throw new Exception("No se puede modificar la orden. Ya no se encuentra  PENDIENTE");
        }
        return  ocModificada;
    }

    //sugerir proveedor

    //sugerir lote

    //GESTION DE ESTADOS

    //cancelar solo si esta en estado pendiente

    public void cancelarOC(int nroOrden) throws Exception {
        OrdenCompra oc = obtenerOC(nroOrden); //buscarla por id
            EstadoOrdenCompra estadoActual = oc.getEstadoOrdenCompra();//buscar el estado actual relacionado
            EstadoOrdencCompra nombreEstado= estadoActual.getNombreEstado() ;

            if (nombreEstado == EstadoOrdencCompra.PENDIENTE){
                estadoActual.setNombreEstado(EstadoOrdencCompra.CANCELADO);
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
        }
        //debería invocar a una funcion de actualizar el inventario

    }



}
