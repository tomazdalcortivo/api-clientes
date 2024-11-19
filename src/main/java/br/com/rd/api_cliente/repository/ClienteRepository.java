package br.com.rd.api_cliente.repository;

import br.com.rd.api_cliente.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}