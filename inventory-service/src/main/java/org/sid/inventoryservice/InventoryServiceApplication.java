package org.sid.inventoryservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryServiceApplication
{

	public static void main(String[] args)
	{
		SpringApplication.run(InventoryServiceApplication.class, args);
	}
	@Bean
	CommandLineRunner start(ProductRepository productRepository){
		return args -> {
			productRepository.save(new Product(null,"PC",700,12));
			productRepository.save(new Product(null,"HP Printer",1200,70));
			productRepository.save(new Product(null,"S10",6500,20));
			productRepository.findAll().forEach(p->{
				System.out.println(p.getName());;
			});
		};
	}
}
