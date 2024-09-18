package backend.project.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class DTOCompra {
    private Long nroOrden;
    private LocalDate date;
    private Double total;

    private Long clienteId;
}
