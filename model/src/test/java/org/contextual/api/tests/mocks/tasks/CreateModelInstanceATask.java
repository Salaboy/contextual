package org.contextual.api.tests.mocks.tasks;

import org.contextual.api.Context;
import org.contextual.api.Model;
import org.contextual.api.tests.mocks.MockModelInstanceA;
import org.contextual.api.tests.mocks.MockModelA;
import org.contextual.api.tests.mocks.cmds.MockExecuteSomethingACommand;
import org.contextual.api.tests.mocks.services.MockServiceA;

import java.util.Iterator;
import java.util.concurrent.Callable;

/**
 * Created by msalatino on 24/02/2017.
 */
public class CreateModelInstanceATask implements Callable<Long> {

    private MockExecuteSomethingACommand cmd;
    private Context context;

    public CreateModelInstanceATask(MockExecuteSomethingACommand cmd, Context context) {
        this.cmd = cmd;
        this.context = context;
    }


    @Override
    public Long call() throws Exception {
        // DO HTTP Call based on the cmd parameters...
        System.out.println("Executing task for cmd: " + cmd);
        Iterator<Model> iterator = cmd.getModels().iterator();
        while(iterator.hasNext()){
            Model next = iterator.next();
            if(next instanceof MockModelA) {
                ((MockServiceA)context.getServicesByType(MockServiceA.TYPE_INSTANCE).iterator().next()).echo(">>> Sending data from the Task ... ");
                MockModelInstanceA mockResourceInstanceA = new MockModelInstanceA(next);
                context.addModelInstance(mockResourceInstanceA);
            }
        }

        return null;
    }
}
