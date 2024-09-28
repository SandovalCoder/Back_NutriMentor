package backend.project.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class DTOReview {
    private Long id;
    private int score;
    private String comment;

    private Long productId;
    private Long clientId;
}
