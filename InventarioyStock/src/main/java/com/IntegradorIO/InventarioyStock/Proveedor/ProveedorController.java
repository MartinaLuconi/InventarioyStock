package com.IntegradorIO.InventarioyStock.Proveedor;

import com.IntegradorIO.InventarioyStock.Articulo.Articulo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proveedor")
@CrossOrigin(origins = "http://localhost:5173")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService ;

    @GetMapping
    public ResponseEntity<List<Proveedor>> obtenerProveedores() throws Exception {
        List<Proveedor> proveedores = proveedorService.obtenerProveedores();
        return new ResponseEntity<>(proveedores, HttpStatus.OK);
    }


}
