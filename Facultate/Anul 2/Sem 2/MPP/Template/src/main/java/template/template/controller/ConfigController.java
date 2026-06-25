package template.template.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import template.template.domain.Config;
import template.template.network.requests.ChooseConfigRequest;
import template.template.network.requests.CreateConfigRequest;
import template.template.service.ConfigService;
import template.template.service.SampleGameState;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin
public class ConfigController {

    private final ConfigService configService;
    private final SampleGameState sampleGameState;

    @PostMapping("/config")
    public ResponseEntity<String> chooseConfig(@RequestBody ChooseConfigRequest request) {

        Config config = new Config();
        config.setN(request.getN());
        config.setNumbers(request.getNumbers());
        sampleGameState.setCurrentConfig(config);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/create-config")
    public ResponseEntity<?> saveConfig(@RequestBody CreateConfigRequest request){
        try {
            configService.saveConfig(request);
            return ResponseEntity.ok().build();
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
