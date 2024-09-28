package backend.project.controllers;

import backend.project.entities.HealthProfessional;
import backend.project.services.HealthProfessionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class HealthProfessionalController {

    @Autowired
    private HealthProfessionalService healthProfessionalService;

    @GetMapping("/health-professionals")
    public ResponseEntity<List<HealthProfessional>> listAllHealthProfessionals() {
        return new ResponseEntity<>(healthProfessionalService.listAll(), HttpStatus.OK);
    }

    @PostMapping("/health-professionals")
    public ResponseEntity<HealthProfessional> addHealthProfessional(@RequestBody HealthProfessional healthProfessional) {
        HealthProfessional newHealthProfessional = healthProfessionalService.insertHealthProfessional(healthProfessional);
        if (newHealthProfessional == null) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(newHealthProfessional, HttpStatus.CREATED);
    }

    @DeleteMapping("/health-professionals/{id}")
    public ResponseEntity<HttpStatus> deleteHealthProfessional(@PathVariable("id") Long id) {
        healthProfessionalService.deleteHealthProfessional(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/health-professionals/{id}")
    public ResponseEntity<HealthProfessional> updateHealthProfessional(@PathVariable("id") Long id, @RequestBody HealthProfessional healthProfessional) {
        healthProfessional.setId(id);
        HealthProfessional updatedHealthProfessional = healthProfessionalService.updateHealthProfessional(healthProfessional);
        return new ResponseEntity<>(updatedHealthProfessional, HttpStatus.OK);
    }

    @GetMapping("/health-professionals/{id}")
    public ResponseEntity<HealthProfessional> detailsById(@PathVariable("id") Long id) {
        HealthProfessional healthProfessionalFound = healthProfessionalService.findById(id);
        if (healthProfessionalFound != null) {
            return new ResponseEntity<>(healthProfessionalFound, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Querys

    @GetMapping("/health-professionals/all")
    public ResponseEntity<List<HealthProfessional>> getAllHealthProfessionals() {
        List<HealthProfessional> professionals = healthProfessionalService.listAllHealthProfessionals();
        return new ResponseEntity<>(professionals, HttpStatus.OK);
    }

}
