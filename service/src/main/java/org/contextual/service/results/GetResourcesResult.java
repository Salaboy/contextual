package org.contextual.service.results;

/**
 * Created by msalatino on 05/02/2017.
 */

import com.fasterxml.jackson.annotation.JsonCreator;
import org.contextual.api.Context;
import org.contextual.api.Model;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

public class GetResourcesResult extends ResourceSupport {

    private List<Model> models;

    @JsonCreator
    public GetResourcesResult(List<Model> models) {
        this.models = models;

    }

    public List<Model> getModels() {
        return models;
    }
}
