package backend.project.serviceimpl;

import backend.project.entities.Recommendation;
import backend.project.exceptions.ResourceNotFoundException;
import backend.project.repositories.RecommendationRepository;
import backend.project.services.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendationServiceImpl implements RecommendationService {

    @Autowired
    private RecommendationRepository recommendationRepository;

    @Override
    public Recommendation updateRecommendation(Recommendation recommendation) {
        Recommendation recommendationFound = findById(recommendation.getId());
        if (recommendationFound == null) {
            throw new ResourceNotFoundException("Recommendation with id: " + recommendation.getId() + " cannot be found");
        }
        recommendationFound.setDescription(recommendation.getDescription());
        return recommendationRepository.save(recommendationFound);
    }

    @Override
    public Recommendation insertRecommendation(Recommendation recommendation) {
        return recommendationRepository.save(recommendation);
    }

    @Override
    public void deleteRecommendation(Long id) {
        Recommendation recommendationFound = findById(id);
        if (recommendationFound == null) {
            throw new ResourceNotFoundException("Recommendation with id: " + id + " cannot be found");
        }
        recommendationRepository.delete(recommendationFound);
    }

    @Override
    public Recommendation findById(Long id) {
        return recommendationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recommendation with id: " + id + " cannot be found"));
    }

    @Override
    public List<Recommendation> listAll() {
        return recommendationRepository.findAll();
    }

    //Querys
    @Override
    public Long countRecommendationsForUser(Long clientId) {
        return recommendationRepository.countRecommendationsForUser(clientId);
    }

    @Override
    public List<Object[]> countRecommendationsByUser() {
        return recommendationRepository.countRecommendationsByUser();
    }
}
