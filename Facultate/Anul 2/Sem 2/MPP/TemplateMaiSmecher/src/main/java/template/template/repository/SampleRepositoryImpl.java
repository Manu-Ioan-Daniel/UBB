package template.template.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import template.template.domain.SampleEntity;
import java.util.Optional;

@Repository
public class SampleRepositoryImpl extends AbstractHibernateRepository<SampleEntity, Long> implements SampleRepository {

    public SampleRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory, SampleEntity.class);
    }

    @Override
    public Optional<SampleEntity> findByName(String nume) {
        try (Session session = sessionFactory.openSession()) {
            SampleEntity entity = session.createQuery(
                            "FROM SampleEntity WHERE name = :nume", SampleEntity.class)
                    .setParameter("nume", nume)
                    .uniqueResult();

            return Optional.ofNullable(entity);
        }
    }


}