package com.IntegradorIO.InventarioyStock.OrdenCompra;

import com.IntegradorIO.InventarioyStock.Articulo.Articulo;
import com.IntegradorIO.InventarioyStock.Articulo.DTO.DTOModificarArticulo;
import com.IntegradorIO.InventarioyStock.Articulo.DTO.DTONuevoArticulo;
import com.IntegradorIO.InventarioyStock.OrdenCompra.DTO.DTOOrdenCompra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ordenCompra")
@CrossOrigin("*")
public class OrdenCompraController {

    @Autowired
    private OrdenCompraService ordenCompraService;

    @PostMapping
    public ResponseEntity<OrdenCompra> crearOrdenCompra(@RequestBody DTOOrdenCompra dtoOC) throws Exception{
        OrdenCompra nuevaOC = ordenCompraService.crearOrdenCompra(dtoOC);
        return new ResponseEntity<>(nuevaOC,HttpStatus.CREATED);
    }

    @PutMapping("/{numeroOrdenCompra}")
    public ResponseEntity<OrdenCompra> modificarOrdenCompra(@PathVariable int nroOrden, @RequestBody DTOOrdenCompra dtoOC) throws Exception{
        OrdenCompra ocActualizada= ordenCompraService.modificarOrdenCompra(nroOrden,dtoOC);
        return new ResponseEntity<>(ocActualizada,HttpStatus.OK);
    }
    @PutMapping("/{numeroOrdenCompra}")
    public void cancelarOC(int nroOrden) throws Exception {
        ordenCompraService.cancelarOC(nroOrden);
    }
    @PutMapping("/{numeroOrdenCompra}")
    public void enviarOC(int nroOrden)throws Exception{
        ordenCompraService.enviarOC(nroOrden);
    }
    @PutMapping("/{numeroOrdenCompra}")
    public void finalizarOC(int nroOrden) throws Exception{
        ordenCompraService.finalizarOC(nroOrden);
    }


}
