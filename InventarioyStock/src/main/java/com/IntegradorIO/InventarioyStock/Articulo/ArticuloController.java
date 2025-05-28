package com.IntegradorIO.InventarioyStock.Articulo;

import com.IntegradorIO.InventarioyStock.Articulo.DTO.DTOModificarArticulo;
import com.IntegradorIO.InventarioyStock.Articulo.DTO.DTONuevoArticulo;
import com.IntegradorIO.InventarioyStock.Proveedor.Proveedor;
import com.IntegradorIO.InventarioyStock.ProveedorArticulo.ProveedorArticulo;
import com.IntegradorIO.InventarioyStock.ProveedorArticulo.ProveedorArticuloRepository;
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

    @Autowired
    private ArticuloRepository articuloRepository;

    @Autowired
    private ProveedorArticuloRepository proveedorArticuloRepository;

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
    public ResponseEntity<Articulo> guardarArticulo(@RequestBody DTONuevoArticulo dtoNuevoArticulo)  throws Exception{
        Articulo nuevoArticulo = articuloService.guardarArticulo(dtoNuevoArticulo);
        return new ResponseEntity<>(nuevoArticulo, HttpStatus.CREATED);
    }
    // modificar articulo existente
    @PutMapping("/{codigoArticulo}")
    public ResponseEntity<Articulo> modificarArticulo(@PathVariable int codigoArticulo, @RequestBody DTOModificarArticulo articuloModificado )  throws Exception{
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

    //listar proveedores de un articulo
    @GetMapping("/{codigoArticulo}/proveedores")
    public ResponseEntity<List<Proveedor>> listarProveedores(@PathVariable int codigoArticulo) throws Exception{
        articuloService.listarProveedores(codigoArticulo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //listar articulos faltantes
    @GetMapping("/articulosFaltantes")
    public ResponseEntity<List<Articulo>> listarArticulosFaltantes () throws Exception{
        try {
            articuloService.listarArticulosFaltantes();
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

    }

    //listar articulos a reponer
    @GetMapping("/articulosReponer")
    public ResponseEntity<List<Articulo>> listarArticulosReponer () throws Exception{
        try {
            articuloService.listarArticulosReponer();
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

// Calcular Punto de Pedido (CGI) para un artículo y proveedor específico
    @GetMapping("/{idArticulo}/proveedor/{idProveedorArticulo}/cgi")
    public double obtenerCGI(
            @PathVariable int idArticulo,
            @PathVariable int idProveedorArticulo
    ) throws Exception {
        Articulo articulo = articuloRepository.findById(idArticulo)
                .orElseThrow(() -> new Exception("Artículo no encontrado"));
        ProveedorArticulo proveedorArticulo = proveedorArticuloRepository.findById(idProveedorArticulo)
                .orElseThrow(() -> new Exception("ProveedorArticulo no encontrado"));
        return articuloService.calcularCGIArticulo(articulo, proveedorArticulo);
    }
}
