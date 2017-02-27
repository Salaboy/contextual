package org.contextual.api.tests.mocks.tasks;

import org.contextual.api.Context;
import org.contextual.api.Resource;
import org.contextual.api.tests.mocks.MockResourceA;
import org.contextual.api.tests.mocks.MockResourceInstanceA;
import org.contextual.api.tests.mocks.cmds.MockExecuteSomethingACommand;

import java.util.Iterator;
import java.util.concurrent.Callable;

/**
 * Created by msalatino on 24/02/2017.
 */
public class CreateResourceInstanceATask implements Callable<Long> {

    private MockExecuteSomethingACommand cmd;
    private Context context;

    public CreateResourceInstanceATask(MockExecuteSomethingACommand cmd, Context context) {
        this.cmd = cmd;
        this.context = context;
    }


    @Override
    public Long call() throws Exception {
        // DO HTTP Call based on the cmd parameters...
        System.out.println("Executing task for cmd: " + cmd);
        Iterator<Resource> iterator = cmd.getResources().iterator();
        while(iterator.hasNext()){
            Resource next = iterator.next();
            if(next instanceof MockResourceA) {

                MockResourceInstanceA mockResourceInstanceA = new MockResourceInstanceA(next);
                context.addResourceInstance(mockResourceInstanceA);
            }
        }

        return null;
    }
}
