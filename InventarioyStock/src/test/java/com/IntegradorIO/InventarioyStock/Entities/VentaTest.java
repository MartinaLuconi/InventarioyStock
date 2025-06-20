package com.IntegradorIO.InventarioyStock.Entities;

import com.IntegradorIO.InventarioyStock.Venta.Venta;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class VentaTest {

    @Test
    public void testConstructorVenta() {
        // Intenta crear una instancia
        Venta venta = new Venta();
        assertNotNull(venta);
    }
}
