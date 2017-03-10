package org.contextual.api.tests.mocks.listeners;

import org.contextual.api.Event;
import org.contextual.api.events.AfterCommandExecutedEvent;
import org.contextual.api.events.BeforeCommandExecutedEvent;
import org.contextual.api.events.ContextDestroyedEvent;
import org.contextual.api.events.ContextRegisteredEvent;
import org.contextual.base.listeners.BaseDomainEventListener;
import org.contextual.base.listeners.BaseExecutorEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by msalatino on 21/02/2017.
 */
public class MockExecutorEventListener extends BaseExecutorEventListener {

    private List<Event> events = new ArrayList<>();

    @Override
    public void onEvent(Event event) {
        events.add(event);
    }

    @Override
    public void beforeCommandExecuted(BeforeCommandExecutedEvent bcee) {
        events.add(bcee);
    }

    @Override
    public void afterCommandExecuted(AfterCommandExecutedEvent acee) {
        events.add(acee);
    }

    public List<Event> getEvents() {
        return events;
    }
}
