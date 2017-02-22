package org.contextual.api.listeners;

import org.contextual.api.EventListener;
import org.contextual.api.events.ContextDestroyedEvent;
import org.contextual.api.events.ContextRegisteredEvent;
import org.contextual.api.events.ResourceAddedEvent;
import org.contextual.api.events.ResourceRemovedEvent;

/**
 * Created by msalatino on 21/02/2017.
 */
public interface ContextEventListener extends EventListener {
    void onResourceAdded(ResourceAddedEvent rae);

    void onResourceRemoved(ResourceRemovedEvent rre);
}
