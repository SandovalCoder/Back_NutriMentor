package backend.project.serviceimpl;

import backend.project.entities.Review;
import backend.project.exceptions.InvalidDataException;
import backend.project.exceptions.ResourceNotFoundException;
import backend.project.repositories.ReviewRepository;
import backend.project.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public Review updateReview(Review review) {
        Review reviewFound = findById(review.getId());
        if (reviewFound == null) {
            throw new ResourceNotFoundException("Review with id: " + review.getId() + " cannot be found");
        }
        if (review.getScore() < 0 || review.getScore() > 5) {
            throw new InvalidDataException("Score must be between 0 and 5");
        }
        reviewFound.setScore(review.getScore());
        reviewFound.setComment(review.getComment());
        return reviewRepository.save(reviewFound);
    }

    @Override
    public Review insertReview(Review review) {
        if (review.getScore() < 0 || review.getScore() > 5) {
            throw new InvalidDataException("Score must be between 0 and 5");
        }
        return reviewRepository.save(review);
    }

    @Override
    public void deleteReview(Long id) {
        Review reviewFound = findById(id);
        if (reviewFound == null) {
            throw new ResourceNotFoundException("Review with id: " + id + " cannot be found");
        }
        reviewRepository.delete(reviewFound);
    }

    @Override
    public Review findById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review with id: " + id + " cannot be found"));
    }

    @Override
    public List<Review> listAll() {
        return reviewRepository.findAll();
    }

    //Querys
    @Override
    public List<Object[]> countReviewsAndAverageScoreByUser() {
        return reviewRepository.countReviewsAndAverageScoreByUser();
    }

    @Override
    public List<Object[]> totalReviewScoresByUser() {
        return reviewRepository.totalReviewScoresByUser();
    }

    @Override
    public List<Object[]> countCriticismsByUser() {
        return reviewRepository.countCriticismsByUser();
    }
}
