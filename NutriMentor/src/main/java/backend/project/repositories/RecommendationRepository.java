package backend.project.repositories;

import backend.project.entities.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
    // Query en SQL Nativo - Cuenta las recomendaciones hechas a un usuario para ver qué tanto ha utilizado la plataforma.
    @Query(nativeQuery = true, value = "SELECT COUNT(r.id) FROM app_recommendation r JOIN app_healthGoal hg ON r.healthGoal_id = hg.id WHERE hg.client_id = ?1")
    Long countRecommendationsForUser(Long clientId);

    // Query en SQL Nativo - Cuenta la cantidad de recomendaciones por cada usuario para evaluar la eficacia de las recomendaciones ofrecidas.
    @Query(nativeQuery = true, value = "SELECT c.id, c.name, COUNT(r.id) AS recommendationCount FROM app_recommendation r JOIN app_healthGoal hg ON r.healthGoal_id = hg.id JOIN app_client c ON hg.client_id = c.id GROUP BY c.id, c.name")
    List<Object[]> countRecommendationsByUser();
}