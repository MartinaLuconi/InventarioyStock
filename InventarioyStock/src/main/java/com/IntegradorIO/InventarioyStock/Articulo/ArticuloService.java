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
    public Articulo obtenerArticulo(int codigoArticulo) throws Exception{
        try {
            Optional<Articulo> articuloOptional = articuloRepository.findById(codigoArticulo);
            return articuloOptional.get(); //lo pone como art.get();
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
            articulo=articuloRepository.save(articulo);
            return articulo;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }



    //para la baja (este deberia ser un boolean para saber si se borro o no)
    public boolean eliminarArticulo(int codigoArticulo) throws Exception{
        try {
            if (articuloRepository.existsById(codigoArticulo)) {
                articuloRepository.deleteById(codigoArticulo);
                return true; // Se eliminó correctamente
            } else {
              throw new Exception("El artículo con el código " + codigoArticulo + " no existe.");
            }


        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
