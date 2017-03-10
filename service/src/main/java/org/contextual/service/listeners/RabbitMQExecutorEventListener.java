package org.contextual.service.listeners;

import org.contextual.api.Event;
import org.contextual.api.events.AfterCommandExecutedEvent;
import org.contextual.api.events.BeforeCommandExecutedEvent;
import org.contextual.api.events.ContextDestroyedEvent;
import org.contextual.api.events.ContextRegisteredEvent;
import org.contextual.api.listeners.DomainEventListener;
import org.contextual.api.listeners.ExecutorEventListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by msalatino on 06/02/2017.
 */
@Component
public class RabbitMQExecutorEventListener implements ExecutorEventListener {

    private RabbitTemplate rabbitTemplate;

    public RabbitMQExecutorEventListener(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void onEvent(Event event) {
        rabbitTemplate.convertAndSend(System.getProperty("QUEUE_NAME", "executor-queue"), "on Event: " + event.toString());
    }

    @Override
    public void beforeCommandExecuted(BeforeCommandExecutedEvent bcee) {
        rabbitTemplate.convertAndSend(System.getProperty("QUEUE_NAME", "executor-queue"), ">>> Executor: Before Command Executed: " + bcee.getCommand());
    }

    @Override
    public void afterCommandExecuted(AfterCommandExecutedEvent acee) {
        rabbitTemplate.convertAndSend(System.getProperty("QUEUE_NAME", "executor-queue"), ">>> Executor: After Command Executed: " + acee.getCommand());
    }


}
