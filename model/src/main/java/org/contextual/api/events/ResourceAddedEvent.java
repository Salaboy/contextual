package org.contextual.api.events;

import org.contextual.api.Event;

/**
 * Created by msalatino on 21/02/2017.
 */
public class ResourceAddedEvent implements Event {
    private String resourceName;

    public ResourceAddedEvent(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourceName() {
        return resourceName;
    }
}
