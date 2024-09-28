package backend.project.services;

import backend.project.entities.Tracking;

import java.util.List;

public interface TrackingService {

    List<Tracking> listAll();

    Tracking insertTracking(Tracking tracking);

    Tracking updateTracking(Tracking tracking);

    void deleteTracking(Long id);

    Tracking findById(Long id);
}
