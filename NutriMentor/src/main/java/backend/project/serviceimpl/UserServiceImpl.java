package backend.project.serviceimpl;

import backend.project.dtos.DTOToken;
import backend.project.dtos.DTOUser;
import backend.project.entities.Authority;
import backend.project.entities.Client;
import backend.project.entities.User;
import backend.project.exceptions.InvalidDataException;
import backend.project.exceptions.KeyRepeatedDataException;
import backend.project.exceptions.ResourceNotFoundException;
import backend.project.repositories.ClientRepository;
import backend.project.repositories.UserRepository;
import backend.project.security.JwtUtilService;
import backend.project.security.SecurityUser;
import backend.project.services.AuthorityService;
import backend.project.services.ClientService;
import backend.project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientService clientService;

    @Autowired
    private AuthorityService authorityService;

    // Método para convertir la lista de authorities desde un string
    private List<Authority> authorityListFromString(String authorityString) {
        List<String> authorityListString = List.of(authorityString.split(";"));
        List<Authority> authorityList = new ArrayList<>();

        for (String authorityStringItem : authorityListString) {
            Authority authority = authorityService.findByName(authorityStringItem);
            authorityList.add(authority);
        }

        return authorityList;
    }

   /* @Override
    public User addUser(DTOUser dtoUser) {
        // Verificar si el username ya está registrado
        if (userRepository.findByUsername(dtoUser.getUsername()).isPresent()) {
            throw new KeyRepeatedDataException("Username is already registered.");
        }

        // Buscar el cliente asociado por clientId
        Client client = clientService.findById(dtoUser.getClientId());

        // Crear el nuevo usuario
        User newUser = new User();
        newUser.setUsername(dtoUser.getUsername());

        // En lugar de inyectar el PasswordEncoder, simplemente creamos una instancia local aquí
        newUser.setPassword(new BCryptPasswordEncoder().encode(dtoUser.getPassword()));

        newUser.setActive(dtoUser.getActive());
        newUser.setClient(client);  // Asociamos el cliente
        newUser.setAuthorities(authorityListFromString(dtoUser.getAuthorities()));  // Asignamos las autoridades

        // Guardar el usuario
        return userRepository.save(newUser);
    }*/

    @Override
    public User addUser(DTOUser dtoUser) {
        // Verificar si el nombre de usuario ya está registrado
        // Si el username ya existe en la base de datos, se lanza una excepción para evitar duplicados
        if (userRepository.findByUsername(dtoUser.getUsername()).isPresent()) {
            throw new KeyRepeatedDataException("El nombre de usuario ya está registrado.");
        }

        // Buscar el cliente asociado utilizando el clientId proporcionado
        // Esto asegura que el cliente existe antes de asociarlo con el nuevo usuario
        Client client = clientService.findById(dtoUser.getClientId());

        // Crear una nueva entidad User
        User newUser = new User();
        // Asignar el nombre de usuario (username) a la nueva entidad
        newUser.setUsername(dtoUser.getUsername());

        // Validar la contraseña (longitud mínima, complejidad, etc.) y encriptarla
        // Si la contraseña tiene menos de 8 caracteres, se lanza una excepción
        if (dtoUser.getPassword().length() < 8) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 8 caracteres.");
        }
        // Encriptar la contraseña usando BCrypt antes de guardarla en la base de datos
        newUser.setPassword(new BCryptPasswordEncoder().encode(dtoUser.getPassword()));

        // Establecer el estado activo del usuario
        // Esto indica si el usuario está habilitado o no
        newUser.setActive(dtoUser.getActive());

        // Asociar el usuario con el cliente previamente encontrado
        newUser.setClient(client);

        // Asignar las autoridades (roles) al usuario a partir del string en DTOUser
        // Convierte la lista de roles (authorities) en entidades Authority
        newUser.setAuthorities(authorityListFromString(dtoUser.getAuthorities()));

        // Guardar y devolver la nueva entidad User en la base de datos
        return userRepository.save(newUser);
    }

    //Faltaria el token
    @Override
    public User updateUser(Long id, DTOUser dtoUser) {
        User user = findById(id);  // Buscamos el usuario por ID

        // Actualizamos el username si se proporciona y no está vacío
        if (dtoUser.getUsername() != null && !dtoUser.getUsername().isBlank()) {
            if (!user.getUsername().equals(dtoUser.getUsername()) && userRepository.findByUsername(dtoUser.getUsername()).isPresent()) {
                throw new KeyRepeatedDataException("Username is already registered.");
            }
            user.setUsername(dtoUser.getUsername());
        }

        // Actualizamos la contraseña si se proporciona
        if (dtoUser.getPassword() != null && !dtoUser.getPassword().isBlank()) {
            user.setPassword(new BCryptPasswordEncoder().encode(dtoUser.getPassword()));
        }

        // Actualizamos el estado de activo
        user.setActive(dtoUser.getActive());

        // Si se proporcionan nuevas authorities, las actualizamos
        if (dtoUser.getAuthorities() != null) {
            user.setAuthorities(authorityListFromString(dtoUser.getAuthorities()));
        }

        // Guardamos los cambios
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        User user = findById(id);
        userRepository.delete(user);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<User> findActiveUsers(boolean active) {
        return userRepository.findByActive(active);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
    }

    // Implementación de UserDetailsService para Spring Security
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        return new SecurityUser(user);  // Devolvemos un objeto que implementa UserDetails
    }

    //Querys
    @Override
    public List<User> findUsersByRole(Integer authorityId) {
        return userRepository.findUsersByRole(authorityId);
    }
}
