package backend.project.services;

import backend.project.dtos.DTOUser;
import backend.project.entities.User;

import java.util.List;

public interface UserService {
    User addUser(DTOUser dtoUser);  // Para registro
    User updateUser(Long id, DTOUser dtoUser);  // Actualizar
    void deleteUser(Long id);  // Eliminar
    User findById(Long id);  // Buscar por ID
    List<User> findAll();  // Listar todos los usuarios
    List<User> findActiveUsers(boolean active);  // Listar usuarios activos
    User findByUsername(String username);  // Buscar por username

    //Querys
    List<User> findUsersByRole(Integer authorityId);
}
