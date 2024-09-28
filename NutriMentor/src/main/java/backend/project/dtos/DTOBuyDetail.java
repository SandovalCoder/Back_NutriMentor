package backend.project.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class DTOBuyDetail {
    private Long id;
    private Integer quantity;
    private BigDecimal subtotal;

    private Long buysId;
    private Long productId;
}
