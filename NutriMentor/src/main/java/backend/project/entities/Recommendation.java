package backend.project.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
@Table(name = "app_recommendation")
public class Recommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;

    @Column(name = "description", nullable = false, length = 200)
    private String description;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "healthGoal_id", nullable = false)
    private HealthGoal healthGoal;
}
