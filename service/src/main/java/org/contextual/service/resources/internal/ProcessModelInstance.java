package org.contextual.service.resources.internal;

import org.contextual.api.Model;
import org.contextual.api.ModelInstance;
import org.contextual.api.utils.IdGenerator;

import java.util.UUID;

/**
 * Created by msalatino on 07/03/2017.
 */
public class ProcessModelInstance implements ModelInstance {

    private String id;
    private ProcessModel model;

    public ProcessModelInstance(Model model) {
        this.id = IdGenerator.generateIdForEntity("ProcessModelInstance");
        assert model instanceof ProcessModel;
        this.model = (ProcessModel)model;

    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public Model getModel() {
        return this.model;
    }
}
