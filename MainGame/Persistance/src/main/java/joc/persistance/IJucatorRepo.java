package joc.persistance;
import joc.model.Jucator;

import java.util.Optional;

public interface IJucatorRepo extends IRepository<Integer, Jucator>{
    Optional findByName(String name);
}
