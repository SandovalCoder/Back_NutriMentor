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
@Table(name = "app_specialization")
public class Specialization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;

    @Column(name = "specialization_name", nullable = false, length = 25)
    private String specializationName;

    @Column(name = "profile_description", nullable = false)
    private String profileDescription;

    @JsonIgnore
    @OneToMany(mappedBy = "specialization", fetch = FetchType.LAZY)
    private List<HealthProfessional> healthProfessionals;
}
