package backend.project.repositories;

import backend.project.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Query en SQL Nativo - Lista todos los productos para el seguimiento de transacciones y gestión de inventario.
    @Query(nativeQuery = true, value = "SELECT * FROM app_product")
    List<Product> listAllProducts();

    // Query en SQL Nativo - Cuenta la cantidad de productos por categoría para conocer la categoría más ofertada.
    @Query(nativeQuery = true, value = "SELECT c.type, COUNT(p.id) AS productCount FROM app_product p JOIN app_category c ON p.category_id = c.id GROUP BY c.type")
    List<Object[]> countProductsByCategory();
}
