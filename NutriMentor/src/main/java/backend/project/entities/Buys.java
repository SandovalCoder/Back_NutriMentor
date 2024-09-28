package backend.project.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
@Table(name = "app_buys")
public class Buys {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  orderNumber;

    @Column(name = "order_date", nullable = false)
    private Date orderDate;

    @Column(name = "total", nullable = false)
    private BigDecimal total;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @JsonIgnore
    @OneToMany(mappedBy = "buys", fetch = FetchType.LAZY)
    private List<BuyDetail> buyDetails;
}
