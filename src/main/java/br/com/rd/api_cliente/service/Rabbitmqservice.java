 package br.com.rd.api_cliente.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Rabbitmqservice {


    private final RabbitTemplate template;

    public void enviarMensagem(String nomeFIla, Object mensagem) {
        this.template.convertAndSend(nomeFIla, mensagem);
    }
}
