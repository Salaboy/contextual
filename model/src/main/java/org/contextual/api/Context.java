package org.contextual.api;

import com.fasterxml.jackson.annotation.*;
import org.contextual.api.listeners.ContextEventListener;
import org.contextual.api.services.CommandExecutorService;
import org.contextual.api.services.Service;
import org.contextual.api.services.ServiceType;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by msalatino on 21/02/2017.
 */

public interface Context {
    String getId();

    String getName();

    Domain getDomain();

    void setName(String name);

    Collection<Resource> getResources();

    Collection<Resource> getResourcesByType(ResourceType type);

    void addResource(Resource resource);

    Resource getResource(String resourceId);

    void removeResource(String resourceId);

    Collection<ResourceInstance> getResourceInstances();

    Collection<ResourceInstance> getResourceInstancesByType(ResourceType type);

    void addResourceInstance(ResourceInstance resourceInstance);

    void removeResourceInstance(String resourceInstanceId);

    void addContextEventListener(ContextEventListener contextEventListener);

    void clearContextEventListeners();

    void setUpAvailableCommands(List<Class> commands);

    Collection<Class> getAvailableCommands();

    void addService(ServiceType serviceType, Service service);

    Map<ServiceType, Collection<Service>> getServices();

    Collection<Service> getServicesByType(ServiceType serviceType);

    void removeService(String serviceId);

    @JsonIgnore
    CommandExecutorService getExecutorService();

    @JsonIgnore
    void setExecutorService(CommandExecutorService executorService);
}
