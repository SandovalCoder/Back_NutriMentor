package backend.project.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class DTOQuestion {
    private Long id;
    private String query;
    private String response;
    private LocalDateTime queryDate;
    private LocalDateTime responseDate;

    private Long clientId;
    private Long healthProfessionalId;
}
