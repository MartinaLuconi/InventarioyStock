package com.IntegradorIO.InventarioyStock.VentaArticulo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VentaArticuloService {
    @Autowired
    private VentaArticuloRepository ventaArticuloRepository;
    public VentaArticulo save(VentaArticulo ventaArticulo) {
        return ventaArticuloRepository.save(ventaArticulo);
    }
}

