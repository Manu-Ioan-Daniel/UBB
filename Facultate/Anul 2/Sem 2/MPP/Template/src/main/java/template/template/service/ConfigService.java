package template.template.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import template.template.domain.Config;
import template.template.network.requests.CreateConfigRequest;
import template.template.repository.ConfigRepository;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConfigService {

    private final ConfigRepository configRepository;

    public List<Config> getConfigs(int n){
        return configRepository.getConfigs(n);
    }

    public void saveConfig(CreateConfigRequest request){
        List<Integer> list = Arrays.stream(request.getNumbers().split(",")).map(String::trim).map(Integer::parseInt).toList();
        System.out.println(list);
        if(list.size() != request.getN() * 2){
            throw new IllegalArgumentException("Number of numbers must be 2 * n");
        }
        Config config = new Config();
        config.setN(request.getN());
        config.setNumbers(request.getNumbers());
        configRepository.save(config);

    }

}
