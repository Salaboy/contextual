package org.contextual.base.listeners;


import org.contextual.api.EventListener;
import org.contextual.api.events.ContextDestroyedEvent;
import org.contextual.api.events.ContextRegisteredEvent;
import org.contextual.api.listeners.DomainEventListener;

/**
 * Created by msalatino on 30/01/2017.
 */
public abstract class BaseDomainEventListener implements DomainEventListener {

    public abstract void onContextRegistered(ContextRegisteredEvent cre);

    public abstract void onContextDestroyed(ContextDestroyedEvent cde);
}
