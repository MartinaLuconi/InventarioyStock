package com.IntegradorIO.InventarioyStock.OrdenCompraArticulo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ordenCompraArticulo")
public class OrdenCompraArticuloController {

    @Autowired
    private OrdenCompraArticuloRepository ordenCompraArticuloRepository;

    @GetMapping
    public List<OrdenCompraArticulo> listar() {
        return ordenCompraArticuloRepository.findAll();
    }

    @PostMapping
    public OrdenCompraArticulo guardar(@RequestBody OrdenCompraArticulo ordenCompraArticulo) {
        return ordenCompraArticuloRepository.save(ordenCompraArticulo);
    }
}
