package org.contextual.api.tests.mocks.services;

import org.contextual.api.services.Endpoint;
import org.contextual.api.services.Service;
import org.contextual.api.services.ServiceType;
import org.contextual.api.utils.IdGenerator;

import java.util.UUID;

/**
 * Created by msalatino on 08/03/2017.
 */
public class MockServiceA implements Service {

    public static final ServiceType TYPE_INSTANCE = new MockServiceTypeA();


    private String id;
    private String name;
    private String description;
    private Endpoint endpoint;

    public MockServiceA(String name, String description, Endpoint endpoint) {
        this.id = IdGenerator.generateIdForEntity("mock-service-a");
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

    public void echo(String value){
        System.out.println("Echoing: " + value + "from Endpoint: " + this);
    }

    @Override
    public String toString() {
        return "MockServiceA{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", endpoint=" + endpoint +
                '}';
    }

    public static class MockServiceTypeA implements ServiceType {

        @Override
        public String getName() {
            return "ServiceTypeA";
        }
    }


}
