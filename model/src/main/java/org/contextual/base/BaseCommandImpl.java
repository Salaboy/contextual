package org.contextual.base;

import org.contextual.api.Command;
import org.contextual.api.Model;
import java.util.Collection;

/**
 * Created by msalatino on 27/02/2017.
 */
public abstract class BaseCommandImpl implements Command {

    protected Collection<Model> models;

    public Collection<Model> getModels() {
        return models;
    }

    @Override
    public String toString() {
        return "BaseCommandImpl{" +
                "models=" + models +
                '}';
    }
}
