package org.contextual.api.tests.mocks;

import org.contextual.api.Resource;
import org.contextual.api.ResourceType;

import java.util.UUID;

/**
 * Created by msalatino on 21/02/2017.
 */
public class MockResourceB implements Resource {
    public static final ResourceTypeB TYPE_INSTANCE = new ResourceTypeB();

    private String id;
    private String name;

    public MockResourceB(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
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
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public ResourceType getResourceType() {
        return TYPE_INSTANCE;
    }

    public static class ResourceTypeB implements ResourceType{

        @Override
        public String getName() {
            return "ResourceTypeB";
        }
    }
}
