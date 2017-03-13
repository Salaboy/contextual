package org.contextual.api.events;

import org.contextual.api.Event;
import org.contextual.api.Model;

/**
 * Created by msalatino on 21/02/2017.
 */
public class ModelInstanceAddedEvent implements Event {
    private String modelInstanceId;
    private Model model;

    public ModelInstanceAddedEvent(String modelInstanceId, Model model) {
        this.modelInstanceId = modelInstanceId;
        this.model = model;
    }

    public String getModelInstanceId() {
        return modelInstanceId;
    }

    public Model getModel() {
        return model;
    }
}
