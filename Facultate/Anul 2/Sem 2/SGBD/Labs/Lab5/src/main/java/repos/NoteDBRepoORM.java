package repos;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import models.Nota;
import models.NotaId;
import utils.HibernateConfig;
import utils.Tuple;
import java.math.BigDecimal;
import java.util.List;

public class  NoteDBRepoORM implements Repository<Tuple<Long,Long>, BigDecimal>{

    private final EntityManagerFactory emf;
    public NoteDBRepoORM(){
        this.emf = HibernateConfig.getEntityManagerFactory();
    }

    @Override
    public BigDecimal findOne(Tuple<Long, Long> id) {
        try(EntityManager em = emf.createEntityManager()){
            NotaId notaId = new NotaId(id.getFirst(), id.getSecond());
            Nota nota = em.find(Nota.class, notaId);
            return nota != null ? nota.getNota() : null;
        }
    }

    @Override
    public List<BigDecimal> findAll() {
        return List.of();
    }

    @Override
    public void save(BigDecimal aBigDecimal) {

    }

    @Override
    public void delete(Tuple<Long, Long> longLongTuple) {

    }

    @Override
    public void update(BigDecimal aBigDecimal) {

    }
}
