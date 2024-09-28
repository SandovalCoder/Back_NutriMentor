package backend.project.controllers;

import backend.project.dtos.DTOToken;
import backend.project.dtos.DTOUser;
import backend.project.entities.Authority;
import backend.project.entities.User;
import backend.project.security.JwtUtilService;
import backend.project.security.SecurityUser;
import backend.project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtilService jwtUtilService;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Endpoint para registrar un nuevo usuario.
     * Genera un token JWT inmediatamente después de registrar.
     * URL: http://localhost:8080/api/users/register
     */
    @PostMapping("/users/register")
    public ResponseEntity<DTOToken> register(@RequestBody DTOUser dtoUser) {
        // Agregar el usuario
        User newUser = userService.addUser(dtoUser);

        // Generar el token JWT inmediatamente después del registro
        String jwtToken = jwtUtilService.generateToken(new SecurityUser(newUser));

        // Crear y devolver el objeto DTOToken
        DTOToken dtoToken = new DTOToken();
        dtoToken.setUserId(newUser.getId());
        dtoToken.setAuthorities(newUser.getAuthorities().stream()
                .map(Authority::getName)
                .collect(Collectors.joining(";", "", ""))
        );
        dtoToken.setJwtToken(jwtToken);

        return new ResponseEntity<>(dtoToken, HttpStatus.CREATED);
    }

    /**
     * Endpoint para hacer login y generar un token JWT.
     * URL: http://localhost:8080/api/users/login
     */
    @PostMapping("/users/login")
    public ResponseEntity<DTOToken> login(@RequestBody DTOUser dtoUser) {
        // Autenticar el usuario
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dtoUser.getUsername(), dtoUser.getPassword())
        );

        // Buscar el usuario después de la autenticación
        User userFound = userService.findByUsername(dtoUser.getUsername());

        // Generar el token JWT
        String jwtToken = jwtUtilService.generateToken(new SecurityUser(userFound));

        // Crear el objeto DTOToken con los detalles del usuario y el token
        DTOToken dtoToken = new DTOToken();
        dtoToken.setUserId(userFound.getId());
        dtoToken.setAuthorities(userFound.getAuthorities().stream()
                .map(Authority::getName)
                .collect(Collectors.joining(";", "", ""))
        );
        dtoToken.setJwtToken(jwtToken);

        return new ResponseEntity<>(dtoToken, HttpStatus.OK);
    }
    //Mas metodos
// Get user by ID
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.findById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // Get all users
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // Update a user
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody DTOUser dtoUser) {
        User updatedUser = userService.updateUser(id, dtoUser);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    // Delete a user
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Get active users
    @GetMapping("/users/active/{status}")
    public ResponseEntity<List<User>> findActiveUsers(@PathVariable boolean status) {
        List<User> activeUsers = userService.findActiveUsers(status);
        return new ResponseEntity<>(activeUsers, HttpStatus.OK);
    }

    // Find user by username
    @GetMapping("/users/by-username/{username}")
    public ResponseEntity<User> findByUsername(@PathVariable String username) {
        User user = userService.findByUsername(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    //Querys
    @GetMapping("/users/by-role/{authorityId}")
    public ResponseEntity<List<User>> findUsersByRole(@PathVariable Integer authorityId) {
        List<User> users = userService.findUsersByRole(authorityId);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
