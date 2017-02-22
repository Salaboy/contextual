package org.contextual.service.results;

/**
 * Created by msalatino on 05/02/2017.
 */

import com.fasterxml.jackson.annotation.JsonCreator;
import org.contextual.api.Context;
import org.contextual.api.Resource;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

public class GetResourcesResult extends ResourceSupport {

    private List<Resource> resources;

    @JsonCreator
    public GetResourcesResult(List<Resource> resources) {
        this.resources = resources;

    }

    public List<Resource> getResources() {
        return resources;
    }
}
