package joc.persistance.hibernate;

import joc.model.Joc;
import joc.model.Mutare;
import joc.persistance.IMutareRepo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MutareRepo implements IMutareRepo {

    private static final Logger logger= LogManager.getLogger();

    @Override
    public void save(Mutare mutare) {
         logger.traceEntry("save({})", mutare);
        HibernateUtils.getSessionFactory().inTransaction(session -> {
            session.persist(mutare);
            session.flush();
        });
        logger.traceExit();
    }

    @Override
    public Optional findOne(Integer integer) {
        logger.traceEntry("findOne({})", integer);
        try (var session = HibernateUtils.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.find(Mutare.class, integer));
        } catch (Exception e) {
            logger.error("Error finding Mutare with id {}: {}", integer, e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Iterable<Mutare> findAll() {
        logger.traceEntry("findAll()");
        try (var session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("FROM Mutare", Mutare.class).getResultList();
        } catch (Exception e) {
            logger.error("Error finding all Mutari: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public List<Mutare> findByJoc(Joc joc) {
        try (var session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("FROM Mutare m WHERE m.joc = :joc", Mutare.class)
                          .setParameter("joc", joc)
                          .list();
        }
    }

}
