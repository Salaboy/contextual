package org.contextual.api.tests.mocks.listeners;

import org.contextual.api.Event;
import org.contextual.api.events.ContextDestroyedEvent;
import org.contextual.api.events.ContextRegisteredEvent;
import org.contextual.base.listeners.BaseDomainEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by msalatino on 21/02/2017.
 */
public class MockDomainEventListener extends BaseDomainEventListener {

    private List<Event> events = new ArrayList<>();

    @Override
    public void onEvent(Event event) {
        events.add(event);
    }

    @Override
    public void onContextRegistered(ContextRegisteredEvent cre) {
        events.add(cre);
    }

    @Override
    public void onContextDestroyed(ContextDestroyedEvent cde) {
        events.add(cde);
    }

    public List<Event> getEvents() {
        return events;
    }
}
