package org.contextual.base;

import org.contextual.api.Context;
import org.contextual.api.Resource;
import org.contextual.api.ResourceType;
import org.contextual.api.events.ResourceAddedEvent;
import org.contextual.api.events.ResourceRemovedEvent;
import org.contextual.api.listeners.ContextEventListener;
import org.contextual.api.services.CommandExecutorService;

import java.util.*;

/**
 * Created by msalatino on 21/02/2017.
 */
public class BaseContextImpl implements Context {

    private String id;
    private String name;
    private String domainId;
    private List<Resource> resources = new ArrayList<>();
    private Map<ResourceType, List<Resource>> resourcesByType = new HashMap<>();
    private List<ContextEventListener> listeners = new ArrayList<>();
    private List<ResourceType> supportedResourceTypes;
    private List<Class> commands;
    // @TODO: the executor or a third component should be in charge of registering enviroments (process engine instance,
    //      content service instance and endpoints. And then know how to delegate based on labels (similar to kubernetes) to
    //      not pollute the commands with the environments information of where they need to be executed.
    private CommandExecutorService executorService;

    public BaseContextImpl(String name, String domainId, List<ResourceType> supportedResourceTypes) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.domainId = domainId;
        this.supportedResourceTypes = supportedResourceTypes;

    }



    @Override
    public String getId() {
        return id;
    }

    @Override
    public Collection<Resource> getResources() {
        return resources;
    }

    @Override
    public Collection<Resource> getResourcesByType(ResourceType type) {
        List<Resource> resources = resourcesByType.get(type);
        if (resources == null) {
            return Collections.EMPTY_LIST;
        }
        return resources;
    }



    @Override
    public void addResource(Resource resource) {
        if (supportedResourceTypes.contains(resource.getResourceType())) {
            List<Resource> resourcesBySpecificType = resourcesByType.get(resource.getResourceType());
            if (resourcesBySpecificType == null) {
                resourcesByType.put(resource.getResourceType(), new ArrayList<>());
            }
            resourcesByType.get(resource.getResourceType()).add(resource);
            this.resources.add(resource);
            for (ContextEventListener l : listeners) {
                l.onResourceAdded(new ResourceAddedEvent(resource.getName()));
            }
        } else {
            throw new IllegalStateException("Resource Type: " + resource.getResourceType()
                    + "not supported!. Supported Resource Types: " + supportedResourceTypes);
        }
    }

    @Override
    public Resource getResource(String resourceId) {
        for (Resource r : resources) {
            if (r.getId().equals(resourceId)) {
                return r;
            }
        }
        return null;
    }

    @Override
    public void removeResource(String resourceId) {
        Resource remove = null;
        for (Resource r : resources) {
            if (r.getId().equals(resourceId)) {
                remove = r;
            }
        }
        resources.remove(remove);
        for (ContextEventListener l : listeners) {
            l.onResourceRemoved(new ResourceRemovedEvent(remove.getName()));
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDomainId() {
        return domainId;
    }

    @Override
    public void addContextEventListener(ContextEventListener contextEventListener) {
        listeners.add(contextEventListener);
    }

    @Override
    public void clearContextEventListeners() {
        listeners.clear();
    }

    @Override
    public List<ResourceType> getSupportedResourceTypes() {
        return supportedResourceTypes;
    }

    @Override
    public List<Class> getAvailableCommands() {
        return commands;
    }

    @Override
    public CommandExecutorService getExecutorService() {
        return executorService;
    }

    @Override
    public void setExecutorService(CommandExecutorService executorService) {
        this.executorService = executorService;
    }

    @Override
    public void setUpAvailableCommands(List<Class> commands) {
        this.commands = commands;
    }
}
