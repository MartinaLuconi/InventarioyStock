package com.IntegradorIO.InventarioyStock.OrdenCompra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ordenCompra")
public class OrdenCompraController {

    @Autowired
    private OrdenCompraRepository ordenCompraRepository;

    @GetMapping
    public List<OrdenCompra> listar() {
        return ordenCompraRepository.findAll();
    }

    @PostMapping
    public OrdenCompra guardar(@RequestBody OrdenCompra ordenCompra) {
        return ordenCompraRepository.save(ordenCompra);
    }
}
