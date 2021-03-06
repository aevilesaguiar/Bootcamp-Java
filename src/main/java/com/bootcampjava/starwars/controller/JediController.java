package com.bootcampjava.starwars.controller;

import com.bootcampjava.starwars.model.Jedi;
import com.bootcampjava.starwars.service.JediService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController //padrão rest
public class JediController {
    private static final Logger logger = LogManager.getLogger(JediController.class);

    private final JediService jediService;

    public JediController(JediService jediService) {
        this.jediService = jediService;
    }

    @GetMapping("/jedi/{id}")
    public ResponseEntity<?> getJedi(@PathVariable Integer id) {

        return jediService.findById(id)
                .map(jedi -> {
                    try {
                        return  ResponseEntity
                                .ok()
                                .eTag(Integer.toString(jedi.getVersion()))
                                .location(new URI("/jedi/" + jedi.getId()))
                                .body(jedi);
                    } catch (URISyntaxException e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                    }
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/jedi")//mapear a rota
    public ResponseEntity<Jedi> saveJedi(@RequestBody Jedi jedi) {//meu objeto completo

        Jedi newJedi = jediService.save(jedi);

        try {
            return ResponseEntity
                    .created(new URI("/jedi/" + newJedi.getId()))//URI mãe que sempre vai iniciar após ele, localização da URI
                    .eTag(Integer.toString(newJedi.getVersion()))//eTag: metodo para colocar o tipo de versionamento
                    .body(newJedi);
        } catch (URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
