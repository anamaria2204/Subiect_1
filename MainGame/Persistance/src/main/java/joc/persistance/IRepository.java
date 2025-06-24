package joc.persistance;
import java.util.Optional;

public interface IRepository<ID, E> {
   Optional findOne(ID id);
   Iterable<E> findAll();
}
