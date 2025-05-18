package com.IntegradorIO.InventarioyStock.ProveedorArticulo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proveedorArticulo")
public class ProveedorArticuloController {

    @Autowired
    private ProveedorArticuloRepository proveedorArticuloRepository;

    @GetMapping
    public List<ProveedorArticulo> listar() {
        return proveedorArticuloRepository.findAll();
    }

    @PostMapping
    public ProveedorArticulo guardar(@RequestBody ProveedorArticulo proveedorArticulo) {
        return proveedorArticuloRepository.save(proveedorArticulo);
    }
}
