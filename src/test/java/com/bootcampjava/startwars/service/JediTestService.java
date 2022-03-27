package com.bootcampjava.startwars.service;
import com.bootcampjava.starwars.model.Jedi;
import com.bootcampjava.starwars.repository.JediRepositoryImpl;
import com.bootcampjava.starwars.service.JediService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
//todo arquivo de teste precisa ter o nome de test

@ExtendWith(SpringExtension.class)//essa anotação é para se referir a classe de teste( que é o JuUnit5)
@SpringBootTest//aqui eu anoto como uma classe de teste do próprio spring
public class JediTestService {

    @Autowired //para sobrescrever dentro do service
    private JediService jediService;//private para não ser acessado por outras classes
/**Usamos o mock quando queremos saber se uma função vai ser chamada corretamente, quantas vezes ela vai ser chamada,
 *  se os parâmetros esperados são os corretos, já o stub vai nos dizer se o resultado do código retorna de acordo
 *  com os parâmetros passado, se retorna sucesso, erro ou exceção por exemplo,
*/
    @MockBean//mock do objeto separadamente MockBean
    private JediRepositoryImpl jediRepository;

    @Test//para mostrar que é um teste
    @DisplayName("Should return Jedi with success") //essa anotaçõa mostra no terminal
    public void testFindBySuccess() {

        // cenario
        Jedi mockJedi = new Jedi(1, "Jedi Name", 10, 1);
        Mockito.doReturn(Optional.of(mockJedi)).when(jediRepository).findById(1);

        // execucao
        Optional<Jedi> returnedJedi  = jediService.findById(1);

        // assert
        Assertions.assertTrue(returnedJedi.isPresent(), "Jedi was not found");
        Assertions.assertSame(returnedJedi.get(), mockJedi, "Jedis must be the same");
    }

    // TODO: Criar teste de erro NOT FOUND
    @Test
    @DisplayName("Should note return Jedy")
    public void testFindByIdNotFound(){
        Mockito.doReturn(Optional.empty()).when(jediRepository).findById(18);
        Optional<Jedi> returnedJedi= jediService.findById(18);
        Assertions.assertTrue(returnedJedi.isEmpty(), "Jedy was not found");
    }


    // TODO: Criar um teste pro findAll();
}
