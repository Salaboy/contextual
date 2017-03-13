package org.contextual.service.listeners;

import org.contextual.api.Event;
import org.contextual.api.events.*;
import org.contextual.api.listeners.ContextEventListener;
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
        rabbitTemplate.convertAndSend(System.getProperty("QUEUE_NAME", "context-queue"), ">>> Context: Model Added: " + rae.getResourceName());
    }

    @Override
    public void onResourceRemoved(ResourceRemovedEvent rre) {
        rabbitTemplate.convertAndSend(System.getProperty("QUEUE_NAME", "context-queue"), ">>> Context: Model Removed: " + rre.getResourceName());
    }

    @Override
    public void onResourceInstanceAdded(ModelInstanceAddedEvent riae) {
        rabbitTemplate.convertAndSend(System.getProperty("QUEUE_NAME", "context-queue"), ">>> Context: ModelInstance Added: " + riae.getModelInstanceId() + " - Model: " + riae.getModel());
    }

    @Override
    public void onResourceInstanceRemoved(ModelInstanceRemovedEvent rire) {
        rabbitTemplate.convertAndSend(System.getProperty("QUEUE_NAME", "context-queue"), ">>> Context: ModelInstance Removed: " + rire.getModelInstanceId() + " - Model: " + rire.getModel());
    }
}
