package org.contextual.base;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.contextual.api.*;
import org.contextual.api.events.ResourceAddedEvent;
import org.contextual.api.events.ModelInstanceAddedEvent;
import org.contextual.api.events.ModelInstanceRemovedEvent;
import org.contextual.api.events.ResourceRemovedEvent;
import org.contextual.api.listeners.ContextEventListener;
import org.contextual.api.services.CommandExecutorService;
import org.contextual.api.services.Service;
import org.contextual.api.services.ServiceType;
import org.contextual.api.utils.IdGenerator;
import org.contextual.api.utils.MyCustomObjectIdResolver;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by msalatino on 21/02/2017.
 */
public class BaseContextImpl implements Context {

    private String id;
    private String name;

//    @JsonIdentityInfo(generator = MyCustomObjectIdResolver.class)
//    @JsonIdentityReference(alwaysAsId = true)
    @JsonIgnore
    private Domain domain;
    private Collection<Model> models = new ArrayList<>();
    private Map<ServiceType, Collection<Service>> services = new ConcurrentHashMap<>();

    // Cache for search only
    private Map<ModelType, Collection<Model>> modelsByType = new ConcurrentHashMap<>();
    private Map<ModelType, Collection<ModelInstance>> modelInstancesByType = new ConcurrentHashMap<>();

    // Runtime
    private Collection<ContextEventListener> listeners = new ArrayList<>();
    private Collection<ModelInstance> modelInstances = new ArrayList<>();
    private Collection<Class> commands;


    // @TODO: the executor or a third component should be in charge of registering enviroments (process engine instance,
    //      content service instance and endpoints. And then know how to delegate based on labels (similar to kubernetes) to
    //      not pollute the commands with the environments information of where they need to be executed.


    private CommandExecutorService executorService;

    public BaseContextImpl(String name, Domain domain) {
        this.id = IdGenerator.generateIdForEntity("context");
        this.name = name;
        this.domain = domain;
    }

    @Override
    public String getId() {
        return id;
    }

    public Collection<Model> getModels() {
        return models;
    }

    @Override
    public Collection<Model> getModelsByType(ModelType type) {
        Collection<Model> models = modelsByType.get(type);
        if (models == null) {
            return Collections.EMPTY_LIST;
        }
        return models;
    }

    public Collection<ModelInstance> getModelInstances() {
        return modelInstances;
    }

    @Override
    public Collection<ModelInstance> getModelInstancesByType(ModelType type) {
        Collection<ModelInstance> modelInstances = modelInstancesByType.get(type);
        if (modelInstances == null) {
            return Collections.EMPTY_LIST;
        }
        return modelInstances;
    }

    @Override
    public void addModel(Model model) {
        if (domain.getSupportedModelTypes().contains(model.getModelType())) {
            Collection<Model> modelsBySpecificType = modelsByType.get(model.getModelType());
            if (modelsBySpecificType == null) {
                modelsByType.put(model.getModelType(), new ArrayList<>());
            }
            modelsByType.get(model.getModelType()).add(model);
            this.models.add(model);
            for (ContextEventListener l : listeners) {
                l.onResourceAdded(new ResourceAddedEvent(model.getName()));
            }
        } else {
            throw new IllegalStateException("Model Type: " + model.getModelType()
                    + "not supported!. Supported Model Types: " + domain.getSupportedModelTypes());
        }
    }

    @Override
    public void addModelInstance(ModelInstance modelInstance) {
        if (domain.getSupportedModelTypes().contains(modelInstance.getModel().getModelType())) {
            Collection<ModelInstance> modelsBySpecificType = modelInstancesByType.get(modelInstance.getModel().getModelType());
            if (modelsBySpecificType == null) {
                modelInstancesByType.put(modelInstance.getModel().getModelType(), new ArrayList<>());
            }
            modelInstancesByType.get(modelInstance.getModel().getModelType()).add(modelInstance);
            this.modelInstances.add(modelInstance);
            for (ContextEventListener l : listeners) {
                l.onResourceInstanceAdded(new ModelInstanceAddedEvent(modelInstance.getId(), modelInstance.getModel()));
            }
        } else {
            throw new IllegalStateException("ModelInstance Type: " + modelInstance.getModel().getModelType()
                    + "not supported!. Supported ModelInstance Types: " + domain.getSupportedModelTypes());
        }
    }

    @Override
    public void removeModelInstance(String modelInstanceId) {
        ModelInstance remove = null;
        for (ModelInstance r : modelInstances) {
            if (r.getId().equals(modelInstanceId)) {
                remove = r;
            }
        }
        modelInstances.remove(remove);
        for (ContextEventListener l : listeners) {
            l.onResourceInstanceRemoved(new ModelInstanceRemovedEvent(remove.getId(), remove.getModel()));
        }
    }

    @Override
    public Model getModel(String modelId) {
        for (Model m : models) {
            if (m.getId().equals(modelId)) {
                return m;
            }
        }
        return null;
    }

    @Override
    public void removeModel(String resourceId) {
        Model remove = null;
        for (Model m : models) {
            if (m.getId().equals(resourceId)) {
                remove = m;
            }
        }
        models.remove(remove);
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
