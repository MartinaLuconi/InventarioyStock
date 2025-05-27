package com.IntegradorIO.InventarioyStock.Articulo;
import com.IntegradorIO.InventarioyStock.ProveedorArticulo.ProveedorArticulo;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
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

    @OneToMany(mappedBy = "articulo",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProveedorArticulo> proveedorArticuloList = new ArrayList<>();

    public Articulo() {
    }
    public EstadoArticulo getEstadoArticulo() {
        return estadoArticulo;
    }
    public void setEstadoArticulo(EstadoArticulo estadoArticulo) {
        this.estadoArticulo = estadoArticulo;
    }

    public int getCodigoArticulo() {
        return codigoArticulo;
    }

    public void setCodigoArticulo(int codigoArticulo) {
        this.codigoArticulo = codigoArticulo;
    }

    public String getNombreArticulo() {
        return nombreArticulo;
    }

    public void setNombreArticulo(String nombreArticulo) {
        this.nombreArticulo = nombreArticulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Timestamp getFechaHoraBajaArticulo() {
        return fechaHoraBajaArticulo;
    }

    public void setFechaHoraBajaArticulo(Timestamp fechaHoraBajaArticulo) {
        this.fechaHoraBajaArticulo = fechaHoraBajaArticulo;
    }

    public int getStockActualArticulo() {
        return stockActualArticulo;
    }

    public void setStockActualArticulo(int stockActualArticulo) {
        this.stockActualArticulo = stockActualArticulo;
    }

    public int getStockSeguridadArticulo() {
        return stockSeguridadArticulo;
    }

    public void setStockSeguridadArticulo(int stockSeguridadArticulo) {
        this.stockSeguridadArticulo = stockSeguridadArticulo;
    }

    public int getStock() {
        return stockActualArticulo;
    }

    public List<ProveedorArticulo> getProveedorArticuloList() {
        return proveedorArticuloList;
    }

    public void addProveedorArticulo(ProveedorArticulo proveedorArticulo) {
        proveedorArticuloList.add(proveedorArticulo);
    }


    public int getPuntoPedido() {
        return puntoPedido;
    }

    public void setPuntoPedido(int puntoPedido) {
        this.puntoPedido = puntoPedido;
    }

    public ModeloInventario getModeloInventario() {
        return modeloInventario;
    }

    public void setModeloInventario(ModeloInventario modeloInventario) {
        this.modeloInventario = modeloInventario;
    }

    public int getDemandaAnual() {
        return demandaAnual;
    }

    public void setDemandaAnual(int demandaAnual) {
        this.demandaAnual = demandaAnual;
    }
}


