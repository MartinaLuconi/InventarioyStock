package com.IntegradorIO.InventarioyStock.VentaArticulo;

import com.IntegradorIO.InventarioyStock.Proveedor.Proveedor;
import com.IntegradorIO.InventarioyStock.Proveedor.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ventaArticulo")
public class VentaArticuloController {
    @Autowired
    private VentaArticuloRepository ventaArticuloRepository;

    @GetMapping
    public List<VentaArticulo> listar() {
        return ventaArticuloRepository.findAll();
    }

    @PostMapping
    public VentaArticulo guardar(@RequestBody VentaArticulo ventaArticulo) {
        return ventaArticuloRepository.save(ventaArticulo);
    }
}
