package com.IntegradorIO.InventarioyStock.OrdenCompra;

import com.IntegradorIO.InventarioyStock.Articulo.Articulo;
import com.IntegradorIO.InventarioyStock.Articulo.DTO.DTODetalleArticulo;
import com.IntegradorIO.InventarioyStock.Articulo.DTO.DTOModificarArticulo;
import com.IntegradorIO.InventarioyStock.Articulo.DTO.DTONuevoArticulo;
import com.IntegradorIO.InventarioyStock.Articulo.DTO.DTOTablaArticulos;
import com.IntegradorIO.InventarioyStock.OrdenCompra.DTO.DTOOrdenCompra;
import com.IntegradorIO.InventarioyStock.OrdenCompra.DTO.DTOTablaOrdenCompra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordenCompra")
@CrossOrigin(origins ="*")
public class OrdenCompraController {

    @Autowired
    private OrdenCompraService ordenCompraService;
    @GetMapping
    public ResponseEntity<List<DTOTablaOrdenCompra>>  obtenerOrdenesCompra() throws Exception{
        List<DTOTablaOrdenCompra> ordenes = ordenCompraService.obtenerOrdenesCompra();
        return ResponseEntity.ok(ordenes);
    }
    @GetMapping("/{numeroOrdenCompra}")
    public ResponseEntity<OrdenCompra> obtenerOC(@PathVariable int nroOrden) throws Exception {
        OrdenCompra oc = ordenCompraService.obtenerOC(nroOrden);
        return new ResponseEntity<>(oc,HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<OrdenCompra> crearOrdenCompra(@RequestBody DTOOrdenCompra dtoOC) throws Exception{
        OrdenCompra nuevaOC = ordenCompraService.crearOrdenCompra(dtoOC);
        return new ResponseEntity<>(nuevaOC,HttpStatus.CREATED);
    }

    @PutMapping("/{numeroOrdenCompra}/modificar")
    public ResponseEntity<OrdenCompra> modificarOrdenCompra(@PathVariable int nroOrden, @RequestBody DTOOrdenCompra dtoOC) throws Exception{
        OrdenCompra ocActualizada= ordenCompraService.modificarOrdenCompra(nroOrden,dtoOC);
        return new ResponseEntity<>(ocActualizada,HttpStatus.OK);
    }
    @GetMapping("/{numeroOrdenCompra}/datos")
    public ResponseEntity<DTOOrdenCompra> mostrarDatosOC(@PathVariable int nroOrden) throws Exception {
        DTOOrdenCompra dtoOrdenCompra = ordenCompraService.mostrarDatosOC(nroOrden);
        return new ResponseEntity<>(dtoOrdenCompra, HttpStatus.OK);
    }


    @PutMapping("/{numeroOrdenCompra}/cancelar")
    public ResponseEntity<Void> cancelarOC(@PathVariable int nroOrden) throws Exception {
        ordenCompraService.cancelarOC(nroOrden);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping("/{numeroOrdenCompra}/enviar")
    public ResponseEntity<Void> enviarOC( @PathVariable int nroOrden)throws Exception{
        ordenCompraService.enviarOC(nroOrden);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping("/{numeroOrdenCompra}/finalizar")
    public ResponseEntity<Void> finalizarOC(@PathVariable int nroOrden) throws Exception{
        ordenCompraService.finalizarOC(nroOrden);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
