package backend.project.controllers;

import backend.project.entities.Specialization;
import backend.project.services.SpecializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class SpecializationController {

    @Autowired
    private SpecializationService specializationService;

    @GetMapping("/specializations")
    public ResponseEntity<List<Specialization>> listAllSpecializations() {
        return new ResponseEntity<>(specializationService.listAll(), HttpStatus.OK);
    }

    @PostMapping("/specializations")
    public ResponseEntity<Specialization> addSpecialization(@RequestBody Specialization specialization) {
        Specialization newSpecialization = specializationService.insertSpecialization(specialization);
        if (newSpecialization == null) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(newSpecialization, HttpStatus.CREATED);
    }

    @DeleteMapping("/specializations/{id}")
    public ResponseEntity<HttpStatus> deleteSpecialization(@PathVariable("id") Long id) {
        specializationService.deleteSpecialization(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/specializations/{id}")
    public ResponseEntity<Specialization> updateSpecialization(@PathVariable("id") Long id, @RequestBody Specialization specialization) {
        specialization.setId(id);
        Specialization updatedSpecialization = specializationService.updateSpecialization(specialization);
        return new ResponseEntity<>(updatedSpecialization, HttpStatus.OK);
    }

    @GetMapping("/specializations/{id}")
    public ResponseEntity<Specialization> detailsById(@PathVariable("id") Long id) {
        Specialization specializationFound = specializationService.findById(id);
        if (specializationFound != null) {
            return new ResponseEntity<>(specializationFound, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
