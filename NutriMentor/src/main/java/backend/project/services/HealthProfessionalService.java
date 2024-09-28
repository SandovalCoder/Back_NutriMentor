package backend.project.services;

import backend.project.entities.HealthProfessional;

import java.util.List;

public interface HealthProfessionalService {

    List<HealthProfessional> listAll();

    HealthProfessional insertHealthProfessional(HealthProfessional healthProfessional);

    HealthProfessional updateHealthProfessional(HealthProfessional healthProfessional);

    void deleteHealthProfessional(Long id);

    HealthProfessional findById(Long id);

    //Querys
    List<HealthProfessional> listAllHealthProfessionals();
}
