package org.contextual.service.resources;

import org.springframework.hateoas.ResourceSupport;

/**
 * Created by msalatino on 09/03/2017.
 */
public class ContextResource extends ResourceSupport {
    private String contextId;
    private String contextName;

    public ContextResource() {
    }

    public ContextResource(String contextId, String contextName) {
        this.contextId = contextId;
        this.contextName = contextName;
    }

    public String getContextId() {
        return contextId;
    }

    public void setContextId(String contextId) {
        this.contextId = contextId;
    }

    public String getContextName() {
        return contextName;
    }

    public void setContextName(String contextName) {
        this.contextName = contextName;
    }

    // links to domain, services, resources, resources-instances, commands

}
