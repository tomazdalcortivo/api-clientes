package br.com.rd.api_cliente.connection;

import jakarta.annotation.PostConstruct;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Component;


@Component
public class RabbitMQConnection {

    private static final String NOME_EXCHANGE = "amq.direct";
    private static final String FILA_CLIENTES = "cliente";

    private final AmqpAdmin amqpAdmin;

    public RabbitMQConnection(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
    }

    private Queue criarFila(String nomeFila) {
        return new Queue(
                nomeFila,
                true,
                false,
                false
        );
    }

    private DirectExchange criarExchange() {
        return new DirectExchange(NOME_EXCHANGE);
    }

    private Binding criarLigacao(Queue fila, DirectExchange exchange) {
        return new Binding(
                fila.getName(),
                Binding.DestinationType.QUEUE,
                exchange.getName(),
                fila.getName(),
                null
        );
    }

    @PostConstruct
    private void configurarRabbitMQ() {
        Queue filaClientes = this.criarFila(FILA_CLIENTES);
        DirectExchange exchange = this.criarExchange();
        Binding ligacaoClientes = this.criarLigacao(filaClientes, exchange);

        // Declarando os componentes no RabbitMQ
        this.amqpAdmin.declareQueue(filaClientes);
        this.amqpAdmin.declareExchange(exchange);
        this.amqpAdmin.declareBinding(ligacaoClientes);
    }
}
