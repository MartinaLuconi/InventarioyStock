package com.IntegradorIO.InventarioyStock.Articulo;

import com.IntegradorIO.InventarioyStock.Proveedor.Proveedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.List;

@RestController
@RequestMapping("/api/articulos")
@CrossOrigin(origins = "*")


public class ArticuloController {
    @Autowired
    private ArticuloService articuloService;

    //listar TODOS los articulos
    @GetMapping
    public ResponseEntity<List<Articulo>> obtenerArticulos() throws Exception {
        List<Articulo> articulos = articuloService.obtenerArticulos();
        return new ResponseEntity<>(articulos, HttpStatus.OK);
    }

    //busca solo un articulo por codigo
    @GetMapping("/{codigoArticulo}")
    public ResponseEntity<Articulo> obtenerArticuloPorCodigo(@PathVariable int codigoArticulo) throws Exception {
        Articulo articulo = articuloService.obtenerArticulo(codigoArticulo);
        if (articulo != null) {
            return new ResponseEntity<>(articulo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    //crear articulo
    @PostMapping
    public ResponseEntity<Articulo> guardarArticulo(@RequestBody Articulo articulo)  throws Exception{
        Articulo nuevoArticulo = articuloService.guardarArticulo(articulo);
        return new ResponseEntity<>(nuevoArticulo, HttpStatus.CREATED);
    }
    // modificar articulo existente
    @PutMapping("/{codigoArticulo}")
    public ResponseEntity<Articulo> modificarArticulo(@PathVariable int codigoArticulo, @RequestBody Articulo articuloModificado)  throws Exception{
        Articulo articuloActualizado = articuloService.modificarArticulo(codigoArticulo, articuloModificado);
        if (articuloActualizado != null) {
            return new ResponseEntity<>(articuloActualizado, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //borrar el articulo
    @DeleteMapping("/{codigoArticulo}")
    public ResponseEntity<Void> eliminarArticulo(@PathVariable int codigoArticulo)  throws Exception{
        articuloService.eliminarArticulo(codigoArticulo);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //listar proveedores
    @GetMapping("/{codigoArticulo}/proveedores")
    public List<Proveedor> listarProveedores(@PathVariable int codigoArticulo) throws Exception{
        return articuloService.listarProveedores(codigoArticulo);
    }

}
