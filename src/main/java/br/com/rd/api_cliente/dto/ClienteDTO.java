package br.com.rd.api_cliente.dto;

import br.com.rd.api_cliente.entity.Cliente;

import java.io.Serializable;

public record ClienteDTO(
        String nome,
        String cpf,
        String email,
        String endereco,
        String telefone) implements Serializable {

    public Cliente toCliente() {
        Cliente cliente = new Cliente();

        cliente.setNome(this.nome);
        cliente.setCpf(this.cpf);
        cliente.setEmail(this.email);
        cliente.setEndereco(this.endereco);
        cliente.setTelefone(this.telefone);

        return cliente;
    }
}
