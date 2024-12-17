package br.com.rd.api_cliente.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RabbitmqserviceTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private Rabbitmqservice rabbitmqservice;

    @Test
    void enviarMensagem() {
        String nomeFila = "testefila";
        Object mensagem = "teste mensagem";

        rabbitmqservice.enviarMensagem(nomeFila, mensagem);


        verify(rabbitTemplate).setMessageConverter(any(Jackson2JsonMessageConverter.class));
        verify(rabbitTemplate).convertAndSend(eq(nomeFila), eq(mensagem));
    }
}