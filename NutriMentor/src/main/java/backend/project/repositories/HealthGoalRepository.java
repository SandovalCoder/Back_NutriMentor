package backend.project.repositories;

import backend.project.entities.HealthGoal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthGoalRepository extends JpaRepository<HealthGoal, Long> {
}