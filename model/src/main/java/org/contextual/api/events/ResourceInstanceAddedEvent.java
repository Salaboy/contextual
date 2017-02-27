package org.contextual.api.events;

import org.contextual.api.Event;
import org.contextual.api.Resource;

/**
 * Created by msalatino on 21/02/2017.
 */
public class ResourceInstanceAddedEvent implements Event {
    private String resourceInstanceId;
    private Resource resource;

    public ResourceInstanceAddedEvent(String resourceInstanceId, Resource resource) {
        this.resourceInstanceId = resourceInstanceId;
        this.resource = resource;
    }

    public String getResourceInstanceId() {
        return resourceInstanceId;
    }

    public Resource getResource() {
        return resource;
    }
}