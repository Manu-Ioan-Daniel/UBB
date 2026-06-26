package template.template.network.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateSampleEntityRequest {

    @NotNull
    private String name;

    @NotNull
    @Size(min = 1, max = 100) //example
    private int sampleInt;
}
