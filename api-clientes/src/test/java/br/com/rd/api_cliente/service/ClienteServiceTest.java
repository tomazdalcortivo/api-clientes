package br.com.rd.api_cliente.service;

import br.com.rd.api_cliente.entity.Cliente;
import br.com.rd.api_cliente.repository.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static br.com.rd.api_cliente.constantes.ClienteConstantes.CLIENTE;
import static br.com.rd.api_cliente.constantes.ClienteConstantes.CLIENTE_DTO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {


    @Mock
    private ClienteRepository repository;

    @InjectMocks
    private ClienteService service;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente(
                1L,
                "joão silva",
                "123.456.789-00",
                "joaoz@example.com",
                "Rua das Flores, 100000, Curitiba, PR",
                "(41) 99999-8888"
        );
    }

    @Test
    void cadastrarCliente_ComDadosValidos_RetornaObjetoCliente() {
        given(repository.save(any(Cliente.class))).willReturn(CLIENTE);

        Cliente clienteSalvo = service.salvar(CLIENTE_DTO.toCliente());

        assertNotNull(clienteSalvo);

        assertEquals(CLIENTE.getId(), clienteSalvo.getId());
        assertEquals(CLIENTE.getNome(), clienteSalvo.getNome());
        assertEquals(CLIENTE.getCpf(), clienteSalvo.getCpf());
        assertEquals(CLIENTE.getEmail(), clienteSalvo.getEmail());
        assertEquals(CLIENTE.getEndereco(), clienteSalvo.getEndereco());
        assertEquals(CLIENTE.getTelefone(), clienteSalvo.getTelefone());

    }

    @Test
    void cadastrarCliente_ComDadosCpfOuEmailJaCadastrado_ThrowsDataIntegrityViolationException() {
        doThrow(DataIntegrityViolationException.class).when(repository).save(any(Cliente.class));

        assertThrows(DataIntegrityViolationException.class, () -> service.salvar(CLIENTE_DTO.toCliente()));
    }

    @Test
    void buscarPorId_ComIdValido_RetornaObjetoCliente() {
        given(repository.findById(anyLong())).willReturn(Optional.of(cliente));
        Cliente clienteSalvo = service.buscarPorId(cliente.getId());

        assertNotNull(clienteSalvo);
        assertEquals(cliente.getId(), clienteSalvo.getId());
        assertEquals(cliente.getNome(), clienteSalvo.getNome());
        assertEquals(cliente.getCpf(), clienteSalvo.getCpf());
        assertEquals(cliente.getEndereco(), clienteSalvo.getEndereco());
        assertEquals(cliente.getTelefone(), clienteSalvo.getTelefone());
        assertEquals(cliente.getEmail(), clienteSalvo.getEmail());
    }

    @Test
    void buscarPorId_ComIdInexistente_LancaExcecaoEntityNotFoundException() {
        given(repository.findById(anyLong())).willThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> service.buscarPorId(2L));
    }

    @Test
    void excluir_ComIdValido_Void() {
        given(repository.findById(anyLong())).willReturn(Optional.of(cliente));
        willDoNothing().given(repository).delete(cliente);
        service.excluir(cliente.getId());

        verify(repository, times(1)).delete(cliente);
    }


    @Test
    void excluir_comIdInexistente_Void() {
        given(repository.findById(anyLong())).willThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> service.excluir(cliente.getId()));
    }
}