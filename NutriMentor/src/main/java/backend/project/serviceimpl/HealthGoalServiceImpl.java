package backend.project.serviceimpl;

import backend.project.entities.HealthGoal;
import backend.project.exceptions.ResourceNotFoundException;
import backend.project.repositories.ClientRepository;
import backend.project.repositories.HealthGoalRepository;
import backend.project.repositories.HealthProfessionalRepository;
import backend.project.services.HealthGoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HealthGoalServiceImpl implements HealthGoalService {

    @Autowired
    private HealthGoalRepository healthGoalRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private HealthProfessionalRepository healthProfessionalRepository;

    @Override
    public HealthGoal updateHealthGoal(HealthGoal healthGoal) {
        HealthGoal healthGoalFound = findById(healthGoal.getId());
        if (healthGoalFound == null) {
            throw new ResourceNotFoundException("HealthGoal with id: " + healthGoal.getId() + " cannot be found");
        }
        if (!clientRepository.existsById(healthGoal.getClient().getId())) {
            throw new ResourceNotFoundException("Client with id: " + healthGoal.getClient().getId() + " cannot be found");
        }
        if (!healthProfessionalRepository.existsById(healthGoal.getHealthProfessional().getId())) {
            throw new ResourceNotFoundException("HealthProfessional with id: " + healthGoal.getHealthProfessional().getId() + " cannot be found");
        }
        healthGoalFound.setGoalType(healthGoal.getGoalType());
        healthGoalFound.setNutritionPlan(healthGoal.getNutritionPlan());
        return healthGoalRepository.save(healthGoalFound);
    }

    @Override
    public HealthGoal insertHealthGoal(HealthGoal healthGoal) {
        if (!clientRepository.existsById(healthGoal.getClient().getId())) {
            throw new ResourceNotFoundException("Client with id: " + healthGoal.getClient().getId() + " cannot be found");
        }
        if (!healthProfessionalRepository.existsById(healthGoal.getHealthProfessional().getId())) {
            throw new ResourceNotFoundException("HealthProfessional with id: " + healthGoal.getHealthProfessional().getId() + " cannot be found");
        }
        return healthGoalRepository.save(healthGoal);
    }

    @Override
    public void deleteHealthGoal(Long id) {
        HealthGoal healthGoalFound = findById(id);
        if (healthGoalFound == null) {
            throw new ResourceNotFoundException("HealthGoal with id: " + id + " cannot be found");
        }
        healthGoalRepository.delete(healthGoalFound);
    }

    @Override
    public HealthGoal findById(Long id) {
        return healthGoalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("HealthGoal with id: " + id + " cannot be found"));
    }

    @Override
    public List<HealthGoal> listAll() {
        return healthGoalRepository.findAll();
    }
}
