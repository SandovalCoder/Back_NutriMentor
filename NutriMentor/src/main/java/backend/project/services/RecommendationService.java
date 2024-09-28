package backend.project.services;

import backend.project.entities.Recommendation;

import java.util.List;

public interface RecommendationService {

    List<Recommendation> listAll();

    Recommendation insertRecommendation(Recommendation recommendation);

    Recommendation updateRecommendation(Recommendation recommendation);

    void deleteRecommendation(Long id);

    Recommendation findById(Long id);

    //Querys
    Long countRecommendationsForUser(Long clientId);
    List<Object[]> countRecommendationsByUser();
}
