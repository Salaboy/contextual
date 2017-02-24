package org.contextual.service;

/**
 * Created by msalatino on 05/02/2017.
 */

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.contextual.api.Command;
import org.contextual.api.Context;
import org.contextual.api.Domain;
import org.contextual.api.Resource;
import org.contextual.base.BaseContextImpl;
import org.contextual.base.BaseDomainImpl;
import org.contextual.service.cmds.NewFileCommand;
import org.contextual.service.cmds.StartProcessCommand;
import org.contextual.service.impl.ExtendedCommandExecutorService;
import org.contextual.service.listeners.RabbitMQContextEventListener;
import org.contextual.service.listeners.RabbitMQDomainEventListener;
import org.contextual.service.resources.DocumentResource;
import org.contextual.service.resources.ProcessResource;
import org.contextual.service.results.ContextCreationResult;
import org.contextual.service.results.GetAvailableCommandsResult;
import org.contextual.service.results.GetContextsResult;
import org.contextual.service.results.GetResourcesResult;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@RestController
public class DomainController {

    private RabbitMQHandler rabbitMQHandler;
    private RabbitMQDomainEventListener domainEventListener;
    private RabbitMQContextEventListener contextEventListener;
    private Domain domain;

    public DomainController(RabbitMQDomainEventListener domainEventListener,
                            RabbitMQContextEventListener contextEventListener,
                            RabbitMQHandler rabbitMQHandler) {
        this.rabbitMQHandler = rabbitMQHandler;
        this.domainEventListener = domainEventListener;
        this.contextEventListener = contextEventListener;
        domain = new BaseDomainImpl("Application Domain");
        domain.addSupportedResourceType(ProcessResource.TYPE_INSTANCE);
        domain.addSupportedResourceType(DocumentResource.TYPE_INSTANCE);
        domain.addDomainEventListener(domainEventListener);

    }


    @RequestMapping(method = RequestMethod.POST, path = "/contexts")
    public HttpEntity<ContextCreationResult> createContext(
            @RequestBody(required = true) String name) {

        Context context = new BaseContextImpl(name, domain.getId(), domain.getSupportedResourceTypes());
        List<Class> cmds = new ArrayList<>();
        cmds.add( StartProcessCommand.class);
        cmds.add( NewFileCommand.class);
        context.setUpAvailableCommands(cmds);
        context.addContextEventListener(contextEventListener);
        context.setExecutorService(new ExtendedCommandExecutorService());
        domain.registerContext(context);


        ContextCreationResult result = new ContextCreationResult(context.getId(), context.getName(), domain.getId());
        result.add(linkTo(methodOn(DomainController.class).createContext(name)).withSelfRel());


        return new ResponseEntity<ContextCreationResult>(result, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/contexts")
    public HttpEntity<GetContextsResult> getContexts() {

        List<Context> contexts = domain.getContexts();

        GetContextsResult result = new GetContextsResult(contexts);

        return new ResponseEntity<GetContextsResult>(result, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/contexts/{contextId}")
    public HttpEntity<Void> newResource(
            @PathVariable("contextId") final String contextId,
            @RequestBody(required = true) Resource resource ) {

        domain.getContextById(contextId).addResource(resource);

        return new ResponseEntity<Void>( HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/contexts/{contextId}")
    public HttpEntity<GetResourcesResult> getResources(
            @PathVariable("contextId") final String contextId) {
        Collection<Resource> resources = domain.getContextById(contextId).getResources();
        GetResourcesResult result = new GetResourcesResult(new ArrayList<>(resources));
        return new ResponseEntity<GetResourcesResult>(result, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/contexts/{contextId}/cmds")
    public HttpEntity<GetAvailableCommandsResult> getCommands(
            @PathVariable("contextId") final String contextId) {
        Collection<Class> cmds = domain.getContextById(contextId).getAvailableCommands();
        GetAvailableCommandsResult result = new GetAvailableCommandsResult(new ArrayList<>(cmds));
        return new ResponseEntity<GetAvailableCommandsResult>(result, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/contexts/{contextId}/cmds")
    public HttpEntity<Void> executeCommand(
            @PathVariable("contextId") final String contextId,
            @RequestBody(required = true) Command cmd ) {

        domain.getContextById(contextId).getExecutorService().execute(cmd);
        return new ResponseEntity<Void>( HttpStatus.OK);
    }



}
