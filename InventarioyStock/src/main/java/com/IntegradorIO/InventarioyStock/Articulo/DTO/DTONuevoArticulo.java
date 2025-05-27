package com.IntegradorIO.InventarioyStock.Articulo.DTO;

import com.IntegradorIO.InventarioyStock.Articulo.ModeloInventario;
import com.IntegradorIO.InventarioyStock.Proveedor.Proveedor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;
@Getter
@Setter
@Data
public class DTONuevoArticulo {

    private int codigoArticulo;
    private String nombreArticulo;
    private  String descripción;
    private int stockReal;
    private int stockSeguridad;
    private int puntoPedido;
    private Timestamp fechaHoraBajaArticulo;
    private double precioUnitario;
    private List<Proveedor> proveedoresAsignados;
    private int demoraEntrega;
    private  double costoPedido;

    private double   costoMantener;
    private double costoAlmacenamiento;
    private int loteOptimo;
    private int inventarioMax;
    private ModeloInventario modeloElegido;
}
