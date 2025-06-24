package joc.networking.controller;

import joc.model.Joc;
import joc.model.Mutare;
import joc.model.Configuratie;
import joc.persistance.IJocRepo;
import joc.persistance.IMutareRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/mutare")
public class MutareController {

    @Autowired
    private IJocRepo jocRepo;

    @Autowired
    private IMutareRepo mutareRepo;

    @PostMapping
    public ResponseEntity<Map<String, String>> mutare(
        @RequestParam int jocId,
        @RequestParam int linie,
        @RequestParam int coloana
        ) {
        Optional<Joc> jocOpt = jocRepo.findOne(jocId);
        if (jocOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("mesaj", "Joc inexistent."));
        }

        Joc joc = jocOpt.get();
        if (joc.isFinalizat()) {
            return ResponseEntity.ok(Map.of("mesaj", "Jocul este deja finalizat."));
        }

        Configuratie config = joc.getConfiguratie();
        boolean ghicit = (config.getLinie() == linie && config.getColoana() == coloana);

        Mutare mutare = new Mutare(joc.getJucator(), joc, linie, coloana, ghicit);
        mutareRepo.save(mutare);

        if (ghicit) {
            joc.setFinalizat(true);
            joc.setMutariRamase(0);
            joc.setEndTime(java.time.LocalDateTime.now());
            jocRepo.update(joc, joc.getId());

            return ResponseEntity.ok(Map.of(
                "mesaj", "Ai ghicit! Animalul era un " + config.getAnimal() + ".",
                "imagineUrl", config.getImagineUrl()
            ));
        }

        joc.setMutariRamase(joc.getMutariRamase() - 1);
        if (joc.getMutariRamase() <= 0) {
            joc.setFinalizat(true);
            joc.setEndTime(java.time.LocalDateTime.now());
            jocRepo.update(joc, joc.getId());
            return ResponseEntity.ok(Map.of(
                "mesaj", "Ai pierdut! Animalul era un " + config.getAnimal() +
                         " la (" + config.getLinie() + "," + config.getColoana() + ")."
            ));
    }

    jocRepo.update(joc, joc.getId());
    String directie = getDirectie(config.getLinie(), config.getColoana(), linie, coloana);
    return ResponseEntity.ok(Map.of("mesaj", "Nu ai ghicit. Direcția este: " + directie));
}


    private String getDirectie(int linieCorecta, int coloanaCorecta, int linieIncercata, int coloanaIncercata) {
        int dLinie = linieCorecta - linieIncercata;
        int dColoana = coloanaCorecta - coloanaIncercata;

        if (dLinie > 0 && dColoana < 0) return "Sud-Vest";
        if (dLinie > 0 && dColoana > 0) return "Sud-Est";
        if (dLinie < 0 && dColoana < 0) return "Nord-Vest";
        if (dLinie < 0 && dColoana > 0) return "Nord-Est";
        if (dLinie > 0) return "Sud";
        if (dLinie < 0) return "Nord";
        if (dColoana < 0) return "Vest";
        if (dColoana > 0) return "Est";
        return "Aceeași poziție";
    }
}
