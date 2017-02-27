package org.contextual.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.contextual.api.listeners.ContextEventListener;
import org.contextual.api.services.CommandExecutorService;

import java.util.Collection;
import java.util.List;

/**
 * Created by msalatino on 21/02/2017.
 */
public interface Context {

    String getId();

    String getName();

    String getDomainId();

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

    List<ResourceType> getSupportedResourceTypes();

    void setUpAvailableCommands(List<Class> commands);

    List<Class> getAvailableCommands();

    @JsonIgnore
    CommandExecutorService getExecutorService();

    @JsonIgnore
    void setExecutorService(CommandExecutorService executorService);
}
