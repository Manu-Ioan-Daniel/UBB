package template.template.domain;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@jakarta.persistence.Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "statistici_joc")
public class StatisticiJoc extends Entity {

    private String playerName;
    private String numeJoc;
    private String pozitii;
    private String punctaje;
    private Long punctajCastigat;

}
