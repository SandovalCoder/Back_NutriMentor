package backend.project.repositories;

import backend.project.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    // Query en SQL Nativo - Muestra el total de reseñas y promedio de puntuación por usuario.
    @Query(nativeQuery = true, value = "SELECT c.id, c.name, COUNT(r.id) AS reviewCount, AVG(r.score) AS averageScore FROM app_review r JOIN app_client c ON r.client_id = c.id GROUP BY c.id, c.name")
    List<Object[]> countReviewsAndAverageScoreByUser();

    // Query en SQL Nativo - Muestra el total de puntuaciones de reseñas por usuario.
    @Query(nativeQuery = true, value = "SELECT c.id, COUNT(r.id) AS reviewCount, SUM(r.score) AS totalScore FROM app_review r JOIN app_client c ON r.client_id = c.id GROUP BY c.id")
    List<Object[]> totalReviewScoresByUser();

    // Query en SQL Nativo - Cuenta la cantidad de críticas (comentarios) por usuario.
    @Query(nativeQuery = true, value = "SELECT c.id, c.name, COUNT(r.comment) AS criticismCount FROM app_review r JOIN app_client c ON r.client_id = c.id GROUP BY c.id, c.name")
    List<Object[]> countCriticismsByUser();
}