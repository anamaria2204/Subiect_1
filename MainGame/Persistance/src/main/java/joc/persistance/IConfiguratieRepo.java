package joc.persistance;

import joc.model.Configuratie;

import java.util.Optional;

public interface IConfiguratieRepo extends IRepository<Integer, Configuratie> {
    Optional<Configuratie> save(Configuratie configuratie);
    Optional<Configuratie> getRandomConfiguratie();
}
