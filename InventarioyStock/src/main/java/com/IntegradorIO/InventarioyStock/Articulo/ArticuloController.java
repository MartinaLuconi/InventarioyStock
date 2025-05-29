package com.IntegradorIO.InventarioyStock.Articulo;

import com.IntegradorIO.InventarioyStock.Articulo.DTO.DTOModificarArticulo;
import com.IntegradorIO.InventarioyStock.Articulo.DTO.DTONuevoArticulo;
import com.IntegradorIO.InventarioyStock.Articulo.DTO.DTOTablaArticulos;
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
    public ResponseEntity<List<DTOTablaArticulos>> obtenerArticulos() throws Exception {
       // List<Articulo> articulos = articuloService.obtenerArticulos();
        //return new ResponseEntity<>(articulos, HttpStatus.OK);
        List<DTOTablaArticulos> articulos = articuloService.obtenerArticulos();
        return ResponseEntity.ok(articulos);
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
    @GetMapping("/{codigoArticulo}/detalle")
    public  ResponseEntity<DTONuevoArticulo> mostrarDetalle(@PathVariable int codigoArticulo){
        DTONuevoArticulo dto = articuloService.mostrarDetalle(codigoArticulo);
        return new ResponseEntity<>(dto, HttpStatus.OK);
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
    public ResponseEntity<List<DTOTablaArticulos>> listarArticulosFaltantes () throws Exception{
        try {
            List<DTOTablaArticulos> articulosFaltantes=articuloService.listarArticulosFaltantes();
            return new ResponseEntity<>(articulosFaltantes,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

    }

    //listar articulos a reponer
    @GetMapping("/articulosReponer")
    public ResponseEntity<List<DTOTablaArticulos>> listarArticulosReponer () throws Exception{
        try {
            List<DTOTablaArticulos> articulosReponer=articuloService.listarArticulosReponer();
            return new ResponseEntity<>(articulosReponer, HttpStatus.OK);
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


    // EOQ para un artículo y proveedor
    @GetMapping("/{idArticulo}/proveedor/{idProveedorArticulo}/eoq")
    public int getEOQ(@PathVariable int idArticulo, @PathVariable int idProveedorArticulo) throws Exception {
        Articulo articulo = articuloService.obtenerArticulo(idArticulo);
        ProveedorArticulo proveedorArticulo = proveedorArticuloRepository.findById(idProveedorArticulo).orElseThrow();
        return articuloService.calcularEOQArticulo(articulo, proveedorArticulo);
    }

    // Stock de seguridad (parámetros por query)
    @GetMapping("/stock-seguridad")
    public int getStockSeguridad(
            @RequestParam double z,
            @RequestParam double desviacionEstandar,
            @RequestParam int l
    ) {
        return articuloService.calcularStockSeguridad(z, desviacionEstandar, l);
    }

    // Punto de pedido (ROP)
    @GetMapping("/rop")
    public int getROP(
            @RequestParam double demandaPromedio,
            @RequestParam int stockSeguridad,
            @RequestParam int l
    ) {
        return articuloService.calcularROP(demandaPromedio, stockSeguridad, l);
    }
}
