package br.com.rd.api_cliente.repository;

import br.com.rd.api_cliente.dto.ClienteDTO;
import br.com.rd.api_cliente.entity.Cliente;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class ClienteRepositoryTest {

    @Autowired
    ClienteRepository repository;

    @Autowired
    EntityManager entityManager;

    @DisplayName("Deve retornar o cliente com o cpf da busca")
    @Test
    void findClienteByCpfCase1() {
        String cpf = "122223334445";

        ClienteDTO dados = new ClienteDTO("roberto", cpf, "roberto@gmail.com", "rua das flores, 100", "23 4588876736");

        this.persistirCliente(dados);

        Optional<Cliente> resultado = this.repository.findClienteByCpf(cpf);

        assertThat(resultado.isPresent()).isTrue();
    }

    @DisplayName("Deve não retornar do banco um cliente que não foi criado")
    @Test
    void findClienteByCpfCase2() {
        String cpf = "122223334445";

        Optional<Cliente> resultado = this.repository.findClienteByCpf(cpf);

        assertThat(resultado.isEmpty()).isTrue();
    }

    private void persistirCliente(ClienteDTO dados){
        this.entityManager.persist(dados.toCliente());
    }
}