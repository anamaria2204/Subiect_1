package joc.persistance.hibernate;

import joc.model.Joc;
import joc.persistance.IJocRepo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JocRepo implements IJocRepo {

    private static final Logger logger= LogManager.getLogger();

    @Override
    public Optional findOne(Integer integer) {
        logger.traceEntry("findOne({})", integer);
        try (var session = HibernateUtils.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.find(Joc.class, integer));
        } catch (Exception e) {
            logger.error("Error finding Joc with id {}: {}", integer, e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Iterable<Joc> findAll() {
        logger.traceEntry("findAll()");
        try (var session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("FROM Joc", Joc.class).getResultList();
        } catch (Exception e) {
            logger.error("Error finding all Jocs: {}", e.getMessage());
            return null;
        }
    }

   @Override
    public synchronized Optional<Joc> save(Joc joc) {
        logger.traceEntry("save({})", joc);
        try {
            HibernateUtils.getSessionFactory().inTransaction(session -> {
                session.persist(joc);
                session.flush();
            });
            logger.traceExit();
            return Optional.of(joc);
        } catch (Exception e) {
            logger.error("Eroare la salvarea jocului: {}", e.getMessage());
            return Optional.empty();
        }
    }


    @Override
    public Optional<Joc> update(Joc joc, Integer id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Joc existing = session.get(Joc.class, id);
            if (existing != null) {
                existing.setMutariRamase(joc.getMutariRamase());
                existing.setFinalizat(joc.isFinalizat());
                existing.setEndTime(joc.getEndTime());
            }
            tx.commit();
            return findOne(id);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<Joc> findJocuriFinalizateFaraGhicit(String numeJucator) {
        try (var session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery(
                "FROM Joc j WHERE j.finalizat = true AND j.mutariRamase = 0 AND j.jucator.nume = :nume",
                Joc.class
            ).setParameter("nume", numeJucator).list();
        }
    }



}
