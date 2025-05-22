package com.IntegradorIO.InventarioyStock.Articulo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticuloService  {
    @Autowired
    private ArticuloRepository articuloRepository;
    //lista los articulos
    public List<Articulo> obtenerArticulos() {
        return articuloRepository.findAll();
    }

    //busca un articulo en particular
    public Optional<Articulo> obtenerArticulo(int codigoArticulo) {
        return articuloRepository.findById(codigoArticulo);
    }
    // para todos los cambios
    public Articulo guardarArticulo(Articulo articulo) {
        return articuloRepository.save(articulo);
    }

    // Modificar un artículo existente
    public Articulo modificarArticulo(int codigoArticulo, Articulo articuloModificado) {
        if (articuloRepository.existsById(codigoArticulo)) {
            articuloModificado.setCodigoArticulo(codigoArticulo);
            return articuloRepository.save(articuloModificado);
        }
        return null; // O lanzar una excepción
    }

    //para la baja
    public void eliminarArticulo(int codigoArticulo) {
        articuloRepository.deleteById(codigoArticulo);
    }
}
