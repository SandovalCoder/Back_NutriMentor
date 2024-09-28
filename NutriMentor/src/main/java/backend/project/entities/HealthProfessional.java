package backend.project.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
@Table(name = "app_healthProfessional")
public class HealthProfessional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;

    @Column(name = "name", nullable = false, length = 25)
    private String name;

    @Column(name = "email", nullable = false, length = 25)
    private String email;

    @Column(name = "address", nullable = false, length = 50)
    private String address;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "specialization_id", nullable = false)
    private Specialization specialization;

    @JsonIgnore
    @OneToMany(mappedBy = "healthProfessional", fetch = FetchType.LAZY)
    private List<HealthGoal> healthGoals;

    @JsonIgnore
    @OneToMany(mappedBy = "healthProfessional", fetch = FetchType.LAZY)
    private List<Question> questions;
}


