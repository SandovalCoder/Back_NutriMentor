package backend.project.services;

import backend.project.entities.HealthGoal;

import java.util.List;

public interface HealthGoalService {

    List<HealthGoal> listAll();

    HealthGoal insertHealthGoal(HealthGoal healthGoal);

    HealthGoal updateHealthGoal(HealthGoal healthGoal);

    void deleteHealthGoal(Long id);

    HealthGoal findById(Long id);
}
