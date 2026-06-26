package template.template.domain;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@jakarta.persistence.Entity
@Table(name = "jucatori")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Jucator extends Entity {
    private String porecla;
    private int varsta;
}
