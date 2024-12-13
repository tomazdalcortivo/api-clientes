package br.com.rd.api_cliente.constantes;

import br.com.rd.api_cliente.dto.ClienteDTO;
import br.com.rd.api_cliente.entity.Cliente;

import java.util.ArrayList;
import java.util.List;

public class ClienteConstantes {


    public static final List<Cliente> CLIENTE_LIST = new ArrayList<>();


    public static final Cliente CLIENTE = new Cliente(
            1L,
            "Cliente teste",
            "99999999999999",
            "Rua teste",
            "7777777777",
            "teste@email.com"
    );

    public static final ClienteDTO CLIENTE_DTO = new ClienteDTO(
            "Cliente teste",
            "99999999999999",
            "Rua teste",
            "7777777777",
            "teste@email.com"
    );

}
