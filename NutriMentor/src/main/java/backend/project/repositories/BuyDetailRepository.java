package backend.project.repositories;

import backend.project.entities.BuyDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuyDetailRepository extends JpaRepository<BuyDetail, Long> {
    // Query en SQL Nativo - Muestra el total de productos consumidos por categoría para un usuario específico.
    @Query(nativeQuery = true, value = "SELECT cat.type, SUM(bd.quantity) FROM app_buyDetail bd JOIN app_product p ON bd.product_id = p.id JOIN app_category cat ON p.category_id = cat.id JOIN app_buys b ON bd.buys_id = b.orderNumber WHERE b.client_id = ?1 GROUP BY cat.type")
    List<Object[]> totalProductsConsumedByCategory(Long clientId);
}