package org.sid.billingservice;

import java.util.Collection;
import java.util.Date;
import java.util.Random;

import org.sid.billingservice.entities.Bill;
import org.sid.billingservice.entities.ProductItem;
import org.sid.billingservice.feign.CustomerRestClient;
import org.sid.billingservice.feign.ProductRestClient;
import org.sid.billingservice.model.Customer;
import org.sid.billingservice.model.Product;
import org.sid.billingservice.repository.BillRepository;
import org.sid.billingservice.repository.ProductItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.hateoas.PagedModel;


@SpringBootApplication
@EnableFeignClients
public class BillingService1Application {

	public static void main(String[] args) {
		SpringApplication.run(BillingService1Application.class, args);
	}
	CommandLineRunner start(
			BillRepository billrepo,
			ProductItemRepository productrepo,
			CustomerRestClient customerrestclient,
			ProductRestClient productrestclient) {
		return args ->{

			Customer customer=customerrestclient.getCustomerById(1L);
			//Bill bill1=billrepo.save(new Bill(null,new Date(),null,customer.getId(),null));
			//System.out.println(bill1);
			System.out.println("+++++++++++++++++++++++++");
			System.out.println(customer.getId());
			System.out.println(customer.getName());
			System.out.println(customer.getEmail());
			/*PagedModel<Product>productPageModul=productrestclient.PagedProducts(1,1);
			productPageModul.forEach(p->{
				ProductItem productItem=new ProductItem();
				productItem.setPrice(p.getPrice());
				productItem.setQuantity(1+new Random().nextInt(100));
				productItem.setBill(bill1);
				productrepo.save(productItem);
			});*/
		};
	}
}
