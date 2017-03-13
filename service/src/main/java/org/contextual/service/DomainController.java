package org.contextual.service;

/**
 * Created by msalatino on 05/02/2017.
 */

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.contextual.api.*;
import org.contextual.base.BaseContextImpl;
import org.contextual.base.BaseDomainImpl;
import org.contextual.base.BaseEndpointImpl;
import org.contextual.service.cmds.NewFileCommand;
import org.contextual.service.cmds.StartProcessCommand;
import org.contextual.service.impl.ExtendedCommandExecutorService;
import org.contextual.service.listeners.RabbitMQContextEventListener;
import org.contextual.service.listeners.RabbitMQDomainEventListener;
import org.contextual.service.listeners.RabbitMQExecutorEventListener;
import org.contextual.service.resources.internal.DocumentModel;
import org.contextual.service.resources.internal.ProcessModel;
import org.contextual.service.results.GetAvailableCommandsResult;
import org.contextual.service.services.BPMService;
import org.contextual.service.services.ContentService;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@ExposesResourceFor(Domain.class)
@RequestMapping(value = "/api/", produces = "application/hal+json")
public class DomainController {


    private RabbitMQHandler rabbitMQHandler;
    private RabbitMQDomainEventListener domainEventListener;
    private RabbitMQContextEventListener contextEventListener;
    private RabbitMQExecutorEventListener executorEventListener;
    private Collection<Domain> domains;

    public DomainController(RabbitMQDomainEventListener domainEventListener,
                            RabbitMQContextEventListener contextEventListener,
                            RabbitMQExecutorEventListener executorEventListener,
                            RabbitMQHandler rabbitMQHandler) {
        this.rabbitMQHandler = rabbitMQHandler;
        this.domainEventListener = domainEventListener;
        this.contextEventListener = contextEventListener;
        this.executorEventListener = executorEventListener;
        domains = new ArrayList<>();

        Domain domain = new BaseDomainImpl("Application Domain");
        domain.addSupportedResourceType(ProcessModel.TYPE_INSTANCE);
        domain.addSupportedResourceType(DocumentModel.TYPE_INSTANCE);
        domain.addSupportedServiceType(BPMService.TYPE_INSTANCE);
        domain.addSupportedServiceType(ContentService.TYPE_INSTANCE);
        domain.addDomainEventListener(domainEventListener);

        domains.add(domain);
    }

    private Domain getDomainById(String domainId) {
        for (Domain d : domains) {
            if (d.getId().equals(domainId)) {
                return d;
            }
        }
        return null;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/")
    public Resources<Resource<Domain>> getDomains() {
        Link selfLink = linkTo(methodOn(DomainController.class).getDomains()).withSelfRel();
        List<Resource<Domain>> domainResources = domains.stream().map(domain -> domainToResource(domain)).collect(Collectors.toList());
        return new Resources<>(domainResources, selfLink);
    }

    @RequestMapping(method = RequestMethod.GET, path = "{domainId}")
    public Resource<Domain> getDomain(@PathVariable("domainId") final String domainId) {
        return domainToResource(getDomainById(domainId));
    }

    private Resource<Domain> domainToResource(Domain domain){
        Link selfLink = linkTo(DomainController.class).slash(domain.getId()).withSelfRel();
        Link contextsLink = linkTo(methodOn(DomainController.class).getContexts(domain.getId())).withRel("contexts");
        return new Resource<>(domain, selfLink, contextsLink);
    }


    private Resource<Context> contextToResource(Domain domain, Context context){
        Link selfLink = linkTo(methodOn(DomainController.class).getContext(domain.getId(), context.getId())).withSelfRel();
        Link domainLink = linkTo(methodOn(DomainController.class).getDomain(domain.getId())).withRel("domain");
        Link modelsLink = linkTo(methodOn(DomainController.class).getModels(domain.getId(), context.getId())).withRel("models");
        Link commandsLink = linkTo(methodOn(DomainController.class).getCommands(domain.getId(), context.getId())).withRel("commands");
        return new Resource<>(context, selfLink, domainLink, modelsLink, commandsLink);
    }

    @RequestMapping(method = RequestMethod.GET, path = "{domainId}/contexts")
    public Resources<Resource<Context>> getContexts(@PathVariable("domainId") final String domainId) {
        Domain domain = getDomainById(domainId);
        Link selfLink = linkTo(methodOn(DomainController.class).getDomains()).withSelfRel();
        List<Context> contexts = domain.getContexts();
        List<Resource<Context>> contextResources = contexts.stream().map(context -> contextToResource(domain, context)).collect(Collectors.toList());
        return new Resources<>(contextResources, selfLink);
    }

    @RequestMapping(method = RequestMethod.GET, path = "{domainId}/{contextId}")
    public Resource<Context> getContext(@PathVariable("domainId") final String domainId,
                                                    @PathVariable("contextId") final String contextId) {
        Domain domain = getDomainById(domainId);
        Context context = domain.getContextById(contextId);
        return contextToResource(domain, context);
    }

    @RequestMapping(method = RequestMethod.POST, path = "{domainId}")
    public Resource<Context> createContext(
            @PathVariable("domainId") final String domainId,
            @RequestBody(required = true) String name) {

        Domain domainById = getDomainById(domainId);
        Context context = new BaseContextImpl(name, domainById);
        List<Class> cmds = new ArrayList<>();
        cmds.add(StartProcessCommand.class);
        cmds.add(NewFileCommand.class);
        context.setUpAvailableCommands(cmds);
        context.addContextEventListener(contextEventListener);
        ExtendedCommandExecutorService extendedCommandExecutorService = new ExtendedCommandExecutorService();
        extendedCommandExecutorService.addExecutorEventListener(executorEventListener);
        context.setExecutorService(extendedCommandExecutorService);
        domainById.registerContext(context);
        context.addService(BPMService.TYPE_INSTANCE, new BPMService("bpm", "my favourite BPM server",
                new BaseEndpointImpl("localhost", 8080, "bpm")));

       return contextToResource(domainById, context);
    }


    @RequestMapping(method = RequestMethod.POST, path = "{domainId}/{contextId}/models")
    public Resource<Model> newModel(
            @PathVariable("domainId") final String domainId,
            @PathVariable("contextId") final String contextId,
            @RequestBody(required = true) Model model) {

        getDomainById(domainId).getContextById(contextId).addModel(model);
        Link models = linkTo(methodOn(DomainController.class).getModels(domainId, contextId)).withRel("models");
        Link selfLink = linkTo(methodOn(DomainController.class).getModelById(domainId, contextId, model.getId())).withSelfRel();
        return new Resource<Model>(model, selfLink, models);
    }

    @RequestMapping(method = RequestMethod.GET, path = "{domainId}/{contextId}/models")
    public Resources<Resource<Model>> getModels(
            @PathVariable("domainId") final String domainId,
            @PathVariable("contextId") final String contextId) {
        Domain domain = getDomainById(domainId);
        Context context = domain.getContextById(contextId);
        Collection<Model> models = context.getModels();
        List<Resource<Model>> domainModels = models.stream().map(model -> modelToResource(domain, context, model)).collect(Collectors.toList());
        Link selfLink = linkTo(methodOn(DomainController.class).getModels(domainId, contextId)).withSelfRel();
        return new Resources<Resource<Model>>(domainModels,selfLink);
    }


    @RequestMapping(method = RequestMethod.GET, path = "{domainId}/{contextId}/models/{modelId}")
    public Resource<Model> getModelById(
            @PathVariable("domainId") final String domainId,
            @PathVariable("contextId") final String contextId, @PathVariable("modelId") final String modelId ) {
        Domain domain = getDomainById(domainId);
        Context context = domain.getContextById(contextId);
        Collection<Model> models = context.getModels();
        for(Model m : models){
            if(m.getId().equals(modelId)){
                return modelToResource(domain, context, m);
            }
        }
        return null;

    }



    private Resource<Model> modelToResource(Domain domain, Context context, Model model){
        Link selfLink = linkTo(methodOn(DomainController.class).getModelById(domain.getId(), context.getId(), model.getId())).withSelfRel();
        return new Resource<>(model, selfLink);
    }

//
//    @RequestMapping(method = RequestMethod.GET, path = "{domainId}/{contextId}/resources-instances")
//    public Resources<Resource<ModelInstance>> getResourcesInstances(
//            @PathVariable("domainId") final String domainId,
//            @PathVariable("contextId") final String contextId) {
//        Collection<ModelInstance> modelInstances = getDomainById(domainId).getContextById(contextId).getModelInstances();
//        return modelInstances;
//    }

    @RequestMapping(method = RequestMethod.GET, path = "{domainId}/{contextId}/cmds")
    public HttpEntity<GetAvailableCommandsResult> getCommands(
            @PathVariable("domainId") final String domainId,
            @PathVariable("contextId") final String contextId) {
        Collection<Class> cmds = getDomainById(domainId).getContextById(contextId).getAvailableCommands();
        GetAvailableCommandsResult result = new GetAvailableCommandsResult(new ArrayList<>(cmds));
        return new ResponseEntity<GetAvailableCommandsResult>(result, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, path = "{domainId}/{contextId}/execute")
    public HttpEntity<Void> executeCommand(
            @PathVariable("domainId") final String domainId,
            @PathVariable("contextId") final String contextId,
            @RequestBody(required = true) Command cmd) {

        Context contextById = getDomainById(domainId).getContextById(contextId);
        contextById.getExecutorService().execute(cmd, contextById);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }


}
