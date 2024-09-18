package backend.project.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
@Table(name="Compra")
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long nroOrden;

    private LocalDate date;
    private Double total;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="Cliente_id")
    private Cliente cliente;
}
