package com.IntegradorIO.InventarioyStock.Inventario;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "inventario")
@Getter
@Setter
public class Inventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int numeroInventario;
    private Timestamp fechaHoraInicioVigenciaInv;
    private Timestamp fechaHoraFinVigenciaInv;

}
