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
@Table(name = "app_healthGoal")
public class HealthGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;

    @Column(name = "goal_type", nullable = false, length = 20)
    private String goalType;

    @Column(name = "nutrition_plan", nullable = false, length = 20)
    private String nutritionPlan;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "healthProfessional_id", nullable = false)
    private HealthProfessional healthProfessional;

    @JsonIgnore
    @OneToMany(mappedBy = "healthGoal", fetch = FetchType.LAZY)
    private List<Recommendation> recommendations;

    @JsonIgnore
    @OneToMany(mappedBy = "healthGoal", fetch = FetchType.LAZY)
    private List<Tracking> trackings;
}

