package backend.project.serviceimpl;

import backend.project.entities.HealthProfessional;
import backend.project.exceptions.InvalidDataException;
import backend.project.exceptions.KeyRepeatedDataException;
import backend.project.exceptions.ResourceNotFoundException;
import backend.project.repositories.HealthProfessionalRepository;
import backend.project.services.HealthProfessionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HealthProfessionalServiceImpl implements HealthProfessionalService {

    @Autowired
    private HealthProfessionalRepository healthProfessionalRepository;

    public boolean isEmailRegistered(String email) {
        return healthProfessionalRepository.findByEmail(email).isPresent();
    }

    @Override
    public HealthProfessional updateHealthProfessional(HealthProfessional healthProfessional) {
        HealthProfessional healthProfessionalFound = findById(healthProfessional.getId());
        if (healthProfessionalFound != null) {
            if (healthProfessional.getEmail() != null) {
                if (healthProfessional.getEmail().isBlank()) {
                    throw new InvalidDataException("HealthProfessional email cannot be blank");
                }
                if (!healthProfessionalFound.getEmail().equals(healthProfessional.getEmail()) && isEmailRegistered(healthProfessional.getEmail())) {
                    throw new KeyRepeatedDataException("HealthProfessional email: " + healthProfessional.getEmail() + " is already registered");
                }
                healthProfessionalFound.setEmail(healthProfessional.getEmail());
            }
            healthProfessionalFound.setName(healthProfessional.getName());
            healthProfessionalFound.setAddress(healthProfessional.getAddress());
            return healthProfessionalRepository.save(healthProfessionalFound);
        } else {
            throw new ResourceNotFoundException("HealthProfessional with id: " + healthProfessional.getId() + " cannot be found");
        }
    }

    @Override
    public HealthProfessional insertHealthProfessional(HealthProfessional healthProfessional) {
        if (healthProfessional.getEmail() == null || healthProfessional.getEmail().isBlank()) {
            throw new InvalidDataException("HealthProfessional email cannot be blank");
        }
        if (isEmailRegistered(healthProfessional.getEmail())) {
            throw new KeyRepeatedDataException("HealthProfessional email: " + healthProfessional.getEmail() + " is already registered");
        }
        return healthProfessionalRepository.save(healthProfessional);
    }

    @Override
    public void deleteHealthProfessional(Long id) {
        HealthProfessional healthProfessionalFound = findById(id);
        if (healthProfessionalFound != null) {
            healthProfessionalRepository.delete(healthProfessionalFound);
        } else {
            throw new ResourceNotFoundException("HealthProfessional with id: " + id + " cannot be found");
        }
    }

    @Override
    public HealthProfessional findById(Long id) {
        return healthProfessionalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("HealthProfessional with id: " + id + " cannot be found"));
    }

    @Override
    public List<HealthProfessional> listAll() {
        return healthProfessionalRepository.findAll();
    }

    //Querys
    @Override
    public List<HealthProfessional> listAllHealthProfessionals() {
        return healthProfessionalRepository.listAllHealthProfessionals();
    }
}
