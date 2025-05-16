package com.IntegradorIO.InventarioyStock.Articulo;
import com.IntegradorIO.InventarioyStock.Articulo.Articulo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticuloRepository extends JpaRepository<Articulo, Long> {
}
