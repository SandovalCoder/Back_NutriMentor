package backend.project.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class DTOBuys {
    private Long orderNumber;
    private Date orderDate;
    private BigDecimal total;

    private Long clientId;
}
