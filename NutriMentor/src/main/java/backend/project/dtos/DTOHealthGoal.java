package backend.project.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class DTOHealthGoal {
    private Long id;
    private String goalType;
    private String nutritionPlan;

    private Long clientId;
    private Long healthProfessionalId;
}
