package org.contextual.api.tests.mocks.listeners;

import org.contextual.api.Event;
import org.contextual.api.events.*;
import org.contextual.base.listeners.BaseContextEventListener;
import org.contextual.base.listeners.BaseDomainEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by msalatino on 21/02/2017.
 */
public class MockContextEventListener extends BaseContextEventListener {

    private List<Event> events = new ArrayList<>();

    @Override
    public void onEvent(Event event) {
        events.add(event);
    }

    @Override
    public void onResourceAdded(ResourceAddedEvent rae) {
        events.add(rae);
    }

    @Override
    public void onResourceRemoved(ResourceRemovedEvent rre) {
        events.add(rre);
    }

    @Override
    public void onResourceInstanceAdded(ResourceInstanceAddedEvent riae) {
        events.add(riae);
    }

    @Override
    public void onResourceInstanceRemoved(ResourceInstanceRemovedEvent rire) {
        events.add(rire);
    }

    public List<Event> getEvents() {
        return events;
    }
}
