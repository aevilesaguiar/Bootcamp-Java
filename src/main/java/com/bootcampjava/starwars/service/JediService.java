package com.bootcampjava.starwars.service;

import com.bootcampjava.starwars.model.Jedi;
import com.bootcampjava.starwars.repository.JediRepository;
import com.bootcampjava.starwars.repository.JediRepositoryImpl;
import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class JediService {

    private static  final Logger logger = LogManager.getLogger(JediService.class);

    //repository
    private final JediRepositoryImpl jediRepositoryImpl;

    public  JediService(JediRepositoryImpl jediRepositoryImpl){
        this.jediRepositoryImpl=jediRepositoryImpl
    }

    public Optional<Jedi> findById(int id){
        logger.info("Find Jedi with id: {}", id);
    return  jediRepositoryImpl.findById(id);
    }

    public List<Jedi> findAll(){

    }






}
