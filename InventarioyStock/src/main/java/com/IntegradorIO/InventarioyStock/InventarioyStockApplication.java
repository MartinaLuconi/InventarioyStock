package com.IntegradorIO.InventarioyStock;

import com.IntegradorIO.InventarioyStock.Articulo.ArticuloService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;


@SpringBootApplication
public class InventarioyStockApplication {



    public static void main(String[] args) {
		SpringApplication.run(InventarioyStockApplication.class, args);
	}
		@Bean
		public CommandLineRunner init(ArticuloService repo) {
			return args -> {

			};
		}




}
