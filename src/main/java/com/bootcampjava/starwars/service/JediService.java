package com.bootcampjava.starwars.service;
import com.bootcampjava.starwars.model.Jedi;
import com.bootcampjava.starwars.repository.JediRepositoryImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class JediService {

    private static  final Logger logger = LogManager.getLogger(JediService.class);

    // repository
    private final JediRepositoryImpl jediRepositoryImpl;

//construtor
    public JediService(JediRepositoryImpl jediRepositoryImpl) {
        this.jediRepositoryImpl = jediRepositoryImpl;
    }

//implementar o findById da querie que eu fiz
    public Optional<Jedi> findById(int id) {
        //log
        logger.info("Find Jedi with id: {}", id);

        return  jediRepositoryImpl.findById(id);
    }
//o que lista todos
    public List<Jedi> findAll() {

        logger.info("Bring all the Jedis from the Galaxy");

        return jediRepositoryImpl.findAll();
    }

    public Jedi save(Jedi jedi) {
        jedi.setVersion(1);

        logger.info("Update Jedi from system");

        return jediRepositoryImpl.save(jedi);
    }

    public boolean update(Jedi jedi) {
        boolean updated = false;

        Jedi savedJedi = this.save(jedi);

        if (savedJedi != null) updated = true;

        return updated;
    }

    public boolean delete(int id) {
        return true;
    }

}
