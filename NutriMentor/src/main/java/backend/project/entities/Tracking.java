package backend.project.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
@Table(name = "app_tracking")
public class Tracking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "weight", nullable = false, length = 20)
    private String weight;

    @Column(name = "height", nullable = false, length = 20)
    private String height;

    @Column(name = "status", nullable = false, length = 25)
    private String status;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "healthGoal_id", nullable = false)
    private HealthGoal healthGoal;
}

