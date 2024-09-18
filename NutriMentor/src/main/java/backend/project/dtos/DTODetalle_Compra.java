package backend.project.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class DTODetalle_Compra {
    private Long id;
    private Long cantidad;
    private Long subtotal;


    private Long productoId;
    private Long compraId;
}
