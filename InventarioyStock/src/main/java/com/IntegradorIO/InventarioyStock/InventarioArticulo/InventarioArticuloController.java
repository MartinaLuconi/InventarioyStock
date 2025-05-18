package com.IntegradorIO.InventarioyStock.InventarioArticulo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventarioArticulo")
public class InventarioArticuloController {

    @Autowired
    private InventarioArticuloRepository inventarioArticuloRepository;

    @GetMapping
    public List<InventarioArticulo> listar() {
        return inventarioArticuloRepository.findAll();
    }

    @PostMapping
    public InventarioArticulo guardar(@RequestBody InventarioArticulo inventarioArticulo) {
        return inventarioArticuloRepository.save(inventarioArticulo);
    }
}
