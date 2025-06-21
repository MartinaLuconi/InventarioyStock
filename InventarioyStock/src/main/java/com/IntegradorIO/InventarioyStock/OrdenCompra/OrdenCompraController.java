package com.IntegradorIO.InventarioyStock.OrdenCompra;


import com.IntegradorIO.InventarioyStock.OrdenCompra.DTO.DTOArticulosDelProveedor;
import com.IntegradorIO.InventarioyStock.OrdenCompra.DTO.DTOOrdenCompra;
import com.IntegradorIO.InventarioyStock.OrdenCompra.DTO.DTOProveedorPredet;
import com.IntegradorIO.InventarioyStock.OrdenCompra.DTO.DTOTablaOrdenCompra;
import com.IntegradorIO.InventarioyStock.Proveedor.Proveedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    @GetMapping("/finalizadas")
    public ResponseEntity<List<DTOTablaOrdenCompra>> obtenerOrdenesCompraFinalizadas() throws Exception {
        List<DTOTablaOrdenCompra> ordenes = ordenCompraService.obtenerOrdenesCompraFinalizadas();
        return ResponseEntity.ok(ordenes);
    }
    @GetMapping("/{nroOrden}")
    public ResponseEntity<OrdenCompra> obtenerOC(@PathVariable int nroOrden) throws Exception {
        OrdenCompra oc = ordenCompraService.obtenerOC(nroOrden);
        return new ResponseEntity<>(oc,HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<?> crearOC(@RequestBody DTOOrdenCompra dtoOC) {
        try {
            Map<String, Object> resultado = ordenCompraService.crearOrdenCompra(dtoOC);
            return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
            //throw ResponseStatusException(HttpStatus.CONFLICT.body(e.getMessage());
        }
    }

    @PutMapping("/{nroOrden}/modificar")
    public ResponseEntity<OrdenCompra> modificarOrdenCompra(@PathVariable int nroOrden, @RequestBody DTOOrdenCompra dtoOC) throws Exception{
        OrdenCompra ocActualizada= ordenCompraService.modificarOrdenCompra(nroOrden,dtoOC);
        return new ResponseEntity<>(ocActualizada,HttpStatus.OK);
    }
    @GetMapping("/{nroOrden}/datos")
    public ResponseEntity<DTOOrdenCompra> mostrarDatosOC(@PathVariable int nroOrden) throws Exception {
        DTOOrdenCompra dtoOrdenCompra = ordenCompraService.mostrarDatosOC(nroOrden);
        return new ResponseEntity<>(dtoOrdenCompra, HttpStatus.OK);
    }


    @PutMapping("/{nroOrden}/cancelar")
    public ResponseEntity<Void> cancelarOC(@PathVariable int nroOrden) throws Exception {
        ordenCompraService.cancelarOC(nroOrden);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping("/{nroOrden}/enviar")
    public ResponseEntity<Void> enviarOC( @PathVariable int nroOrden)throws Exception{
        ordenCompraService.enviarOC(nroOrden);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
   @PutMapping("/{nroOrden}/finalizar")
    public ResponseEntity<Void> finalizarOC(@PathVariable int nroOrden) throws Exception {
       ordenCompraService.finalizarOC(nroOrden);
       return new ResponseEntity<>(HttpStatus.NO_CONTENT);

   }

   //sugerir proveedor
   @GetMapping("/ProvPredeterminado/{codArticulo}")
   public ResponseEntity<DTOProveedorPredet> sugerirProveedorPredetertminado(@PathVariable int codArticulo) {
       System.out.println("🟡 codArticulo recibido: " + codArticulo);
       DTOProveedorPredet proveedor = ordenCompraService.sugerirProveedorPredetertminado(codArticulo);
       return ResponseEntity.ok(proveedor);

   }

   //armar seleccionable
   @GetMapping("/ProveedoresPorArticulo/{codArticulo}")
   public ResponseEntity<List<DTOProveedorPredet>> filtrarProveedorParaArticulos(@PathVariable int codArticulo) {
       List<DTOProveedorPredet> desplegableProveedores = ordenCompraService.filtrarProveedorParaArticulos(codArticulo);
       return ResponseEntity.ok(desplegableProveedores);
   }

   //filtrar lista de articulo por proveedor seleccionado
    @GetMapping("/ArticulosPorProveedor/{codProveedor}")

   public ResponseEntity<List<DTOArticulosDelProveedor>> ArticulosPorProveedor(@PathVariable int codProveedor){
        List<DTOArticulosDelProveedor> desplegableArticulos = ordenCompraService.ArticulosPorProveedor(codProveedor);
        return ResponseEntity.ok(desplegableArticulos);
   }
    @GetMapping("/sugerirCantidad/{codArticulo}/{codProveedor}")
    public ResponseEntity<?> sugerirCantidad(@PathVariable int codArticulo, @PathVariable int codProveedor) {
        try {
            int cantidadSugerida = ordenCompraService.sugerirCantidadAPedir(codArticulo, codProveedor);
            return ResponseEntity.ok(cantidadSugerida);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al calcular la cantidad sugerida: " + e.getMessage());
        }
    }



   //sugerencia de cantidad a pedir
    @GetMapping("/sugerenciaCantidad")
    public ResponseEntity<Integer> sugerirCantidad(@RequestParam int codArticulo, @RequestParam int codProveedor) {
        try {
            int sugerencia = ordenCompraService.sugerirCantidadAPedir(codArticulo, codProveedor);
            return ResponseEntity.ok(sugerencia);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


}
