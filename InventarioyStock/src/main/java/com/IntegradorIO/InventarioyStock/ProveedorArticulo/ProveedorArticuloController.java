package com.IntegradorIO.InventarioyStock.ProveedorArticulo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proveedorArticulo")
@CrossOrigin(origins = "*")
public class
ProveedorArticuloController {

    @Autowired
    private ProveedorArticuloService proveedorArticuloService;

    /*@GetMapping("/articulo/{codigoArticulo}")
    public ResponseEntity<List<ProveedorArticulo>> obtenerProveedoresPorArticulo(@PathVariable int codigoArticulo) {
        try {
            List<ProveedorArticulo> relaciones = proveedorArticuloService.obtenerRelacionesPorArticulo(codigoArticulo);
            return ResponseEntity.ok(relaciones);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }*/
}
