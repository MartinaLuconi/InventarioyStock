package com.IntegradorIO.InventarioyStock.Articulo;
import com.IntegradorIO.InventarioyStock.ProveedorArticulo.ProveedorArticulo;
import jakarta.persistence.*;

import com.IntegradorIO.InventarioyStock.ProveedorArticulo.ProveedorArticulo;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
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

    public void addProveedorArticulo(ProveedorArticulo proveedorArticulo) {
        proveedorArticuloList.add(proveedorArticulo);
    }



   
}


