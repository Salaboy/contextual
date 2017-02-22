package org.contextual.service.results;

/**
 * Created by msalatino on 05/02/2017.
 */

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.contextual.api.Context;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

public class GetContextsResult extends ResourceSupport {

    private List<Context> contexts;

    @JsonCreator
    public GetContextsResult(List<Context> contexts) {
        this.contexts = contexts;

    }

    public List<Context> getContexts() {
        return contexts;
    }
}
