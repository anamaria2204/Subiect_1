package joc.persistance;

import joc.model.Joc;
import joc.model.Mutare;

import java.util.List;

public interface IMutareRepo extends IRepository<Integer, Mutare>{
    void save(Mutare mutare);
    List<Mutare> findByJoc(Joc joc);
}
