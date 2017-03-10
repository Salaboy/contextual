package org.contextual.service;

/**
 * Created by msalatino on 05/02/2017.
 */

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import com.rabbitmq.client.impl.ChannelN;
import org.contextual.api.*;
import org.contextual.api.services.ServiceType;
import org.contextual.base.BaseContextImpl;
import org.contextual.base.BaseDomainImpl;
import org.contextual.base.BaseEndpointImpl;
import org.contextual.service.cmds.NewFileCommand;
import org.contextual.service.cmds.StartProcessCommand;
import org.contextual.service.impl.ExtendedCommandExecutorService;
import org.contextual.service.listeners.RabbitMQContextEventListener;
import org.contextual.service.listeners.RabbitMQDomainEventListener;
import org.contextual.service.listeners.RabbitMQExecutorEventListener;
import org.contextual.service.resources.ContextResource;
import org.contextual.service.resources.DocumentResource;
import org.contextual.service.resources.DomainResource;
import org.contextual.service.resources.ProcessResource;
import org.contextual.service.results.ContextCreationResult;
import org.contextual.service.results.GetAvailableCommandsResult;
import org.contextual.service.results.GetContextsResult;
import org.contextual.service.results.GetResourcesResult;
import org.contextual.service.services.BPMService;
import org.contextual.service.services.ContentService;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


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
        domain.addSupportedResourceType(ProcessResource.TYPE_INSTANCE);
        domain.addSupportedResourceType(DocumentResource.TYPE_INSTANCE);
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
    public HttpEntity<List<DomainResource>> getDomains() {
        List<DomainResource> result = new ArrayList<>();
        for (Domain d : domains) {
            List<ResourceType> supportedResourceTypes = d.getSupportedResourceTypes();
            List<String> resourceTypes = new ArrayList<>(d.getSupportedResourceTypes().size());
            for (ResourceType rt : supportedResourceTypes) {
                resourceTypes.add(rt.getName());
            }
            List<ServiceType> supportedServiceTypes = d.getSupportedServiceTypes();
            List<String> serviceTypes = new ArrayList<>(d.getSupportedServiceTypes().size());
            for (ServiceType st : supportedServiceTypes) {
                serviceTypes.add(st.getName());
            }
            List<ContextResource> contexts = new ArrayList<>(d.getContexts().size());
            for(Context c : d.getContexts()){
                ContextResource contextResource = new ContextResource(c.getId(), c.getName());
                contexts.add(contextResource);
            }
            DomainResource domainResource = new DomainResource(d.getId(), d.getName(), resourceTypes, serviceTypes,contexts);
            domainResource.add(linkTo(DomainController.class).slash(d.getId()).withSelfRel());
            result.add(domainResource);
        }

        return new ResponseEntity<List<DomainResource>>(result, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "{domainId}")
    public HttpEntity<DomainResource> getDomain(@PathVariable("domainId") final String domainId) {

        DomainResource domainResource = domainToResource(domainId);

        return new ResponseEntity<DomainResource>(domainResource, HttpStatus.OK);
    }


    private DomainResource domainToResource(String domainId){
        Domain d = getDomainById(domainId);
        List<ResourceType> supportedResourceTypes = d.getSupportedResourceTypes();
        List<String> resourceTypes = new ArrayList<>(d.getSupportedResourceTypes().size());
        for (ResourceType rt : supportedResourceTypes) {
            resourceTypes.add(rt.getName());
        }
        List<ServiceType> supportedServiceTypes = d.getSupportedServiceTypes();
        List<String> serviceTypes = new ArrayList<>(d.getSupportedServiceTypes().size());
        for (ServiceType st : supportedServiceTypes) {
            serviceTypes.add(st.getName());
        }
        List<ContextResource> contexts = new ArrayList<>(d.getContexts().size());
        for(Context c : d.getContexts()){
            contexts.add(contextToResource(d, c));
        }
        DomainResource domainResource = new DomainResource(d.getId(), d.getName(), resourceTypes, serviceTypes, contexts);
        domainResource.add(linkTo(DomainController.class).slash(d.getId()).withSelfRel());
        return domainResource;
    }

    private ContextResource contextToResource(Domain d, Context c){
        ContextResource contextResource = new ContextResource(c.getId(), c.getName());
        contextResource.add(linkTo(methodOn(DomainController.class).getContext(d.getId(), c.getId())).withSelfRel());
        contextResource.add(linkTo(methodOn(DomainController.class).getResources(d.getId(), c.getId())).withRel("resources"));
        contextResource.add(linkTo(methodOn(DomainController.class).getCommands(d.getId(), c.getId())).withRel("commands"));
        return contextResource;
    }

    @RequestMapping(method = RequestMethod.GET, path = "{domainId}/{contextId}")
    public HttpEntity<ContextResource> getContext(@PathVariable("domainId") final String domainId,
                                                    @PathVariable("contextId") final String contextId) {
        Domain d = getDomainById(domainId);
        ContextResource contextResource = contextToResource(d, d.getContextById(contextId));
        return new ResponseEntity<ContextResource>(contextResource, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, path = "{domainId}")
    public HttpEntity<ContextResource> createContext(
            @PathVariable("domainId") final String domainId,
            @RequestBody(required = true) String name) {


        Context context = new BaseContextImpl(name, getDomainById(domainId));
        List<Class> cmds = new ArrayList<>();
        cmds.add(StartProcessCommand.class);
        cmds.add(NewFileCommand.class);
        context.setUpAvailableCommands(cmds);
        context.addContextEventListener(contextEventListener);
        ExtendedCommandExecutorService extendedCommandExecutorService = new ExtendedCommandExecutorService();
        extendedCommandExecutorService.addExecutorEventListener(executorEventListener);
        context.setExecutorService(extendedCommandExecutorService);
        getDomainById(domainId).registerContext(context);
        context.addService(BPMService.TYPE_INSTANCE, new BPMService("bpm", "my favourite BPM server",
                new BaseEndpointImpl("localhost", 8080, "bpm")));


        ContextResource contextResource = new ContextResource(context.getId(), context.getName());

        contextResource.add(linkTo(DomainController.class).slash(domainId).slash(context.getId()).withSelfRel());
        contextResource.add(linkTo(DomainController.class).slash(domainId).withRel("domain"));


        return new ResponseEntity<ContextResource>(contextResource, HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.POST, path = "{domainId}/{contextId}/resources")
    public HttpEntity<Void> newResource(
            @PathVariable("domainId") final String domainId,
            @PathVariable("contextId") final String contextId,
            @RequestBody(required = true) Resource resource) {

        getDomainById(domainId).getContextById(contextId).addResource(resource);

        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, path = "{domainId}/{contextId}/resources")
    public HttpEntity<GetResourcesResult> getResources(
            @PathVariable("domainId") final String domainId,
            @PathVariable("contextId") final String contextId) {
        Collection<Resource> resources = getDomainById(domainId).getContextById(contextId).getResources();
        GetResourcesResult result = new GetResourcesResult(new ArrayList<>(resources));
        return new ResponseEntity<GetResourcesResult>(result, HttpStatus.OK);
    }

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
