package joc.networking.controller;

import joc.model.Jucator;
import joc.persistance.IJucatorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/jucator")
public class JucatorController {

    private final IJucatorRepo jucatorRepo;

    @Autowired
    public JucatorController (IJucatorRepo jucatorRepo) {
        this.jucatorRepo = jucatorRepo;
    }

    @GetMapping("/test")
    public String test(@RequestParam(value = "msg", defaultValue = "Hello") String msg) {
        return ("Jucator API - test: " + msg).toUpperCase();
    }


    @GetMapping
    public Iterable<Jucator> getAll() {
        return jucatorRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Jucator> getById(@PathVariable int id) {
        Optional<Jucator> result = jucatorRepo.findOne(id);
        return result.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/nume/{nume}")
    public ResponseEntity<Jucator> getByNume(@PathVariable String nume) {
        Optional<Jucator> result = jucatorRepo.findByName(nume);
        return result.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

}

