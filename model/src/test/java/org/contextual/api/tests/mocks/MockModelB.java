package org.contextual.api.tests.mocks;

import org.contextual.api.Model;
import org.contextual.api.ModelType;
import org.contextual.api.utils.IdGenerator;

import java.util.UUID;

/**
 * Created by msalatino on 21/02/2017.
 */
public class MockModelB implements Model {
    public static final ModelTypeB TYPE_INSTANCE = new ModelTypeB();

    private String id;
    private String name;

    public MockModelB(String name) {
        this.id = IdGenerator.generateIdForEntity("mock-model-b");
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

    public static class ModelTypeB implements ModelType {

        @Override
        public String getName() {
            return "ModelTypeB";
        }
    }
}
