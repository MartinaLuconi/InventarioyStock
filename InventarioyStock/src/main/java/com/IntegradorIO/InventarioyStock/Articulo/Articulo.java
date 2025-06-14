package com.IntegradorIO.InventarioyStock.Articulo;
import com.IntegradorIO.InventarioyStock.ProveedorArticulo.ProveedorArticulo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import com.IntegradorIO.InventarioyStock.ProveedorArticulo.ProveedorArticulo;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@Entity

@jakarta.persistence.EntityListeners({
        com.IntegradorIO.InventarioyStock.EstrategiaDeRevisionP.ArticuloListenerP.class,
        com.IntegradorIO.InventarioyStock.EstrategiaDeRevisión.ArticuloListener.class
})

@Getter
@Setter
public class Articulo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codigoArticulo;
    private String nombreArticulo;
    private String descripcion;
    private Timestamp fechaHoraBajaArticulo;
    private int stockActualArticulo;
    private int stockSeguridadArticulo;
    private EstadoArticulo estadoArticulo;
    private ModeloInventario modeloInventario;
    private int demandaAnual;
    private int puntoPedido;
    private int desviacionEstandar;
    private double costoAlmacenamiento;

    //@OneToMany(mappedBy = "articulo",cascade = CascadeType.ALL, orphanRemoval = true)
   // @JsonIgnore
   // private List<ProveedorArticulo> proveedorArticuloList = new ArrayList<>();

   
    public Articulo() {
    }

    public void addProveedorArticulo(ProveedorArticulo proveedorArticulo) {
        proveedorArticuloList.add(proveedorArticulo);
    }



   
}


