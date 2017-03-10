package org.contextual.service.listeners;

import org.contextual.api.Event;
import org.contextual.api.EventListener;
import org.contextual.api.events.ContextDestroyedEvent;
import org.contextual.api.events.ContextRegisteredEvent;
import org.contextual.api.listeners.DomainEventListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by msalatino on 06/02/2017.
 */
@Component
public class RabbitMQDomainEventListener implements DomainEventListener {

    private RabbitTemplate rabbitTemplate;

    public RabbitMQDomainEventListener(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void onEvent(Event event) {
        rabbitTemplate.convertAndSend(System.getProperty("QUEUE_NAME", "domain-queue"), "on Event: " + event.toString());
    }

    @Override
    public void onContextRegistered(ContextRegisteredEvent cre) {
        rabbitTemplate.convertAndSend(System.getProperty("QUEUE_NAME", "domain-queue"), ">>> Domain: Context Registered: " + cre.getContextName());
    }

    @Override
    public void onContextDestroyed(ContextDestroyedEvent cde) {
        rabbitTemplate.convertAndSend(System.getProperty("QUEUE_NAME", "domain-queue"), ">>> Domain: Context Destroyed: " + cde.getContextName());
    }
}
