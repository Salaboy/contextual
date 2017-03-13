package org.contextual.api.tests.mocks;

import org.contextual.api.Model;
import org.contextual.api.ModelType;
import org.contextual.api.utils.IdGenerator;

/**
 * Created by msalatino on 21/02/2017.
 */
public class MockModelA implements Model {
    public static final ModelTypeA TYPE_INSTANCE = new ModelTypeA();
    private String id;
    private String name;

    public MockModelA(String name) {
        this.id = IdGenerator.generateIdForEntity("mock-model-a");
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
    public ModelType getModelType() {
        return TYPE_INSTANCE;
    }

    public static class ModelTypeA implements ModelType {

        @Override
        public String getName() {
            return "ModelTypeA";
        }
    }
}
