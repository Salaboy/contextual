package org.contextual.api.events;

import org.contextual.api.Event;
import org.contextual.api.Resource;

/**
 * Created by msalatino on 21/02/2017.
 */
public class ResourceInstanceRemovedEvent implements Event {
    private String resourceInstanceId;
    private Resource resource;

    public ResourceInstanceRemovedEvent(String resourceInstanceId, Resource resource) {
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
