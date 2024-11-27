package br.com.rd.api_cliente.service;

import br.com.rd.api_cliente.entity.Cliente;
import br.com.rd.api_cliente.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public Cliente salvar(Cliente cliente) {
        return this.clienteRepository.save(cliente);
    }

    public void excluir(Long id) {
        Cliente cliente = this.buscarPorId(id);
        this.clienteRepository.delete(cliente);
    }

    public List<Cliente> buscarTodos() {
        return this.clienteRepository.findAll();
    }

    public Cliente buscarPorId(Long id) {
        return this.clienteRepository.findById(id).orElseThrow();
    }

    public Cliente atualizar(Cliente cliente, Long id) {
        Cliente existente = clienteRepository.findById(id).orElseThrow();

        existente.setNome(cliente.getNome());
        existente.setCpf(cliente.getCpf());
        existente.setEmail(cliente.getEmail());
        existente.setEndereco(cliente.getEndereco());
        existente.setTelefone(cliente.getTelefone());

        this.clienteRepository.save(existente);
        return existente;
    }
}
