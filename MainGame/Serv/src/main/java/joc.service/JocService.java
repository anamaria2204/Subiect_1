package joc.service;

import joc.model.Configuratie;
import joc.model.Joc;
import joc.model.Jucator;
import joc.model.Mutare;
import joc.persistance.IConfiguratieRepo;
import joc.persistance.IJocRepo;
import joc.persistance.IJucatorRepo;
import joc.persistance.IMutareRepo;
import joc.persistance.hibernate.MutareRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class JocService {

    @Autowired
    private IJocRepo jocRepo;

    @Autowired
    private IJucatorRepo jucatorRepo;

    @Autowired
    private IConfiguratieRepo configuratieRepo;
    @Autowired
    private IMutareRepo mutareRepo;

    @Transactional
    public Optional<Joc> startJoc(String numeJucator) {
        Optional<Jucator> jucatorOpt = jucatorRepo.findByName(numeJucator);
        Optional<Configuratie> configuratieOpt = configuratieRepo.getRandomConfiguratie();

        if (jucatorOpt.isPresent() && configuratieOpt.isPresent()) {
            Joc joc = new Joc(jucatorOpt.get(), configuratieOpt.get());
            return jocRepo.save(joc);
        }
        return Optional.empty();
    }

    public Iterable<Joc> findAll() {
        return jocRepo.findAll();
    }

    public List<Joc> findJocuriFinalizateFaraGhicit(String numeJucator){
        return jocRepo.findJocuriFinalizateFaraGhicit(numeJucator);
    }

    public List<Mutare> findByJoc(Joc joc){
        return mutareRepo.findByJoc(joc);
    }
}
