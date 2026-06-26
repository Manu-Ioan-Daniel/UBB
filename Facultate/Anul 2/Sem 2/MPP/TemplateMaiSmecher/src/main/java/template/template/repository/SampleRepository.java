package template.template.repository;

import template.template.domain.SampleEntity;
import java.util.Optional;

public interface SampleRepository extends BaseRepository<SampleEntity, Long> {
    Optional<SampleEntity> findByName(String nume);
}