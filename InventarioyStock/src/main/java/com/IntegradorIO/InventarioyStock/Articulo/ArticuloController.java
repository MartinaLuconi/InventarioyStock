package com.IntegradorIO.InventarioyStock.Articulo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/articulos")
public class ArticuloController {

    @Autowired
    private ArticuloRepository articuloRepository;

    @GetMapping
    public List<Articulo> listar() {
        return articuloRepository.findAll();
    }

    @PostMapping
    public Articulo guardar(@RequestBody Articulo articulo) {
        return articuloRepository.save(articulo);
    }
}
