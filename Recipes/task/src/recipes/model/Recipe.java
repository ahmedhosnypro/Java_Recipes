package recipes.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Recipe entity
 * @author Ahmed Hosny
 * @version 1.0
 * @since 2023-04-07
 */
@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity(name = "recipe")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String name;

    private String category;

    private String description;

    @UpdateTimestamp
    private LocalDateTime date = LocalDateTime.now();

    @ElementCollection
    @CollectionTable(name = "ingredient", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "ingredients")
    private List<String> ingredients;

    @ElementCollection
    @CollectionTable(name = "direction", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "directions")
    private List<String> directions;
}
