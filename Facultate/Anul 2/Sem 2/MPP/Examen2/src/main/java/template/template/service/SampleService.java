package template.template.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import template.template.domain.Player;
import template.template.repository.SampleRepository;
import template.template.utils.Observer.Observable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Getter
@Service
@RequiredArgsConstructor
public class SampleService extends Observable {

    private final SampleRepository sampleRepository;

    public Optional<Player> findOne(String username) {
        return sampleRepository.findByName(username);
    }

}
