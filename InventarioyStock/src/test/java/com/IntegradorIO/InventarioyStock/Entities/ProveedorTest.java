package com.IntegradorIO.InventarioyStock.Entities;

import com.IntegradorIO.InventarioyStock.Proveedor.Proveedor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProveedorTest {

    @Test
    public void testConstructorProveedor() {
        // Intenta crear una instancia
        Proveedor proveedor = new Proveedor();
        assertNotNull(proveedor);
    }
}
