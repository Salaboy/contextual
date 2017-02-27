package org.contextual.base;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.contextual.api.Command;
import org.contextual.api.Resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by msalatino on 27/02/2017.
 */
public abstract class BaseCommandImpl implements Command {

    protected Collection<Resource> resources;

    public Collection<Resource> getResources() {
        return resources;
    }

    @Override
    public String toString() {
        return "BaseCommandImpl{" +
                "resources=" + resources +
                '}';
    }
}
