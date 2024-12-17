package br.com.rd.api_cliente.consumer;


import br.com.rd.api_cliente.dto.ClienteDTO;
import br.com.rd.api_cliente.rabbitmq.RabbitmqConnection;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = RabbitmqConnection.QUEUE_CLIENTS)
public class ClienteConsumer {


    @RabbitHandler
    public void receive(ClienteDTO dto) {
        System.out.println("Mensagem recebida " + dto);
    }
}
