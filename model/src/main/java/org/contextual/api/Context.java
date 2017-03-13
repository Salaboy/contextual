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

    Collection<Model> getModels();

    Collection<Model> getModelsByType(ModelType type);

    void addModel(Model model);

    Model getModel(String modelId);

    void removeModel(String modelId);

    Collection<ModelInstance> getModelInstances();

    Collection<ModelInstance> getModelInstancesByType(ModelType type);

    void addModelInstance(ModelInstance modelInstance);

    void removeModelInstance(String modelInstanceId);

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
