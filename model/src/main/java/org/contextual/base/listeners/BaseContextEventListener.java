package org.contextual.base.listeners;


import org.contextual.api.events.ResourceAddedEvent;
import org.contextual.api.events.ModelInstanceAddedEvent;
import org.contextual.api.events.ModelInstanceRemovedEvent;
import org.contextual.api.events.ResourceRemovedEvent;
import org.contextual.api.listeners.ContextEventListener;

/**
 * Created by msalatino on 30/01/2017.
 */
public abstract class BaseContextEventListener implements ContextEventListener {

    public abstract void onResourceAdded(ResourceAddedEvent rae);

    public abstract void onResourceRemoved(ResourceRemovedEvent rre);

    public abstract void onResourceInstanceAdded(ModelInstanceAddedEvent riae);

    public abstract void onResourceInstanceRemoved(ModelInstanceRemovedEvent rire);

}
