package com.IntegradorIO.InventarioyStock.EstadoOrdenCompra;

import com.IntegradorIO.InventarioyStock.Articulo.Articulo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
@Getter
@Setter
@Entity
public class EstadoOrdenCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codigoEstadoOrdenCompra;

    private EstadoOrdencCompra nombreEstado;
    private Timestamp fechaHoraBajaEstadoOC;
    //private String nombreEstadoOC;

    @ManyToOne
    @JoinColumn(name = "codigo_articulo")
    private Articulo articulo;

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }
}
