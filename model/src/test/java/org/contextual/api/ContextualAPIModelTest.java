package org.contextual.api;

import org.contextual.api.listeners.ContextEventListener;
import org.contextual.api.listeners.DomainEventListener;
import org.contextual.api.tests.mocks.MockResourceA;
import org.contextual.api.tests.mocks.MockResourceB;
import org.contextual.api.tests.mocks.cmds.MockExecuteSomethingACommand;
import org.contextual.api.tests.mocks.listeners.MockContextEventListener;
import org.contextual.api.tests.mocks.listeners.MockDomainEventListener;
import org.contextual.base.BaseContextImpl;
import org.contextual.base.BaseDomainImpl;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


/**
 * Created by msalatino on 30/01/2017.
 */
public class ContextualAPIModelTest {

    @Test
    public void testAPIModelFromConsumer() {


        Domain myDomain = new BaseDomainImpl("my domain");
        myDomain.addSupportedResourceType(MockResourceA.TYPE_INSTANCE);
        myDomain.addSupportedResourceType(MockResourceB.TYPE_INSTANCE);

        DomainEventListener mockDomainEventListener = new MockDomainEventListener();
        myDomain.addDomainEventListener(mockDomainEventListener);

        Context myContext = new BaseContextImpl("first context", myDomain.getId(), myDomain.getSupportedResourceTypes());

        List<Class> cmds = new ArrayList<>();
        cmds.add( MockExecuteSomethingACommand.class);
        myContext.setUpAvailableCommands(cmds);

        MockContextEventListener mockContextEventListener = new MockContextEventListener();
        myContext.addContextEventListener(mockContextEventListener);
        myDomain.registerContext(myContext);

        Context mySecondContext = new BaseContextImpl("second context", myDomain.getId(), myDomain.getSupportedResourceTypes());
        MockContextEventListener secondMockContextEventListener = new MockContextEventListener();
        mySecondContext.addContextEventListener(secondMockContextEventListener);
        myDomain.registerContext(mySecondContext);

        myContext.addResource(new MockResourceA("myfile.a"));

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

        List<Class> commands = myContext.getAvailableCommands();
        assertEquals(1, commands.size());

        assertEquals(MockExecuteSomethingACommand.class, commands.get(0)  );

        MockExecuteSomethingACommand mockExecuteSomethingACommand = new MockExecuteSomethingACommand("prop1", "prop2");

        myContext.executeCommand(mockExecuteSomethingACommand);


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