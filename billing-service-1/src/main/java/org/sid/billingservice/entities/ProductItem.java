package org.sid.billingservice.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sid.billingservice.model.Product;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductItem {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private double quantity;
	private double price;
	private long productId;
	@ManyToOne
	private Bill bill;
	@Transient
	private Product product;
}
