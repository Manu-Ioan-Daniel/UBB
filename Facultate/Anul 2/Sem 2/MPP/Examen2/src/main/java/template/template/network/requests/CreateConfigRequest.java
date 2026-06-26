package template.template.network.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateConfigRequest {

    @NotNull
    private int n;

    @NotNull
    private String numbers;

}
