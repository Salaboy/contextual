package org.contextual.api;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.contextual.api.listeners.DomainEventListener;
import org.contextual.api.services.ServiceType;

import java.util.List;
import java.util.Map;

/**
 * Created by msalatino on 21/02/2017.
 */
public interface Domain {

    String getId();

    String getName();

    void setName(String name);

    void registerContext(Context context);

    List<Context> getContexts();

    Context getContextById(String contextId);

    Context getContextByName(String name);

    void destroyContext(String name);

    void addDomainEventListener(DomainEventListener domainEventListener);

    void clearDomainEventListeners();

    List<ResourceType> getSupportedResourceTypes();

    void addSupportedResourceType(ResourceType type);

    List<ServiceType> getSupportedServiceTypes();

    void addSupportedServiceType(ServiceType type);

}
