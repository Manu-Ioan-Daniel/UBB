package template.template.domain;


import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@jakarta.persistence.Entity
@Table(name = "players")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Player extends Entity {
    private String name;
}
