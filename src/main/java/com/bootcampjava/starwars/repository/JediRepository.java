package com.bootcampjava.starwars.repository;

import com.bootcampjava.starwars.model.Jedi;


import java.util.List;
import java.util.Optional;

public interface JediRepository {

    Optional<Jedi> findById(int id );//valor de retorno opcional

    List<Jedi> findAll();

    Boolean update  (Jedi jedi);

   Jedi save(Jedi jedi);

   boolean delete (int id);

}
