package org.contextual.api.tests.mocks;

import org.contextual.api.Model;
import org.contextual.api.ModelInstance;
import org.contextual.api.utils.IdGenerator;


/**
 * Created by msalatino on 27/02/2017.
 */
public class MockModelInstanceA implements ModelInstance {

    private String id;
    private MockModelA model;

    public MockModelInstanceA(Model model) {
        this.id = IdGenerator.generateIdForEntity("mock-model-instance-a");
        assert model instanceof MockModelA;
        this.model = (MockModelA)model;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Model getModel() {
        return model;
    }
}
