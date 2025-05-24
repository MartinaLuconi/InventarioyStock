package com.IntegradorIO.InventarioyStock.Inventario;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
public class Inventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int numeroInventario;
    private Timestamp fechaHoraInicioVigenciaInv;
    private Timestamp fechaHoraFinVigenciaInv;

}
