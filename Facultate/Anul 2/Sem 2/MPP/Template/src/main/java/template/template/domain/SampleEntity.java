package template.template.domain;


import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@jakarta.persistence.Entity
@Table(name = "sample_entities")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class SampleEntity extends Entity {
    private String name;
    private int sampleInt;
}
