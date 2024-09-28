package backend.project.repositories;

import backend.project.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);  // Usar Optional para evitar nulls
    List<User> findByActive(boolean active);  // Filtrar usuarios activos

    // Query en SQL Nativo - Busca usuarios según su rol.
    @Query(nativeQuery = true, value = "SELECT * FROM app_user WHERE authority_id = ?1")
    List<User> findUsersByRole(Integer authorityId);
}