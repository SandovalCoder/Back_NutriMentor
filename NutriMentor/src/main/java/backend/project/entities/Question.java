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
@Table(name = "app_question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;

    @Column(name = "query", nullable = false, length = 100)
    private String query;

    @Column(name = "response", nullable = false, length = 100)
    private String response;

    @Column(name = "query_date", nullable = false)
    private LocalDateTime queryDate;

    @Column(name = "response_date", nullable = false)
    private LocalDateTime responseDate;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "healthProfessional_id", nullable = false)
    private HealthProfessional healthProfessional;
}

