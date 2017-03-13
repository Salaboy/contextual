package org.contextual.api.tests.mocks.cmds;

import org.contextual.api.Model;
import org.contextual.api.ModelType;
import org.contextual.api.tests.mocks.MockModelA;
import org.contextual.base.BaseCommandImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by msalatino on 22/02/2017.
 */
public class MockExecuteSomethingACommand extends BaseCommandImpl {
    private String property1;
    private String property2;

    public MockExecuteSomethingACommand(Collection<Model> models, String property1, String property2) {
        this.models = models;
        this.property1 = property1;
        this.property2 = property2;
    }

    public MockExecuteSomethingACommand(Model model, String property1, String property2) {
        this.models = new ArrayList<>();
        models.add(model);
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
    public Collection<ModelType> applicableTo() {
        List<ModelType> supportedTypes = new ArrayList<>();
        supportedTypes.add(MockModelA.TYPE_INSTANCE);
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
