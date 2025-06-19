package com.IntegradorIO.InventarioyStock.Articulo.DTO;

import com.IntegradorIO.InventarioyStock.Articulo.ModeloInventario;
import com.IntegradorIO.InventarioyStock.Proveedor.Proveedor;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Data
@Getter
@Setter
public class DTOModificarArticulo {



    private String nombreArticulo;
    private  String descripcion;
    private int stockReal;
    private int stockSeguridad;
    private int puntoPedido;
    private Timestamp fechaHoraBajaArticulo;
    private double costoAlmacenamiento;
    @Enumerated(EnumType.STRING)
    private ModeloInventario modeloElegido;
    private int demandaAnual;
    private int desviacionEstandar;
}
