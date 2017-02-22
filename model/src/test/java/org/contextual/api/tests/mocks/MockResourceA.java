package org.contextual.api.tests.mocks;

import org.contextual.api.Resource;
import org.contextual.api.ResourceType;

import java.util.UUID;

/**
 * Created by msalatino on 21/02/2017.
 */
public class MockResourceA implements Resource {
    public static final ResourceTypeA TYPE_INSTANCE = new ResourceTypeA();
    private String id;
    private String name;

    public MockResourceA(String name) {
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

    public static class ResourceTypeA implements ResourceType{

        @Override
        public String getName() {
            return "ResourceTypeA";
        }
    }
}
