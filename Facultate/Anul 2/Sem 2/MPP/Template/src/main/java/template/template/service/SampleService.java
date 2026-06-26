package template.template.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import template.template.domain.SampleEntity;
import template.template.network.requests.CreateSampleEntityRequest;
import template.template.repository.SampleRepository;
import template.template.utils.Observer.Observable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Getter
@Service
@RequiredArgsConstructor
public class SampleService extends Observable {

    private final SampleRepository sampleRepository;

    public void example(CreateSampleEntityRequest request) {

        SampleEntity sampleEntity = new SampleEntity();
        sampleEntity.setSampleInt(request.getSampleInt());
        sampleEntity.setName(request.getName());
        if(sampleRepository.findByName(request.getName()).isPresent()){
            throw new IllegalStateException("Sample already exists");
        }
        sampleRepository.save(sampleEntity);
    }

    public Optional<SampleEntity> findOne(String username) {
        return sampleRepository.findByName(username);
    }

}
