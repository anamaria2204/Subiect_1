package joc.networking.controller;

import joc.model.Configuratie;
import joc.model.Jucator;
import joc.persistance.IConfiguratieRepo;
import joc.persistance.IJucatorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/configuratie")
public class ConfiguratieController {

    private final IConfiguratieRepo configuratieRepo;

    @Autowired
    public ConfiguratieController (IConfiguratieRepo configuratieRepo) {
        this.configuratieRepo = configuratieRepo;
    }

    @GetMapping("/test")
    public String test(@RequestParam(value = "msg", defaultValue = "Hello") String msg) {
        return ("Configuratie API - test: " + msg).toUpperCase();
    }


    @GetMapping
    public Iterable<Configuratie> getAll() {
        return configuratieRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Configuratie> getById(@PathVariable int id) {
        Optional<Configuratie> result = configuratieRepo.findOne(id);
        return result.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<Configuratie> create(@RequestBody Configuratie newConfiguratie) {
        newConfiguratie.setId(null);
        Optional<Configuratie> saved = configuratieRepo.save(newConfiguratie);
        return saved.map(c -> ResponseEntity.status(HttpStatus.CREATED).body(c))
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }
}
