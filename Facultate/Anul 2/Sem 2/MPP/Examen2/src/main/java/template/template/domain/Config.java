package template.template.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import java.util.Arrays;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "configs")
public class Config extends template.template.domain.Entity {


    private int n;
    private String numbers;

    public List<Integer> getNumbersList(){
        if (numbers == null) {
            return java.util.Collections.emptyList();
        }
        return Arrays.stream(numbers.split(",")).map((Integer::parseInt)).toList();
    }
}
