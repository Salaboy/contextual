package org.contextual.base;

import org.contextual.api.services.Endpoint;
import org.contextual.api.services.Service;
import org.contextual.api.utils.IdGenerator;

import java.util.UUID;

/**
 * Created by msalatino on 09/03/2017.
 */
public abstract class BaseServiceImpl implements Service {

    private String id;
    private String name;
    private String description;
    private Endpoint endpoint;

    public BaseServiceImpl(String name, String description, Endpoint endpoint) {
        this.id = IdGenerator.generateIdForEntity("service");
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
