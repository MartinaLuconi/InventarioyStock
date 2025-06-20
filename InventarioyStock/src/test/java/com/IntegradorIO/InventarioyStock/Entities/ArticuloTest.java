package com.IntegradorIO.InventarioyStock.Entities;

import com.IntegradorIO.InventarioyStock.Articulo.Articulo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ArticuloTest {

    @Test
    public void testConstructorArticulo(){
        // Intenta crear una instancia
        Articulo articulo = new Articulo();
        assertNotNull(articulo);
    }
}
