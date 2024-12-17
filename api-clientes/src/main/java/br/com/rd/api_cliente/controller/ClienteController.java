package br.com.rd.api_cliente.controller;

import br.com.rd.api_cliente.dto.ClienteDTO;
import br.com.rd.api_cliente.entity.Cliente;
import br.com.rd.api_cliente.rabbitmq.RabbitmqConfig;
import br.com.rd.api_cliente.rabbitmq.RabbitmqConnection;
import br.com.rd.api_cliente.service.ClienteService;
import br.com.rd.api_cliente.service.Rabbitmqservice;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;
    private final Rabbitmqservice rabbitmqservice;

    @PostMapping
    public ResponseEntity<Cliente> salvar(@RequestBody ClienteDTO clienteDTO) {
        this.rabbitmqservice.enviarMensagem(RabbitmqConnection.QUEUE_CLIENTS, clienteDTO);

        Cliente clienteCriado = this.clienteService.salvar(clienteDTO.toCliente());

        return new ResponseEntity<>(clienteCriado, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Cliente> atualizar(@RequestBody ClienteDTO clienteDTO, @PathVariable Long id) {
        this.rabbitmqservice.enviarMensagem(RabbitmqConnection.QUEUE_CLIENTS, clienteDTO);

        Cliente clienteAtualizado = this.clienteService.atualizar(clienteDTO.toCliente(), id);

        return new ResponseEntity<>(clienteAtualizado, HttpStatus.OK);
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Cliente> buscarCpf(@PathVariable String cpf) {
        Cliente cliente = this.clienteService.buscarPorCpf(cpf);
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Cliente> buscar(@PathVariable Long id) {
        Cliente cliente = this.clienteService.buscarPorId(id);
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        this.clienteService.excluir(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> buscarTodos() {
        List<Cliente> clientes = this.clienteService.buscarTodos();
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

}
