package com.bootcampjava.starwars.repository;

import com.bootcampjava.starwars.service.JediService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Optional;

@Repository
public class JediRepositoryImpl {
    private static  final Logger logger = LogManageranager.getLogger(JediService.class);

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public  JediRepositoryImpl(JdbcTemplate jdbcTemplate, DataSource dataSource){
        this.jdbcTemplate = jdbcTemplate;

        this.simpleJdbcInsert=new SimpleJdbcInsert(dataSource)
                .withTableName("jedis")
                .usingGeneratedKeyColumns("id");
    }

    public Optional<Jedi> findById (int id){
        try {
            Jedi jedi = jdbcTemplate.queryForObject("SELECT * FROM jedis WHERE id= ?",
                    new )
        }
    }


}
