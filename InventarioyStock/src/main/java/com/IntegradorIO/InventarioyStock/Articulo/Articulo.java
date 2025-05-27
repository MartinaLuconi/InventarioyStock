package com.IntegradorIO.InventarioyStock.Articulo;
import jakarta.persistence.*;

import com.IntegradorIO.InventarioyStock.ProveedorArticulo.ProveedorArticulo;
import java.sql.Timestamp;
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

    @OneToMany(
            mappedBy = "articulo",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<ProveedorArticulo> proveedoresAsociados;
    public Articulo() {
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
    public List<ProveedorArticulo> getProveedoresAsociados() {
        return proveedoresAsociados;
    }

    public void setProveedoresAsociados(List<ProveedorArticulo> proveedoresAsociados) {
        this.proveedoresAsociados = proveedoresAsociados;
    }
}


