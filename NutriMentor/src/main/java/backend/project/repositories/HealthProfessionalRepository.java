package backend.project.repositories;

import backend.project.entities.HealthProfessional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HealthProfessionalRepository extends JpaRepository<HealthProfessional, Long> {
    Optional<HealthProfessional> findByEmail(String email);

    // Query en SQL Nativo - Lista todos los profesionales de la salud.
    @Query(nativeQuery = true, value = "SELECT * FROM app_healthProfessional")
    List<HealthProfessional> listAllHealthProfessionals();
}

