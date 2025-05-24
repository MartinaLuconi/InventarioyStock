package com.IntegradorIO.InventarioyStock.Articulo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ArticuloRepository extends JpaRepository<Articulo, Integer> {
    @Query("SELECT a FROM Articulo a WHERE a.codigoArticulo = :id")
    Articulo obtenerArticulo(@Param("id") Integer id);

    @Query("SELECT a FROM Articulo a")
    List<Articulo> obtenerArticulos();

    @Query("SELECT a FROM Articulo a WHERE a.codigoArticulo= :id")
    Articulo modificarArticulo(@Param("id") Integer id);


}
