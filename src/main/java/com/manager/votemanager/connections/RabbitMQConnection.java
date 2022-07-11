package com.manager.votemanager.connections;

import com.manager.votemanager.constants.RabbitMQConstants;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Component
public class RabbitMQConnection {
    private static final String NAME_EXCHANGE = "amq.direct";

    private AmqpAdmin amqpAdmin;

    public RabbitMQConnection(AmqpAdmin amqpAdmin){
        this.amqpAdmin = amqpAdmin;
    }

    private Queue queue(String queueName){

        return new Queue(queueName, true, false, false);
    }

    private DirectExchange directExchange(){

        return new DirectExchange(NAME_EXCHANGE);
    }

    private Binding relationship(Queue queue, DirectExchange exchange){

        return new Binding(queue.getName(), Binding.DestinationType.QUEUE, exchange.getName(), queue.getName(), null);
    }

    @PostConstruct
    private void add(){

        Queue queueResult = this.queue(RabbitMQConstants.QUEUE_RESULT);

        DirectExchange exchangeResult = this.directExchange();

        Binding connect = this.relationship(queueResult, exchangeResult);

        this.amqpAdmin.declareQueue(queueResult);

        this.amqpAdmin.declareExchange(exchangeResult);

        this.amqpAdmin.declareBinding(connect);
    }
}
