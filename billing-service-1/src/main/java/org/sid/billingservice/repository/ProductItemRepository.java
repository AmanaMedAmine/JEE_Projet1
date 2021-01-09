package org.sid.billingservice.repository;

import java.util.Collection;

import org.sid.billingservice.entities.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ProductItemRepository extends JpaRepository<ProductItem, Long>{
	public Collection<ProductItem> findByBillId(Long id);
}
