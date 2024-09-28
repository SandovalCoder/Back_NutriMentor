package backend.project.controllers;

import backend.project.entities.HealthGoal;
import backend.project.services.HealthGoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class HealthGoalController {

    @Autowired
    private HealthGoalService healthGoalService;

    @GetMapping("/health-goals")
    public ResponseEntity<List<HealthGoal>> listAllHealthGoals() {
        return new ResponseEntity<>(healthGoalService.listAll(), HttpStatus.OK);
    }

    @PostMapping("/health-goals")
    public ResponseEntity<HealthGoal> addHealthGoal(@RequestBody HealthGoal healthGoal) {
        HealthGoal newHealthGoal = healthGoalService.insertHealthGoal(healthGoal);
        if (newHealthGoal == null) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(newHealthGoal, HttpStatus.CREATED);
    }

    @DeleteMapping("/health-goals/{id}")
    public ResponseEntity<HttpStatus> deleteHealthGoal(@PathVariable("id") Long id) {
        healthGoalService.deleteHealthGoal(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/health-goals/{id}")
    public ResponseEntity<HealthGoal> updateHealthGoal(@PathVariable("id") Long id, @RequestBody HealthGoal healthGoal) {
        healthGoal.setId(id);
        HealthGoal updatedHealthGoal = healthGoalService.updateHealthGoal(healthGoal);
        return new ResponseEntity<>(updatedHealthGoal, HttpStatus.OK);
    }

    @GetMapping("/health-goals/{id}")
    public ResponseEntity<HealthGoal> detailsById(@PathVariable("id") Long id) {
        HealthGoal healthGoalFound = healthGoalService.findById(id);
        if (healthGoalFound != null) {
            return new ResponseEntity<>(healthGoalFound, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
