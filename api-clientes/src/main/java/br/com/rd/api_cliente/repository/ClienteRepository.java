package br.com.rd.api_cliente.repository;

import br.com.rd.api_cliente.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findClienteByCpf(String cpf);
}