package com.IntegradorIO.InventarioyStock;

import com.IntegradorIO.InventarioyStock.Articulo.ArticuloRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;
import com.IntegradorIO.InventarioyStock.Articulo.Articulo;


@SpringBootApplication
public class InventarioyStockApplication {



    public static void main(String[] args) {
		SpringApplication.run(InventarioyStockApplication.class, args);
	}
		@Bean
		public CommandLineRunner init(ArticuloRepository repo) {
			return args -> {
				Articulo articulo = new Articulo();
				articulo.setNombre("Chocolate");
				repo.save(articulo);

				System.out.println("Artículo guardado: " + articulo.getNombre());
			};
		}




}
