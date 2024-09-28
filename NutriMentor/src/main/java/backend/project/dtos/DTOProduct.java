package backend.project.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class DTOProduct {
    private Long id;
    private String name;
    private BigDecimal price;
    private Integer stock;

    private Long categoryId;
}
