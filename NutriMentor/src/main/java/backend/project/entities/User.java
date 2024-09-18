package backend.project.entities;


import ch.qos.logback.core.net.server.Client;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    private Boolean active;

    @ManyToOne
    @JoinColumn(name="authority_id")
    private Authority authority;

    @OneToOne
    @JoinColumn(name="client_id")
    private Client client;
    /*Muestra error porque aun no se crea la tabla cleinte :D*/

}
