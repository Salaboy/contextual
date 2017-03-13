package org.contextual.api.listeners;

import org.contextual.api.EventListener;
import org.contextual.api.events.*;

/**
 * Created by msalatino on 21/02/2017.
 */
public interface ContextEventListener extends EventListener {
    void onResourceAdded(ResourceAddedEvent rae);

    void onResourceRemoved(ResourceRemovedEvent rre);

    void onResourceInstanceAdded(ModelInstanceAddedEvent rae);

    void onResourceInstanceRemoved(ModelInstanceRemovedEvent rre);
}
