package backend.project.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class DTOCliente {
    private Long id;
    private String nombre;
    private String country;
    private String direccion;
}
