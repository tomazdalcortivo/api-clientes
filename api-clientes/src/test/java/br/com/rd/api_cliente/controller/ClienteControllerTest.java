package br.com.rd.api_cliente.controller;

import br.com.rd.api_cliente.dto.ClienteDTO;
import br.com.rd.api_cliente.entity.Cliente;
import br.com.rd.api_cliente.service.ClienteService;
import br.com.rd.api_cliente.service.Rabbitmqservice;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ClienteControllerTest {

    @InjectMocks
    private ClienteController controller;

    @Mock
    private ClienteService clienteService;

    @Mock
    private Rabbitmqservice rabbitmqservice;

    private MockMvc mockMvc;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        cliente = new Cliente(
                1L,
                "Joao silva",
                "123.456.789.00",
                "joaoz@exemplo.com",
                "rua dos magos, 8000, curitiba pr",
                "(41) 99999-8888"
        );
    }

    @Test
    void cadastrarCliente_ComDadosValidos_RetornaStatusOk() throws Exception {
        given(clienteService.salvar(any(Cliente.class))).willReturn(cliente);

        mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "id": 1,
                                  "nome": "Joao silva",
                                  "cpf": "123.456.789.00",
                                  "email": "joaoz@exmplo.com",
                                  "endereco": "rua dos magos, 8000, curitiba pr",
                                  "telefone": "(41) 99999-8888"
                                }"""))
                .andExpect(status().isOk());

    }

    @Test
    void buscarPorId_ComIdValido_RetornaStatusOk() throws Exception {
        given(clienteService.buscarPorId(anyLong())).willReturn(cliente);

        mockMvc.perform(get("/api/clientes/{id}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    void buscarPorId_ComIdInexistente_RetornaStatusNotFound() throws Exception {
        given(clienteService.buscarPorId(anyLong())).willThrow(new EntityNotFoundException("Cliente não encontrado"));

        mockMvc.perform(get("/api/clientes/{id}", 2L))
                .andExpect(status().isNotFound());
    }

    @Test
    void excluir_ComIdValido_RetornaStatusNoContent() throws Exception {
        willDoNothing().given(clienteService).excluir(anyLong());

        mockMvc.perform(delete("/api/clientes/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}