package com.atom.application.repos;



import java.util.Optional;

import com.atom.application.models.Product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    
 /**
     * Retrieves a single product whose productName matches the requested productName.
     * <p>
     * If no matching product is found, the returned <code>Optional</code> will be
     * empty.
     * 
     * @param productName - the productName of the requested product
     * @return the requested user, wrapped in an <code>Optional</code>
     */
    public Optional<Product> findByProductName(String productName);

}
