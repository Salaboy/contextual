package org.contextual.api;

import org.contextual.api.listeners.DomainEventListener;
import org.contextual.api.tests.mocks.MockCommandExecutorService;
import org.contextual.api.tests.mocks.MockResourceA;
import org.contextual.api.tests.mocks.MockResourceB;
import org.contextual.api.tests.mocks.cmds.MockExecuteSomethingACommand;
import org.contextual.api.tests.mocks.listeners.MockContextEventListener;
import org.contextual.api.tests.mocks.listeners.MockDomainEventListener;
import org.contextual.api.tests.mocks.listeners.MockExecutorEventListener;
import org.contextual.base.BaseEndpointImpl;
import org.contextual.api.tests.mocks.services.MockServiceA;
import org.contextual.base.BaseContextImpl;
import org.contextual.base.BaseDomainImpl;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Future;

import static org.junit.Assert.*;


/**
 * Created by msalatino on 30/01/2017.
 */
public class ContextualAPIModelTest {

    @Test
    public void testAPIModelFromConsumer() throws InterruptedException {


        Domain myDomain = new BaseDomainImpl("my domain");
        myDomain.addSupportedResourceType(MockResourceA.TYPE_INSTANCE);
        myDomain.addSupportedResourceType(MockResourceB.TYPE_INSTANCE);
        myDomain.addSupportedServiceType(MockServiceA.TYPE_INSTANCE);

        DomainEventListener mockDomainEventListener = new MockDomainEventListener();
        myDomain.addDomainEventListener(mockDomainEventListener);

        Context myContext = new BaseContextImpl("first context", myDomain);

        List<Class> cmds = new ArrayList<>();
        cmds.add( MockExecuteSomethingACommand.class);
        myContext.setUpAvailableCommands(cmds);

        MockContextEventListener mockContextEventListener = new MockContextEventListener();
        myContext.addContextEventListener(mockContextEventListener);

        myContext.addService(MockServiceA.TYPE_INSTANCE, new MockServiceA("mock service",
                "this is my mock service",
                new BaseEndpointImpl("localhost", 8080, "my-app")));

        MockCommandExecutorService mockCommandExecutorService = new MockCommandExecutorService();
        MockExecutorEventListener mockExecutorEventListener = new MockExecutorEventListener();
        mockCommandExecutorService.addExecutorEventListener(mockExecutorEventListener);
        myContext.setExecutorService(mockCommandExecutorService);
        myDomain.registerContext(myContext);

        Context mySecondContext = new BaseContextImpl("second context", myDomain);
        MockContextEventListener secondMockContextEventListener = new MockContextEventListener();
        mySecondContext.addContextEventListener(secondMockContextEventListener);
        myDomain.registerContext(mySecondContext);

        MockResourceA mockResourceA = new MockResourceA("myfile.a");
        myContext.addResource(mockResourceA);

        myContext.addResource(new MockResourceB("myfile2.b"));

        try {
            myContext.addResource(new DummyResource());
            fail();
        }catch(Exception e){
            assertTrue(e instanceof IllegalStateException);
        }

        mySecondContext.addResource(new MockResourceA("myfile.a"));

        assertEquals(1, mySecondContext.getResources().size());

        assertEquals(2, myContext.getResources().size());

        assertEquals(1, myContext.getResourcesByType(MockResourceA.TYPE_INSTANCE).size());
        assertEquals(1, myContext.getResourcesByType(MockResourceB.TYPE_INSTANCE).size());

        assertEquals(1, mySecondContext.getResourcesByType(MockResourceA.TYPE_INSTANCE).size());
        assertEquals(0, mySecondContext.getResourcesByType(MockResourceB.TYPE_INSTANCE).size());

        assertEquals(2, mockContextEventListener.getEvents().size());
        assertEquals(1, secondMockContextEventListener.getEvents().size());

        Collection<Class> commands = myContext.getAvailableCommands();
        assertEquals(1, commands.size());

        assertEquals(MockExecuteSomethingACommand.class, commands.iterator().next()  );

        Resource resource = myContext.getResource(mockResourceA.getId());

        MockExecuteSomethingACommand mockExecuteSomethingACommand = new MockExecuteSomethingACommand(resource, "prop1", "prop2");

        // @TODO: nasty API here.. we shouldn't send my context as parameter.. due the fact that myContext is getting the executor service
        //          it should be able to get the reference from itself. But now the Executor is decoupled from the Context.. which might be good
        Future execution = myContext.getExecutorService().execute(mockExecuteSomethingACommand, myContext);

        assertNotNull(execution);

        while(!execution.isDone() && !execution.isCancelled()){
            System.out.println("Sleeping until task gets executed ...");
            Thread.sleep(100);
        }

        assertTrue(execution.isDone());

        assertEquals(2, mockExecutorEventListener.getEvents().size());



    }


    @Test
    public void testAPIModelPlusRuntimeInstance() throws InterruptedException {

        // Creating a Domain
        Domain myDomain = new BaseDomainImpl("my domain");

        myDomain.addSupportedResourceType(MockResourceA.TYPE_INSTANCE);

        DomainEventListener mockDomainEventListener = new MockDomainEventListener();
        myDomain.addDomainEventListener(mockDomainEventListener);
        myDomain.addSupportedServiceType(MockServiceA.TYPE_INSTANCE);


        // Creating a Context
        Context myContext = new BaseContextImpl("first context", myDomain);

        MockContextEventListener mockContextEventListener = new MockContextEventListener();
        myContext.addContextEventListener(mockContextEventListener);
        MockCommandExecutorService mockCommandExecutorService = new MockCommandExecutorService();
        MockExecutorEventListener mockExecutorEventListener = new MockExecutorEventListener();
        mockCommandExecutorService.addExecutorEventListener(mockExecutorEventListener);
        myContext.setExecutorService(mockCommandExecutorService);
        myDomain.registerContext(myContext);

        MockResourceA mockResourceA = new MockResourceA("myfile.a");
        myContext.addResource(mockResourceA);

        myContext.addService(MockServiceA.TYPE_INSTANCE, new MockServiceA("mock service",
                "this is my mock service",
                new BaseEndpointImpl("localhost", 8080, "my-app")));

        assertEquals(1, myContext.getResources().size());

        List<Class> cmds = new ArrayList<>();
        cmds.add( MockExecuteSomethingACommand.class);
        myContext.setUpAvailableCommands(cmds);

        Collection<Class> commands = myContext.getAvailableCommands();
        assertEquals(1, commands.size());

        assertEquals(MockExecuteSomethingACommand.class, commands.iterator().next()  );

        // Creating instance of Supported Command
        MockExecuteSomethingACommand mockExecuteSomethingACommand = new MockExecuteSomethingACommand(mockResourceA, "prop1", "prop2");

        // Executing command
        // @TODO: nasty API here.. we shouldn't send my context as parameter.. due the fact that myContext is getting the executor service
        //          it should be able to get the reference from itself. But now the Executor is decoupled from the Context.. which might be good
        Future execution = myContext.getExecutorService().execute(mockExecuteSomethingACommand, myContext);

        assertNotNull(execution);

        while(!execution.isDone() && !execution.isCancelled()){
            System.out.println("Sleeping until task gets executed ...");
            Thread.sleep(100);
        }
        assertTrue(execution.isDone());

        Collection<ResourceInstance> instances = myContext.getResourceInstances();

        assertEquals(1, instances.size());

        assertEquals(2, mockExecutorEventListener.getEvents().size());


    }

    // @TODO: test for removals and removal events



    private class DummyResource implements Resource{
        @Override
        public String getId() {
            return UUID.randomUUID().toString();
        }

        @Override
        public String getName() {
            return "Dummy Resource";
        }

        @Override
        public void setName(String name) {

        }

        @Override
        public ResourceType getResourceType() {
            return new UnsupportedResourceType();
        }
    }
    private class UnsupportedResourceType implements ResourceType{

        @Override
        public String getName() {
            return "UnsupportedResourceType";
        }
    }
}