package org.sid.billingservice.entities;

import java.util.Collection;
import java.util.Date;
import javax.persistence.*;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sid.billingservice.model.Customer;


@Entity 
@Data @NoArgsConstructor @AllArgsConstructor
public class Bill {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private Date billingDate;
	@OneToMany(mappedBy = "bill")
	private Collection<ProductItem> productItems;
	private long customerId;
	@Transient
	private Customer customer;
}
