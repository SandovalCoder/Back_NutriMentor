package backend.project.serviceimpl;

import backend.project.entities.Tracking;
import backend.project.exceptions.ResourceNotFoundException;
import backend.project.repositories.TrackingRepository;
import backend.project.services.TrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackingServiceImpl implements TrackingService {

    @Autowired
    private TrackingRepository trackingRepository;

    @Override
    public Tracking updateTracking(Tracking tracking) {
        Tracking trackingFound = findById(tracking.getId());
        if (trackingFound != null) {
            trackingFound.setStartDate(tracking.getStartDate());
            trackingFound.setEndDate(tracking.getEndDate());
            trackingFound.setWeight(tracking.getWeight());
            trackingFound.setHeight(tracking.getHeight());
            trackingFound.setStatus(tracking.getStatus());
            return trackingRepository.save(trackingFound);
        } else {
            throw new ResourceNotFoundException("Tracking with id: " + tracking.getId() + " cannot be found");
        }
    }

    @Override
    public Tracking insertTracking(Tracking tracking) {
        return trackingRepository.save(tracking);
    }

    @Override
    public void deleteTracking(Long id) {
        Tracking trackingFound = findById(id);
        if (trackingFound != null) {
            trackingRepository.delete(trackingFound);
        } else {
            throw new ResourceNotFoundException("Tracking with id: " + id + " cannot be found");
        }
    }

    @Override
    public Tracking findById(Long id) {
        return trackingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tracking with id: " + id + " cannot be found"));
    }

    @Override
    public List<Tracking> listAll() {
        return trackingRepository.findAll();
    }
}
