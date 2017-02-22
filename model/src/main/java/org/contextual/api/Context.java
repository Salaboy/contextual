package org.contextual.api;

import org.contextual.api.listeners.ContextEventListener;

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

    void addContextEventListener(ContextEventListener contextEventListener);

    void clearContextEventListeners();

    List<ResourceType> getSupportedResourceTypes();

    void setUpAvailableCommands(List<Class> commands);

    List<Class> getAvailableCommands();

    void executeCommand(Command cmd);
}
