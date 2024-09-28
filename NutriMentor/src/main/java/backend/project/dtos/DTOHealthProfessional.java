package backend.project.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class DTOHealthProfessional {
    private Long id;
    private String name;
    private String email;
    private String address;

    private Long specializationId;
}
