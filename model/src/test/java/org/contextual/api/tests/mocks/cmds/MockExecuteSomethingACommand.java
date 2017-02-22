package org.contextual.api.tests.mocks.cmds;

import org.contextual.api.Command;
import org.contextual.api.ResourceType;
import org.contextual.api.tests.mocks.MockResourceA;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by msalatino on 22/02/2017.
 */
public class MockExecuteSomethingACommand implements Command {
    private String property1;
    private String property2;

    public MockExecuteSomethingACommand(String property1, String property2) {
        this.property1 = property1;
        this.property2 = property2;
    }

    public String getProperty1() {
        return property1;
    }

    public String getProperty2() {
        return property2;
    }

    @Override
    public Collection<ResourceType> applicableTo() {
        List<ResourceType> supportedTypes = new ArrayList<>();
        supportedTypes.add(MockResourceA.TYPE_INSTANCE);
        return supportedTypes;
    }

    @Override
    public String toString() {
        return "MockExecuteSomethingACommand{" +
                "property1='" + property1 + '\'' +
                ", property2='" + property2 + '\'' +
                '}';
    }
}
