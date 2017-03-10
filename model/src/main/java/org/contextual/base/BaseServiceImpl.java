package org.contextual.base;

import org.contextual.api.services.Endpoint;
import org.contextual.api.services.Service;

/**
 * Created by msalatino on 09/03/2017.
 */
public abstract class BaseServiceImpl implements Service {

    private String id;
    private String name;
    private String description;
    private Endpoint endpoint;

    public BaseServiceImpl(String name, String description, Endpoint endpoint) {
        this.name = name;
        this.description = description;
        this.endpoint = endpoint;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Endpoint getEndpoint() {
        return endpoint;
    }
}
