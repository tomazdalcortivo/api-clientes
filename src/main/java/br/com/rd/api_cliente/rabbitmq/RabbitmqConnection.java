package br.com.rd.api_cliente.rabbitmq;

import br.com.rd.api_cliente.rabbitmq.constants.RabbitmqConstantes;
import jakarta.annotation.PostConstruct;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Component;

@Component
public class RabbitmqConnection {

    private final AmqpAdmin amqpAdmin;

    public RabbitmqConnection(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
    }

    @PostConstruct
    private void initializeRabbitMQ() {
        Queue clientQueue = createQueue(RabbitmqConstantes.QUEUE_CLIENTS);
        DirectExchange exchange = createExchange(RabbitmqConstantes.EXCHANGE_NAME);
        Binding clientBinding = createBinding(clientQueue, exchange);

        registerComponents(clientQueue, exchange, clientBinding);
    }

    private Queue createQueue(String name) {
        return new Queue(name, true, false, false);
    }

    private DirectExchange createExchange(String name) {
        return new DirectExchange(name);
    }

    private Binding createBinding(Queue queue, DirectExchange exchange) {
        return new Binding(
                queue.getName(),
                Binding.DestinationType.QUEUE,
                exchange.getName(),
                queue.getName(),
                null
        );
    }

    private void registerComponents(Queue queue, DirectExchange exchange, Binding binding) {
        amqpAdmin.declareQueue(queue);
        amqpAdmin.declareExchange(exchange);
        amqpAdmin.declareBinding(binding);
    }
}
