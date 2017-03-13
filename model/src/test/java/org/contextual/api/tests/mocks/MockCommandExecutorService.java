package org.contextual.api.tests.mocks;

import org.contextual.api.Command;
import org.contextual.api.Context;
import org.contextual.api.tests.mocks.cmds.MockExecuteSomethingACommand;
import org.contextual.api.tests.mocks.tasks.CreateModelInstanceATask;
import org.contextual.base.BaseCommandExecutorService;

import java.util.concurrent.*;

/**
 * Created by msalatino on 24/02/2017.
 */
public class MockCommandExecutorService extends BaseCommandExecutorService {
    @Override
    public Future executeTask(Command cmd, Context context) {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        // @TODO: implement a nice selection
        if(cmd instanceof MockExecuteSomethingACommand) {
            return executor.submit(new CreateModelInstanceATask((MockExecuteSomethingACommand)cmd, context));
        }
        return null;
    }
}
