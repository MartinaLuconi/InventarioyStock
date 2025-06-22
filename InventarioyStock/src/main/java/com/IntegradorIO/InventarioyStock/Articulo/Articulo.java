package com.IntegradorIO.InventarioyStock.Articulo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity



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
    @Enumerated(EnumType.STRING)
    private EstadoArticulo estadoArticulo;
    @Enumerated(EnumType.STRING)
    private ModeloInventario modeloInventario;
    private int demandaAnual;
    private int puntoPedido;
    private int desviacionEstandar;
    private double costoAlmacenamiento;

    //@OneToMany(mappedBy = "articulo",cascade = CascadeType.ALL, orphanRemoval = true)
   // @JsonIgnore
   // private List<ProveedorArticulo> proveedorArticuloList = new ArrayList<>();

   // public Articulo() {
   // }


}


