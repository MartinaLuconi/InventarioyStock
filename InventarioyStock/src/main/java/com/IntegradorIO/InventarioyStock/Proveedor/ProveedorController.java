package com.IntegradorIO.InventarioyStock.Proveedor;

import com.IntegradorIO.InventarioyStock.ProveedorArticulo.ProveedorArticulo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
@CrossOrigin(origins = "http://localhost:5173")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    // Listar solo proveedores activos
    @GetMapping
    public ResponseEntity<List<Proveedor>> obtenerTodosLosProveedores() {
        List<Proveedor> proveedores = proveedorService.obtenerProveedores();
        return new ResponseEntity<>(proveedores, HttpStatus.OK);
    }

    // Obtener un proveedor activo por código
    @GetMapping("/{codigoProveedor}")
    public ResponseEntity<Proveedor> obtenerProveedorPorCodigo(@PathVariable Integer codigoProveedor) {
        return proveedorService.obtenerProveedor(codigoProveedor)
                .map(p -> new ResponseEntity<>(p, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Crear un nuevo proveedor (siempre activo)
    @PostMapping
    public ResponseEntity<Proveedor> guardarProveedor(@RequestBody Proveedor proveedor) {
        Proveedor creado = proveedorService.guardarProveedor(proveedor);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    // Actualizar proveedor activo
    @PutMapping("/{codigoProveedor}")
    public ResponseEntity<Proveedor> modificarProveedor(
            @PathVariable Integer codigoProveedor,
            @RequestBody Proveedor proveedorModificado) {

        Proveedor actualizado = proveedorService.modificarProveedor(codigoProveedor, proveedorModificado);
        if (actualizado != null) {
            return new ResponseEntity<>(actualizado, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Baja lógica con control de predeterminado
    @DeleteMapping("/{codigoProveedor}")
    public ResponseEntity<Void> bajaProveedor(@PathVariable Integer codigoProveedor) {
        try {
            proveedorService.bajaProveedor(codigoProveedor);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            // No existe o ya estaba inactivo
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            // Está predeterminado en algún artículo
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/{codigoProveedor}/articulos")
    public ResponseEntity<List<ProveedorArticulo>> listarArticulosPorProveedor(
            @PathVariable Integer codigoProveedor) {
        try {
            List<ProveedorArticulo> lista =
                    proveedorService.obtenerArticulosPorProveedor(codigoProveedor);
            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            // Proveedor no existe o está inactivo
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
