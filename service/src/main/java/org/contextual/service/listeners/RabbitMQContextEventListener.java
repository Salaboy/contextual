package org.contextual.service.listeners;

import org.contextual.api.Event;
import org.contextual.api.events.*;
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
        rabbitTemplate.convertAndSend(System.getProperty("QUEUE_NAME", "context-queue"), ">>> Context: Resource Added: " + rae.getResourceName());
    }

    @Override
    public void onResourceRemoved(ResourceRemovedEvent rre) {
        rabbitTemplate.convertAndSend(System.getProperty("QUEUE_NAME", "context-queue"), ">>> Context: Resource Removed: " + rre.getResourceName());
    }

    @Override
    public void onResourceInstanceAdded(ResourceInstanceAddedEvent riae) {
        rabbitTemplate.convertAndSend(System.getProperty("QUEUE_NAME", "context-queue"), ">>> Context: ResourceInstance Added: " + riae.getResourceInstanceId() + " - Resource: " + riae.getResource());
    }

    @Override
    public void onResourceInstanceRemoved(ResourceInstanceRemovedEvent rire) {
        rabbitTemplate.convertAndSend(System.getProperty("QUEUE_NAME", "context-queue"), ">>> Context: ResourceInstance Removed: " + rire.getResourceInstanceId() + " - Resource: " + rire.getResource());
    }
}
