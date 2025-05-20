package com.predators.repository;

import com.predators.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.PriorityQueue;

@Repository
public interface ProductJpaRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    List<Product> findByCategory_Id(Long categoryId);

    PriorityQueue<Product> findAllByDiscountPriceIsNotNull();
}
