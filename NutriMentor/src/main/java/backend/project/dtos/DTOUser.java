package backend.project.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class DTOUser {
    private Long id;
    private String username;
    private String password;
    private Boolean active;
    private Long clientId;
    private String authorities; // Ejemplo: "ADMIN;USER"
}
