package com.IntegradorIO.InventarioyStock.ProveedorArticulo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorArticuloService {

    @Autowired
    private ProveedorArticuloRepository proveedorArticuloRepository;

   /* public List<ProveedorArticulo> obtenerRelacionesPorArticulo(int codigoArticulo) throws Exception {
        try {
            return proveedorArticuloRepository.findByArticulo_CodigoArticulo(codigoArticulo);
        } catch (Exception e) {
            throw new Exception("Error al obtener proveedores del artículo: " + e.getMessage());
        }
    } */

}
