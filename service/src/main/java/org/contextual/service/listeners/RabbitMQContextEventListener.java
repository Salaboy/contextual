package org.contextual.service.listeners;

import org.contextual.api.Event;
import org.contextual.api.events.ContextDestroyedEvent;
import org.contextual.api.events.ContextRegisteredEvent;
import org.contextual.api.events.ResourceAddedEvent;
import org.contextual.api.events.ResourceRemovedEvent;
import org.contextual.api.listeners.ContextEventListener;
import org.contextual.api.listeners.DomainEventListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by msalatino on 06/02/2017.
 */
@Component
public class RabbitMQContextEventListener implements ContextEventListener {

    private RabbitTemplate rabbitTemplate;

    public RabbitMQContextEventListener(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void onEvent(Event event) {
        rabbitTemplate.convertAndSend(System.getProperty("QUEUE_NAME", "context-queue"), "on Event: " + event.toString());
    }


    @Override
    public void onResourceAdded(ResourceAddedEvent rae) {
        rabbitTemplate.convertAndSend(System.getProperty("QUEUE_NAME", "context-queue"), "Resource Added: " + rae.getResourceName());
    }

    @Override
    public void onResourceRemoved(ResourceRemovedEvent rre) {
        rabbitTemplate.convertAndSend(System.getProperty("QUEUE_NAME", "context-queue"), "Resource Removed: " + rre.getResourceName());
    }
}
