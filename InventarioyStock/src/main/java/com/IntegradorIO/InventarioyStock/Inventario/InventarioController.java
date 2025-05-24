package com.IntegradorIO.InventarioyStock.Inventario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventario")
public class InventarioController {

    @Autowired
    private InventarioRepository inventarioRepository;

    @GetMapping
    public List<Inventario> listar() {
        return inventarioRepository.findAll();
    }

    @PostMapping
    public Inventario guardar(@RequestBody Inventario inventario) {
        return inventarioRepository.save(inventario);
    }

}
