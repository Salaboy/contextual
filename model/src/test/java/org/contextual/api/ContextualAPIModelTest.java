package org.contextual.api;

import org.contextual.api.listeners.DomainEventListener;
import org.contextual.api.tests.mocks.MockCommandExecutorService;
import org.contextual.api.tests.mocks.MockModelA;
import org.contextual.api.tests.mocks.MockModelB;
import org.contextual.api.tests.mocks.cmds.MockExecuteSomethingACommand;
import org.contextual.api.tests.mocks.listeners.MockContextEventListener;
import org.contextual.api.tests.mocks.listeners.MockDomainEventListener;
import org.contextual.api.tests.mocks.listeners.MockExecutorEventListener;
import org.contextual.api.utils.IdGenerator;
import org.contextual.base.BaseEndpointImpl;
import org.contextual.api.tests.mocks.services.MockServiceA;
import org.contextual.base.BaseContextImpl;
import org.contextual.base.BaseDomainImpl;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Future;

import static org.junit.Assert.*;


/**
 * Created by msalatino on 30/01/2017.
 */
public class ContextualAPIModelTest {

    @Test
    public void testAPIModelFromConsumer() throws InterruptedException {


        Domain myDomain = new BaseDomainImpl("my domain");
        myDomain.addSupportedResourceType(MockModelA.TYPE_INSTANCE);
        myDomain.addSupportedResourceType(MockModelB.TYPE_INSTANCE);
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

        MockModelA mockModelA = new MockModelA("myfile.a");
        myContext.addModel(mockModelA);

        myContext.addModel(new MockModelB("myfile2.b"));

        try {
            myContext.addModel(new DummyModel());
            fail();
        }catch(Exception e){
            assertTrue(e instanceof IllegalStateException);
        }

        mySecondContext.addModel(new MockModelA("myfile.a"));

        assertEquals(1, mySecondContext.getModels().size());

        assertEquals(2, myContext.getModels().size());

        assertEquals(1, myContext.getModelsByType(MockModelA.TYPE_INSTANCE).size());
        assertEquals(1, myContext.getModelsByType(MockModelB.TYPE_INSTANCE).size());

        assertEquals(1, mySecondContext.getModelsByType(MockModelA.TYPE_INSTANCE).size());
        assertEquals(0, mySecondContext.getModelsByType(MockModelB.TYPE_INSTANCE).size());

        assertEquals(2, mockContextEventListener.getEvents().size());
        assertEquals(1, secondMockContextEventListener.getEvents().size());

        Collection<Class> commands = myContext.getAvailableCommands();
        assertEquals(1, commands.size());

        assertEquals(MockExecuteSomethingACommand.class, commands.iterator().next()  );

        Model model = myContext.getModel(mockModelA.getId());

        MockExecuteSomethingACommand mockExecuteSomethingACommand = new MockExecuteSomethingACommand(model, "prop1", "prop2");

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

        myDomain.addSupportedResourceType(MockModelA.TYPE_INSTANCE);

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

        MockModelA mockModelA = new MockModelA("myfile.a");
        myContext.addModel(mockModelA);

        myContext.addService(MockServiceA.TYPE_INSTANCE, new MockServiceA("mock service",
                "this is my mock service",
                new BaseEndpointImpl("localhost", 8080, "my-app")));

        assertEquals(1, myContext.getModels().size());

        List<Class> cmds = new ArrayList<>();
        cmds.add( MockExecuteSomethingACommand.class);
        myContext.setUpAvailableCommands(cmds);

        Collection<Class> commands = myContext.getAvailableCommands();
        assertEquals(1, commands.size());

        assertEquals(MockExecuteSomethingACommand.class, commands.iterator().next()  );

        // Creating instance of Supported Command
        MockExecuteSomethingACommand mockExecuteSomethingACommand = new MockExecuteSomethingACommand(mockModelA, "prop1", "prop2");

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

        Collection<ModelInstance> instances = myContext.getModelInstances();

        assertEquals(1, instances.size());

        assertEquals(2, mockExecutorEventListener.getEvents().size());


    }

    // @TODO: test for removals and removal events



    private class DummyModel implements Model{
        @Override
        public String getId() {
            return IdGenerator.generateIdForEntity("dummy-model");
        }

        @Override
        public String getName() {
            return "Dummy Model";
        }

        @Override
        public void setName(String name) {

        }

        @Override
        public ModelType getModelType() {
            return new UnsupportedModelType();
        }
    }
    private class UnsupportedModelType implements ModelType {

        @Override
        public String getName() {
            return "UnsupportedModelType";
        }
    }
}