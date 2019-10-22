package com.hellokoding.springboot.restful.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

@RequiredArgsConstructor
public class ProductService {
    //  private final ProductRespository productRespository;

    public List<Product> findAll() {
        return null;
    }

    public Optional<Product> findById(Long id) {
        return null;// productRespository.findById(id);
    }

    public Product save(Product stock) {
        return null;
    }

    public void deleteById(Long id) {
        //productRespository.deleteById(id);
    }
}
