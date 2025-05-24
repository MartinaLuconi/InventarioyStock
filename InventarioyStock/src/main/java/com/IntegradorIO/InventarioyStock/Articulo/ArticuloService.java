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
    public List<Articulo> obtenerArticulos() throws Exception {
        try {
            List<Articulo> articuloStock = articuloRepository.findAll();
            return articuloStock;
        }catch (Exception e){
           // System.out.println("No se encontraron artículos");
           // return null;
            throw new Exception(e.getMessage());
        }
    }

    //busca un articulo en particular
    public Optional<Articulo> obtenerArticulo(int codigoArticulo) throws Exception{
        try {
            Optional<Articulo> art = articuloRepository.findById(codigoArticulo);
            return art; //lo pone como art.get();
        }catch (Exception e){
            throw new Exception(e.getMessage()); //No se encontro un articulo con ese codigo
        }

    }
    // para todos los cambios
    public Articulo guardarArticulo(Articulo articulo) throws Exception{
        try {
            articulo = articuloRepository.save(articulo);
            return articulo;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }

    }

    // Modificar un artículo existente
    public Articulo modificarArticulo(int codigoArticulo, Articulo articuloModificado) throws Exception{

        try {
            Articulo articulo = articuloRepository.obtenerArticulo(codigoArticulo);
            articulo=articuloRepository.guardarArticulo(articulo);
            return articulo;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    //para la baja
    public void eliminarArticulo(int codigoArticulo) throws Exception{
        try {
            articuloRepository.deleteById(codigoArticulo);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
