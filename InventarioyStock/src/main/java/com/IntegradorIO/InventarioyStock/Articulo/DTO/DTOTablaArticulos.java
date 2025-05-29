package com.IntegradorIO.InventarioyStock.Articulo.DTO;

import com.IntegradorIO.InventarioyStock.Articulo.Articulo;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Data
@Getter
@Setter
public class DTOTablaArticulos {

        private int codigoArticulo;
        private String nombreArticulo;
        private String descripcion;
        private Timestamp fechaHoraBajaArticulo;

        public DTOTablaArticulos(Articulo articulo) {
                this.codigoArticulo = articulo.getCodigoArticulo();
                this.nombreArticulo = articulo.getNombreArticulo();
                this.descripcion = articulo.getDescripcion();
                this.fechaHoraBajaArticulo = articulo.getFechaHoraBajaArticulo();
        }
}
