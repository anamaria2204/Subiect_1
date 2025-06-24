package joc.networking.controller;

import jakarta.servlet.ServletRequest;
import joc.model.Configuratie;
import joc.model.Joc;
import joc.model.Jucator;
import joc.model.Mutare;
import joc.persistance.IConfiguratieRepo;
import joc.persistance.IJocRepo;
import joc.persistance.IJucatorRepo;
import joc.service.JocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/joc")
public class JocController {

    @Autowired
    private JocService jocService;

    @PostMapping("/start")
    public ResponseEntity<Joc> startJoc(@RequestParam String numeJucator) {
        Optional<Joc> jocOpt = jocService.startJoc(numeJucator);
        return jocOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/toate")
    public ResponseEntity<List<Map<String, Object>>> getToateJocurile() {
        List<Joc> jocuri = (List<Joc>) jocService.findAll();
        List<Map<String, Object>> rezultat = new ArrayList<>();

        for (Joc joc : jocuri) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", joc.getId());
            map.put("jucator", joc.getJucator().getNume());
            map.put("animal", joc.getConfiguratie().getAnimal());
            map.put("linie", joc.getConfiguratie().getLinie());
            map.put("coloana", joc.getConfiguratie().getColoana());

            int mutari = joc.isFinalizat() ? 3 - joc.getMutariRamase() : -1;
            map.put("mutari", mutari);
            map.put("finalizat", joc.isFinalizat());

            rezultat.add(map);
        }

        return ResponseEntity.ok(rezultat);
    }

    @GetMapping("/pierderi/{numeJucator}")
    public ResponseEntity<?> getJocuriPierdute(@PathVariable String numeJucator, ServletRequest servletRequest) {
        List<Joc> jocuri = jocService.findJocuriFinalizateFaraGhicit(numeJucator);
        List<Map<String, Object>> rezultat = new ArrayList<>();

        for (Joc joc : jocuri) {
            Map<String, Object> jocMap = new HashMap<>();
            jocMap.put("jucator", joc.getJucator().getNume());
            jocMap.put("animal", joc.getConfiguratie().getAnimal());
            jocMap.put("pozitieAnimal", Map.of(
                "linie", joc.getConfiguratie().getLinie(),
                "coloana", joc.getConfiguratie().getColoana()
            ));

            List<Mutare> mutari = jocService.findByJoc(joc);
            List<Map<String, Integer>> pozitiiIncercate = mutari.stream()
                .map(m -> Map.of("linie", m.getLinie(), "coloana", m.getColoana()))
                .collect(Collectors.toList());

            jocMap.put("pozitiiIncercate", pozitiiIncercate);
            jocMap.put("nrIncercari", mutari.size());

            rezultat.add(jocMap);
        }

        return ResponseEntity.ok(rezultat);
    }



}
