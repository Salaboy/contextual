package org.contextual.service.results;

/**
 * Created by msalatino on 05/02/2017.
 */
import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ContextCreationResult extends ResourceSupport {

    private final String contextId;
    private final String name;
    private final String domainId;

    @JsonCreator
    public ContextCreationResult(@JsonProperty("contextId") String contextId,
                                 @JsonProperty("name") String name,
                                 @JsonProperty("domainId") String domainId) {
        this.contextId = contextId;
        this.name = name;
        this.domainId = domainId;
    }

    public String getName() {
        return name;
    }

    public String getContextId() {
        return contextId;
    }

    public String getDomainId() {
        return domainId;
    }
}
