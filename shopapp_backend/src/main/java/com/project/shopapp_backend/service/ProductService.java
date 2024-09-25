package com.project.shopapp_backend.service;

import com.project.shopapp_backend.dtos.ProductDTO;
import com.project.shopapp_backend.exceptions.DataNotFoundException;
import com.project.shopapp_backend.models.Category;
import com.project.shopapp_backend.models.Product;
import com.project.shopapp_backend.repositories.CategoryRepository;
import com.project.shopapp_backend.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;// khoi tao bang final la khoi tao 1 lan va su dung mai mai
    private final CategoryRepository categoryRepository;

    @Override
    public Product createProduct(ProductDTO productDTO) throws DataNotFoundException {
       Category existingCategory = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(()
                        -> new DataNotFoundException("can not category with id "+productDTO.getCategoryId()));
        Product newProduct = Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .thumbnail(productDTO.getThumbnail())
                .category(existingCategory)
                .build();
        return productRepository.save(newProduct);
    }

    @Override
    public Product getProductById(long productId) throws Exception {
        return productRepository.findById(productId)
                .orElseThrow(()-> new DataNotFoundException(
                        "cannot find product with id"+productId));
    }

    @Override
    public Page<Product> getAllProducts(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest);
    }

    @Override
    public Product updateProduct(long id, ProductDTO productDTO)
            throws Exception {
        Product existingProduct = getProductById(id);

        return null;
    }

    @Override
    public void deleteProduct(long id) {

    }

    @Override
    public boolean existsByName(String name) {
        return false;
    }
}
