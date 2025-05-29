package com.IntegradorIO.InventarioyStock.EstadoOrdenCompra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estadoOrdenCompra")
public class EstadoOrdenCompraController {

    @Autowired
    private EstadoOrdenCompraRepository estadoOrdenCompraRepository;

    @GetMapping
    public List<EstadoOrdenCompra> listar() {
        return estadoOrdenCompraRepository.findAll();
    }

    @PostMapping
    public EstadoOrdenCompra guardar(@RequestBody EstadoOrdenCompra estadoOrdenCompra) {
        return estadoOrdenCompraRepository.save(estadoOrdenCompra);
    }
}
