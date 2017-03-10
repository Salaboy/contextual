package org.contextual.base;

import com.fasterxml.jackson.annotation.*;
import org.contextual.api.*;
import org.contextual.api.events.ResourceAddedEvent;
import org.contextual.api.events.ResourceInstanceAddedEvent;
import org.contextual.api.events.ResourceInstanceRemovedEvent;
import org.contextual.api.events.ResourceRemovedEvent;
import org.contextual.api.listeners.ContextEventListener;
import org.contextual.api.services.CommandExecutorService;
import org.contextual.api.services.Service;
import org.contextual.api.services.ServiceType;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by msalatino on 21/02/2017.
 */
public class BaseContextImpl implements Context {

    private String id;
    private String name;
    private Domain domain;
    private Collection<Resource> resources = new ArrayList<>();
    private Map<ServiceType, Collection<Service>> services = new ConcurrentHashMap<>();

    // Cache for search only
    private Map<ResourceType, Collection<Resource>> resourcesByType = new ConcurrentHashMap<>();
    private Map<ResourceType, Collection<ResourceInstance>> resourceInstancesByType = new ConcurrentHashMap<>();

    // Runtime
    private Collection<ContextEventListener> listeners = new ArrayList<>();
    private Collection<ResourceInstance> resourceInstances = new ArrayList<>();
    private Collection<Class> commands;


    // @TODO: the executor or a third component should be in charge of registering enviroments (process engine instance,
    //      content service instance and endpoints. And then know how to delegate based on labels (similar to kubernetes) to
    //      not pollute the commands with the environments information of where they need to be executed.


    private CommandExecutorService executorService;

    public BaseContextImpl(String name, Domain domain) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.domain = domain;
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
        Collection<Resource> resources = resourcesByType.get(type);
        if (resources == null) {
            return Collections.EMPTY_LIST;
        }
        return resources;
    }

    @Override
    public Collection<ResourceInstance> getResourceInstances() {
        return resourceInstances;
    }

    @Override
    public Collection<ResourceInstance> getResourceInstancesByType(ResourceType type) {
        Collection<ResourceInstance> resources = resourceInstancesByType.get(type);
        if (resources == null) {
            return Collections.EMPTY_LIST;
        }
        return resources;
    }

    @Override
    public void addResource(Resource resource) {
        if (domain.getSupportedResourceTypes().contains(resource.getResourceType())) {
            Collection<Resource> resourcesBySpecificType = resourcesByType.get(resource.getResourceType());
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
                    + "not supported!. Supported Resource Types: " + domain.getSupportedResourceTypes());
        }
    }

    @Override
    public void addResourceInstance(ResourceInstance resourceInstance) {
        if (domain.getSupportedResourceTypes().contains(resourceInstance.getResource().getResourceType())) {
            Collection<ResourceInstance> resourcesBySpecificType = resourceInstancesByType.get(resourceInstance.getResource().getResourceType());
            if (resourcesBySpecificType == null) {
                resourceInstancesByType.put(resourceInstance.getResource().getResourceType(), new ArrayList<>());
            }
            resourceInstancesByType.get(resourceInstance.getResource().getResourceType()).add(resourceInstance);
            this.resourceInstances.add(resourceInstance);
            for (ContextEventListener l : listeners) {
                l.onResourceInstanceAdded(new ResourceInstanceAddedEvent(resourceInstance.getId(), resourceInstance.getResource()));
            }
        } else {
            throw new IllegalStateException("ResourceInstance Type: " + resourceInstance.getResource().getResourceType()
                    + "not supported!. Supported ResourceInstance Types: " + domain.getSupportedResourceTypes());
        }
    }

    @Override
    public void removeResourceInstance(String resourceInstanceId) {
        ResourceInstance remove = null;
        for (ResourceInstance r : resourceInstances) {
            if (r.getId().equals(resourceInstanceId)) {
                remove = r;
            }
        }
        resourceInstances.remove(remove);
        for (ContextEventListener l : listeners) {
            l.onResourceInstanceRemoved(new ResourceInstanceRemovedEvent(remove.getId(), remove.getResource()));
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
    public Domain getDomain() {
        return domain;
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
    public Collection<Class> getAvailableCommands() {
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


    @Override
    public void addService(ServiceType serviceType, Service service) {
        if(domain.getSupportedServiceTypes().contains(serviceType)) {
            if (this.services.get(serviceType) == null) {
                this.services.put(serviceType, new ArrayList<>());

            }
            this.services.get(serviceType).add(service);
        }else{
            throw new IllegalStateException("Service Type: " + serviceType
                    + "not supported!. Supported Service Types: " + domain.getSupportedServiceTypes());
        }
    }

    @Override
    public Map<ServiceType, Collection<Service>> getServices() {
        return services;
    }

    public Collection<Service> getServicesByType(ServiceType serviceType) {
        return services.get(serviceType);
    }

    @Override
    public void removeService(String serviceId) {
        Service remove = null;
        for (ServiceType st : services.keySet()) {
            for (Service s : services.get(st)) {
                if (s.getId().equals(serviceId)) {
                    remove = s;
                }
            }
        }
        if(remove != null ) {
            services.remove(remove);
        }
    }
}
