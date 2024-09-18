package backend.project.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
@Table(name="Detalle_Compra")
public class DetalleCompra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long cantidad;
    private Long subtotal;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="Producto_id")
    private Producto producto;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="Compra_nroOrden")
    private Compra compra;

}
