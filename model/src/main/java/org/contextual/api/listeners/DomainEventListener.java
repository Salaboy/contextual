package org.contextual.api.listeners;

import org.contextual.api.EventListener;
import org.contextual.api.events.ContextDestroyedEvent;
import org.contextual.api.events.ContextRegisteredEvent;

/**
 * Created by msalatino on 21/02/2017.
 */
public interface DomainEventListener extends EventListener {

    void onContextRegistered(ContextRegisteredEvent cre);

    void onContextDestroyed(ContextDestroyedEvent cde);
}
