package com.ewis.demo.ewispc.repository;

import com.ewis.demo.ewispc.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    /*
        Why?
            No need to define methods—Spring Data JPA provides built-in save(), findAll(), findById(), etc.
     */
}
