package br.com.rd.api_cliente.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Rabbitmqservice {

    private final RabbitTemplate template;

    public void enviarMensagem(String nomeFIla, Object mensagem) {
        this.template.setMessageConverter(new Jackson2JsonMessageConverter());
        this.template.convertAndSend(nomeFIla, mensagem);
    }

}
