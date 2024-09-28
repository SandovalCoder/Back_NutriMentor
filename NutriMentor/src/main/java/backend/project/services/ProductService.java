package backend.project.services;

import backend.project.entities.Product;

import java.util.List;

public interface ProductService {

    List<Product> listAll();

    Product insertProduct(Product product);

    Product updateProduct(Product product);

    void deleteProduct(Long id);

    Product findById(Long id);

    //Querys
    List<Product> listAllProducts();
    List<Object[]> countProductsByCategory();
}
