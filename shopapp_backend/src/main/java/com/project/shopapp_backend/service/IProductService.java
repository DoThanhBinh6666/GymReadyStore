package com.project.shopapp_backend.service;

import com.project.shopapp_backend.dtos.ProductDTO;
import com.project.shopapp_backend.exceptions.DataNotFoundException;
import com.project.shopapp_backend.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


public interface IProductService {
    public Product createProduct(ProductDTO productDTO) throws DataNotFoundException;

    Product getProductById(long id) throws Exception;

    Page<Product> getAllProducts(PageRequest pageRequest);

    Product updateProduct(long id, ProductDTO productDTO) throws Exception;

    void deleteProduct(long id);

    boolean existsByName(String name);
}

