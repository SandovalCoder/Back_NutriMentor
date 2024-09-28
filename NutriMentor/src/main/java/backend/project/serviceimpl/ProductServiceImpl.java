package backend.project.serviceimpl;

import backend.project.entities.Product;
import backend.project.exceptions.InvalidDataException;
import backend.project.exceptions.ResourceNotFoundException;
import backend.project.repositories.CategoryRepository;
import backend.project.repositories.ProductRepository;
import backend.project.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Product updateProduct(Product product) {
        Product productFound = findById(product.getId());
        if (productFound == null) {
            throw new ResourceNotFoundException("Product with id: " + product.getId() + " cannot be found");
        }
        if (product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidDataException("Price must be greater than zero");
        }
        if (product.getStock() < 0) {
            throw new InvalidDataException("Stock cannot be negative");
        }
        productFound.setName(product.getName());
        productFound.setPrice(product.getPrice());
        productFound.setStock(product.getStock());
        return productRepository.save(productFound);
    }

    @Override
    public Product insertProduct(Product product) {
        if (!categoryRepository.existsById(product.getCategory().getId())) {
            throw new ResourceNotFoundException("Category with id: " + product.getCategory().getId() + " cannot be found");
        }
        if (product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidDataException("Price must be greater than zero");
        }
        if (product.getStock() < 0) {
            throw new InvalidDataException("Stock cannot be negative");
        }
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        Product productFound = findById(id);
        if (productFound == null) {
            throw new ResourceNotFoundException("Product with id: " + id + " cannot be found");
        }
        productRepository.delete(productFound);
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id: " + id + " cannot be found"));
    }

    @Override
    public List<Product> listAll() {
        return productRepository.findAll();
    }

    //Querys
    @Override
    public List<Product> listAllProducts() {
        return productRepository.listAllProducts();
    }

    @Override
    public List<Object[]> countProductsByCategory() {
        return productRepository.countProductsByCategory();
    }
}
