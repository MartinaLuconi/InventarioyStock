package com.IntegradorIO.InventarioyStock.Proveedor;

import com.IntegradorIO.InventarioyStock.Articulo.Articulo;
import com.IntegradorIO.InventarioyStock.Articulo.ArticuloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorService {
    @Autowired
    private ProveedorRepository proveedorRepository;
    public List<Proveedor> obtenerProveedores() throws Exception {
        try {
            List<Proveedor> proveedorList = proveedorRepository.findAll();
            return proveedorList;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
