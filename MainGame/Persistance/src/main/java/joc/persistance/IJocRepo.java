package joc.persistance;

import joc.model.Joc;

import java.util.List;
import java.util.Optional;

public interface IJocRepo extends IRepository<Integer, Joc>{
    Optional<Joc> save (Joc joc);
    Optional<Joc> update(Joc joc, Integer id);
    List<Joc> findJocuriFinalizateFaraGhicit(String numeJucator);
}
