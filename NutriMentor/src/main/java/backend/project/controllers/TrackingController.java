package backend.project.controllers;

import backend.project.entities.Tracking;
import backend.project.services.TrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class TrackingController {

    @Autowired
    private TrackingService trackingService;

    @GetMapping("/trackings")
    public ResponseEntity<List<Tracking>> listAllTrackings() {
        return new ResponseEntity<>(trackingService.listAll(), HttpStatus.OK);
    }

    @PostMapping("/trackings")
    public ResponseEntity<Tracking> addTracking(@RequestBody Tracking tracking) {
        Tracking newTracking = trackingService.insertTracking(tracking);
        if (newTracking == null) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(newTracking, HttpStatus.CREATED);
    }

    @DeleteMapping("/trackings/{id}")
    public ResponseEntity<HttpStatus> deleteTracking(@PathVariable("id") Long id) {
        trackingService.deleteTracking(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/trackings/{id}")
    public ResponseEntity<Tracking> updateTracking(@PathVariable("id") Long id, @RequestBody Tracking tracking) {
        tracking.setId(id);
        Tracking updatedTracking = trackingService.updateTracking(tracking);
        return new ResponseEntity<>(updatedTracking, HttpStatus.OK);
    }

    @GetMapping("/trackings/{id}")
    public ResponseEntity<Tracking> detailsById(@PathVariable("id") Long id) {
        Tracking trackingFound = trackingService.findById(id);
        if (trackingFound != null) {
            return new ResponseEntity<>(trackingFound, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
