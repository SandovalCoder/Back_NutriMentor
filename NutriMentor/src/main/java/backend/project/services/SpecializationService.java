package backend.project.services;

import backend.project.entities.Specialization;

import java.util.List;

public interface SpecializationService {

    List<Specialization> listAll();

    Specialization insertSpecialization(Specialization specialization);

    Specialization updateSpecialization(Specialization specialization);

    void deleteSpecialization(Long id);

    Specialization findById(Long id);
}
