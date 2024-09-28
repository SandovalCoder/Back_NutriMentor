package backend.project.controllers;

import backend.project.entities.Review;
import backend.project.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/reviews")
    public ResponseEntity<List<Review>> listAllReviews() {
        return new ResponseEntity<>(reviewService.listAll(), HttpStatus.OK);
    }

    @PostMapping("/reviews")
    public ResponseEntity<Review> addReview(@RequestBody Review review) {
        Review newReview = reviewService.insertReview(review);
        if (newReview == null) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(newReview, HttpStatus.CREATED);
    }

    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<HttpStatus> deleteReview(@PathVariable("id") Long id) {
        reviewService.deleteReview(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/reviews/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable("id") Long id, @RequestBody Review review) {
        review.setId(id);
        Review updatedReview = reviewService.updateReview(review);
        return new ResponseEntity<>(updatedReview, HttpStatus.OK);
    }

    @GetMapping("/reviews/{id}")
    public ResponseEntity<Review> detailsById(@PathVariable("id") Long id) {
        Review reviewFound = reviewService.findById(id);
        if (reviewFound != null) {
            return new ResponseEntity<>(reviewFound, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Querys

    @GetMapping("/reviews/average-score")
    public ResponseEntity<List<Object[]>> countReviewsAndAverageScoreByUser() {
        List<Object[]> result = reviewService.countReviewsAndAverageScoreByUser();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/reviews/total-scores")
    public ResponseEntity<List<Object[]>> totalReviewScoresByUser() {
        List<Object[]> result = reviewService.totalReviewScoresByUser();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/reviews/criticisms")
    public ResponseEntity<List<Object[]>> countCriticismsByUser() {
        List<Object[]> result = reviewService.countCriticismsByUser();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
