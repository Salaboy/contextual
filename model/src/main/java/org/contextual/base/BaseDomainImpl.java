package org.contextual.base;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.contextual.api.Context;
import org.contextual.api.Domain;
import org.contextual.api.ResourceType;
import org.contextual.api.events.ContextDestroyedEvent;
import org.contextual.api.events.ContextRegisteredEvent;
import org.contextual.api.listeners.DomainEventListener;
import org.contextual.api.services.ServiceType;

import java.util.*;

/**
 * Created by msalatino on 21/02/2017.
 */

public class BaseDomainImpl implements Domain {

    private String id;
    private String name;
    private List<ResourceType> supportedResourceTypes = new ArrayList<>();
    private List<ServiceType> supportedServiceTypes = new ArrayList<>();

    // @TODO: evaluate hazelcast for eviction maps and cluster/distributed sync
    private Map<String, Context> contexts = new HashMap<>();
    private List<DomainEventListener> listeners = new ArrayList<>();


    public BaseDomainImpl(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }


    @Override
    public String getId() {
        return id;
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
    public void registerContext(Context context) {
        contexts.put(context.getName(), context);
        for(DomainEventListener l : listeners){
            l.onContextRegistered(new ContextRegisteredEvent(context.getName()));
        }
    }

    @Override
    public List<Context> getContexts() {
        return new ArrayList<>(contexts.values());
    }

    @Override
    public Context getContextById(String contextId) {
        for(Context c : contexts.values()){
            if(c.getId().equals(contextId)){
                return c;
            }
        }
        return null;
    }

    @Override
    public Context getContextByName(String name) {
        return contexts.get(name);
    }

    @Override
    public void destroyContext(String name) {
        contexts.remove(name);
        for(DomainEventListener l : listeners){
            l.onContextDestroyed(new ContextDestroyedEvent(name));
        }
    }

    @Override
    public void addDomainEventListener(DomainEventListener domainEventListener) {
        listeners.add(domainEventListener);
    }

    @Override
    public void clearDomainEventListeners() {
        listeners.clear();
    }

    @Override
    public List<ResourceType> getSupportedResourceTypes() {
        return supportedResourceTypes;
    }

    @Override
    public void addSupportedResourceType(ResourceType type){
        supportedResourceTypes.add(type);
    }

    @Override
    public List<ServiceType> getSupportedServiceTypes() {
        return supportedServiceTypes;
    }

    @Override
    public void addSupportedServiceType(ServiceType type) {
        supportedServiceTypes.add(type);
    }
}
