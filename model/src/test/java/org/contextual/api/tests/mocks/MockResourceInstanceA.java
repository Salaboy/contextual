package org.contextual.api.tests.mocks;

import org.contextual.api.Resource;
import org.contextual.api.ResourceInstance;

import java.util.UUID;

/**
 * Created by msalatino on 27/02/2017.
 */
public class MockResourceInstanceA implements ResourceInstance {

    private String id;
    private MockResourceA resource;

    public MockResourceInstanceA(Resource resource) {
        this.id = UUID.randomUUID().toString();
        assert resource instanceof MockResourceA;
        this.resource = (MockResourceA)resource;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Resource getResource() {
        return resource;
    }
}
