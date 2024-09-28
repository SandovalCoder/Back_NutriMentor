package backend.project.repositories;

import backend.project.entities.Buys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface BuysRepository extends JpaRepository<Buys, Long> {
    // Query en SQL Nativo - Cuenta el total de compras realizadas por un cliente específico.
    @Query(nativeQuery = true, value = "SELECT COUNT(b.orderNumber) FROM app_buys b WHERE b.client_id = ?1")
    Long countTotalPurchasesByClient(Long clientId);

    // Query en SQL Nativo - Calcula el total gastado por un cliente específico sumando todas sus compras.
    @Query(nativeQuery = true, value = "SELECT SUM(b.total) FROM app_buys b WHERE b.client_id = ?1")
    BigDecimal calculateTotalSpendingByClient(Long clientId);
}