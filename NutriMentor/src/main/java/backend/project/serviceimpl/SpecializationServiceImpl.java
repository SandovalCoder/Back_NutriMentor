package backend.project.serviceimpl;

import backend.project.entities.Specialization;
import backend.project.exceptions.InvalidDataException;
import backend.project.exceptions.KeyRepeatedDataException;
import backend.project.exceptions.ResourceNotFoundException;
import backend.project.repositories.SpecializationRepository;
import backend.project.services.SpecializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecializationServiceImpl implements SpecializationService {

    @Autowired
    private SpecializationRepository specializationRepository;

    public boolean isSpecializationRegistered(String specialization) {
        return specializationRepository.findBySpecializationName(specialization).isPresent();
    }

    @Override
    public Specialization updateSpecialization(Specialization specialization) {
        Specialization specializationFound = findById(specialization.getId());
        if (specializationFound != null) {
            if (specialization.getSpecializationName() != null) {
                if (specialization.getSpecializationName().isBlank()) {
                    throw new InvalidDataException("Specialization name cannot be blank");
                }
                if (!specializationFound.getSpecializationName().equals(specialization.getSpecializationName())
                        && isSpecializationRegistered(specialization.getSpecializationName())) {
                    throw new KeyRepeatedDataException("Specialization: " + specialization.getSpecializationName() + " is already registered");
                }
                specializationFound.setSpecializationName(specialization.getSpecializationName());
            }
            specializationFound.setProfileDescription(specialization.getProfileDescription());
            return specializationRepository.save(specializationFound);
        } else {
            throw new ResourceNotFoundException("Specialization with id: " + specialization.getId() + " cannot be found");
        }
    }

    @Override
    public Specialization insertSpecialization(Specialization specialization) {
        if (specialization.getSpecializationName() == null || specialization.getSpecializationName().isBlank()) {
            throw new InvalidDataException("Specialization name cannot be blank");
        }
        if (isSpecializationRegistered(specialization.getSpecializationName())) {
            throw new KeyRepeatedDataException("Specialization: " + specialization.getSpecializationName() + " is already registered");
        }
        return specializationRepository.save(specialization);
    }

    @Override
    public void deleteSpecialization(Long id) {
        Specialization specializationFound = findById(id);
        if (specializationFound != null) {
            specializationRepository.delete(specializationFound);
        } else {
            throw new ResourceNotFoundException("Specialization with id: " + id + " cannot be found");
        }
    }

    @Override
    public Specialization findById(Long id) {
        return specializationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Specialization with id: " + id + " cannot be found"));
    }

    @Override
    public List<Specialization> listAll() {
        return specializationRepository.findAll();
    }
}
