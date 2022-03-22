package com.bootcampjava.starwars.repository;

import com.bootcampjava.starwars.model.Jedi;


import java.util.List;

public class JediRepository {

    Optionalonal<Jedi> findById(int id);

    List<Jedi> findAll();

    boolean update (Jedi jedi);


}
