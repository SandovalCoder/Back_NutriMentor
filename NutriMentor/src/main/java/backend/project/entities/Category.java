package backend.project.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.math3.stat.descriptive.summary.Product;

import java.util.List;

@Entity
@Table(name="category")
@Data
@NoArgsConstructor
@AllArgsConstructor


public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;

    @OneToMany(mappedBy = "category",fetch = FetchType.LAZY)
    private List <Product> products;


}
