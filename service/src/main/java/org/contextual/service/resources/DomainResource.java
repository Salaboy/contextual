package org.contextual.service.resources;

import org.springframework.hateoas.ResourceSupport;

import java.util.List;

/**
 * Created by msalatino on 09/03/2017.
 */
public class DomainResource extends ResourceSupport {
    private String domainId;
    private String domainName;
    private List<String> supportedResourceTypes;
    private List<String> supportedServiceTypes;
    private List<ContextResource> contexts;

    public DomainResource() {
    }

    public DomainResource(String domainId, String domainName, List<String> supportedResourceTypes,
                          List<String> supportedServiceTypes, List<ContextResource> contexts) {
        this.domainId = domainId;
        this.domainName = domainName;
        this.supportedResourceTypes = supportedResourceTypes;
        this.supportedServiceTypes = supportedServiceTypes;
        this.contexts = contexts;
    }

    public String getDomainId() {
        return domainId;
    }

    public void setDomainId(String domainId) {
        this.domainId = domainId;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public List<String> getSupportedResourceTypes() {
        return supportedResourceTypes;
    }

    public void setSupportedResourceTypes(List<String> supportedResourceTypes) {
        this.supportedResourceTypes = supportedResourceTypes;
    }

    public List<String> getSupportedServiceTypes() {
        return supportedServiceTypes;
    }

    public void setSupportedServiceTypes(List<String> supportedServiceTypes) {
        this.supportedServiceTypes = supportedServiceTypes;
    }

    public List<ContextResource> getContexts() {
        return contexts;
    }

    public void setContexts(List<ContextResource> contexts) {
        this.contexts = contexts;
    }
}
