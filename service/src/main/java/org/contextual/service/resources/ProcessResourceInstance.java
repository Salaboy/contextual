package org.contextual.service.resources;

import org.contextual.api.Resource;
import org.contextual.api.ResourceInstance;

import java.util.UUID;

/**
 * Created by msalatino on 07/03/2017.
 */
public class ProcessResourceInstance implements ResourceInstance {

    private String id;
    private ProcessResource resource;

    public ProcessResourceInstance(Resource resource) {
        this.id = UUID.randomUUID().toString();
        assert resource instanceof ProcessResource;
        this.resource = (ProcessResource)resource;

    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public Resource getResource() {
        return this.resource;
    }
}
