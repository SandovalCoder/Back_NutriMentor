package backend.project.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class DTOTracking {
    private Long id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String weight;
    private String height;
    private String status;

    private Long healthGoalId;
}
