package backend.project.controllers;

import backend.project.entities.Recommendation;
import backend.project.services.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;

    @GetMapping("/recommendations")
    public ResponseEntity<List<Recommendation>> listAllRecommendations() {
        return new ResponseEntity<>(recommendationService.listAll(), HttpStatus.OK);
    }

    @PostMapping("/recommendations")
    public ResponseEntity<Recommendation> addRecommendation(@RequestBody Recommendation recommendation) {
        Recommendation newRecommendation = recommendationService.insertRecommendation(recommendation);
        if (newRecommendation == null) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(newRecommendation, HttpStatus.CREATED);
    }

    @DeleteMapping("/recommendations/{id}")
    public ResponseEntity<HttpStatus> deleteRecommendation(@PathVariable("id") Long id) {
        recommendationService.deleteRecommendation(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/recommendations/{id}")
    public ResponseEntity<Recommendation> updateRecommendation(@PathVariable("id") Long id, @RequestBody Recommendation recommendation) {
        recommendation.setId(id);
        Recommendation updatedRecommendation = recommendationService.updateRecommendation(recommendation);
        return new ResponseEntity<>(updatedRecommendation, HttpStatus.OK);
    }

    @GetMapping("/recommendations/{id}")
    public ResponseEntity<Recommendation> detailsById(@PathVariable("id") Long id) {
        Recommendation recommendationFound = recommendationService.findById(id);
        if (recommendationFound != null) {
            return new ResponseEntity<>(recommendationFound, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Querys
    @GetMapping("/recommendations/user-count/{clientId}")
    public ResponseEntity<Long> countRecommendationsForUser(@PathVariable Long clientId) {
        Long count = recommendationService.countRecommendationsForUser(clientId);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @GetMapping("/recommendations/by-user")
    public ResponseEntity<List<Object[]>> countRecommendationsByUser() {
        List<Object[]> count = recommendationService.countRecommendationsByUser();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
}
