package com.IntegradorIO.InventarioyStock.Venta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/venta")
public class VentaController {

    @Autowired
    private VentaRepository ventaRepository;

    @GetMapping
    public List<Venta> listar() {
        return ventaRepository.findAll();
    }

    @PostMapping
    public Venta guardar(@RequestBody Venta venta) {
        return ventaRepository.save(venta);
    }
}
