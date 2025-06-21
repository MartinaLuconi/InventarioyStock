package com.IntegradorIO.InventarioyStock.Venta;

import com.IntegradorIO.InventarioyStock.OrdenCompra.DTO.DTOOrdenCompra;
import org.springframework.web.bind.annotation.*;
import com.IntegradorIO.InventarioyStock.Venta.dto.DTOTablaVentas;
import com.IntegradorIO.InventarioyStock.Venta.dto.VentaRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/ventas")
@CrossOrigin(origins = "*")
public class VentaController {


    @Autowired
    private VentaService ventaService;


    @Autowired
    private VentaRepository ventaRepository;


    /** Listar todas las ventas */
    @GetMapping("/tabla")
    public ResponseEntity<List<DTOTablaVentas>> listarVentasTabla() {
        try {
            List<DTOTablaVentas> ventas = ventaService.obtenerVentas();
            return new ResponseEntity<>(ventas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    /** Alta de venta (con validación completa de stock) */
    @PostMapping
    public ResponseEntity<?> crearVenta(@RequestBody VentaRequest req) {
        try {
            Venta v = ventaService.guardarVentaConArticulos(req);
            return new ResponseEntity<>(v, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/verDetalle/{nroVenta}")
    public ResponseEntity<VentaRequest> mostrarDetalleVentas(@PathVariable int nroVenta) throws Exception {
        VentaRequest dtoDetalles = ventaService.mostrarDetalleVentas(nroVenta);
        return new ResponseEntity<>(dtoDetalles,HttpStatus.OK);
    }

}
