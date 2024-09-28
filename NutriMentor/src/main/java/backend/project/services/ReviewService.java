package backend.project.services;

import backend.project.entities.Review;

import java.util.List;

public interface ReviewService {

    List<Review> listAll();

    Review insertReview(Review review);

    Review updateReview(Review review);

    void deleteReview(Long id);

    Review findById(Long id);

    //Querys
    List<Object[]> countReviewsAndAverageScoreByUser();

    List<Object[]> totalReviewScoresByUser();

    List<Object[]> countCriticismsByUser();
}
