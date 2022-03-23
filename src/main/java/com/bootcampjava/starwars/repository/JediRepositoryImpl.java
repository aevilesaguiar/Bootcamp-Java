package com.bootcampjava.starwars.repository;

import com.bootcampjava.starwars.model.Jedi;
import com.bootcampjava.starwars.service.JediService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JediRepositoryImpl implements JediRepository {

    //classe de log
    private static  final Logger logger = LogManager.getLogger(JediService.class);

    /** JdbcTemplate: Esta é a classe central no pacote principal JDBC. Ele simplifica o uso do JDBC e ajuda a evitar erros comuns.
     *  Ele executa o fluxo de trabalho principal do JDBC, deixando o código do aplicativo para fornecer SQL e extrair
     *  resultados. Essa classe executa consultas ou atualizações SQL, iniciando a iteração sobre ResultSets e
     *  capturando exceções JDBC e traduzindo-as para a hierarquia de exceção genérica e mais informativa
     *  definida no org.springframework.daopacote..
     *  */
    private final JdbcTemplate jdbcTemplate;

    /**
     * Um SimpleJdbcInsert é um objeto reutilizável multithread que fornece recursos de inserção fáceis para uma tabela.
     * Ele fornece processamento de metadados para simplificar o código necessário para construir uma instrução de
     * inserção básica. Tudo o que você precisa fornecer é o nome da tabela e um Mapa contendo os nomes das colunas
     * e os valores das colunas.
     * O processamento de metadados é baseado no DatabaseMetaData fornecido pelo driver JDBC. Contanto que o driver JDBC
     *  possa fornecer os nomes das colunas para uma tabela especificada, podemos contar com esse recurso de detecção automática.
     * Se esse não for o caso, os nomes das colunas devem ser especificados explicitamente.
     *
     * A inserção real é tratada usando Spring's JdbcTemplate.
     *
     * Muitos dos métodos de configuração retornam a instância atual do SimpleJdbcInsert para fornecer a capacidade de
     * encadear vários em um estilo de interface "fluente".
     * */
    private final SimpleJdbcInsert simpleJdbcInsert;

    //incluir os construtores para que possa identificar o JdbcTemplate e validar o funcionamento dele

    //DataSource queries sql
    public JediRepositoryImpl(JdbcTemplate jdbcTemplate, DataSource dataSource){
    this.jdbcTemplate=jdbcTemplate;
    this.simpleJdbcInsert=new SimpleJdbcInsert(dataSource)//inserir os objetos dentro de um sql
            .withTableName("jedis")//criar uma tabela
            .usingGeneratedKeyColumns("id");
    }

    //metodos do jediRepository pois ele é uma interface. Retorna valores default
    @Override
    public Optional<Jedi> findById(int id) {
        try {
            Jedi jedi = jdbcTemplate.queryForObject("SELECT*FROM jedis WHERE id = ?",
                    new Object[]{id},
                    (rs, rowNum) -> {
                        Jedi p = new Jedi();
                        p.setId(rs.getInt("id"));
                        p.setName(rs.getString("name"));
                        p.setStrength(rs.getInt("version"));
                        return p;
                    });

            return Optional.of(jedi);

        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }
    @Override
    public List<Jedi> findAll() {
        return jdbcTemplate.query("SELECT * FROM jedis",
                (rs, rowNumber) -> {
                    Jedi jedi = new Jedi();
                    jedi.setId(rs.getInt("id"));
                    jedi.setName(rs.getString("name"));
                    jedi.setStrength(rs.getInt("strength"));
                    jedi.setVersion(rs.getInt("version"));
                    return jedi;
                });
    }

    @Override
    public Boolean update(Jedi jedi) {
        return jdbcTemplate.update("UPDATE jedis SET name = ?, strength = ?, version = ? WHERE id = ?",//busco e atualizo a tabela
                jedi.getName(),
                jedi.getStrength(),
                jedi.getVersion(),
                jedi.getId()) == 1;
    }

    @Override
    public Jedi save(Jedi jedi) {
        Map<String, Object> parameters = new HashMap<>(1);//toda vez que dermos um new dentro do map usamos hasmap
        parameters.put("name", jedi.getName());
        parameters.put("strength", jedi.getStrength());
        parameters.put("version", jedi.getVersion());
        //Number classe interna do java
        Number newId = simpleJdbcInsert.executeAndReturnKey(parameters);
        logger.info("Inserting Jedi intro database, generated id is: {}", newId);
        jedi.setId((Integer) newId);
        return jedi;
    }

    @Override
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM jedis WHERE id = ?", id) == 1;
    }
}
