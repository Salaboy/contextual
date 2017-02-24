package org.contextual.api.tests.mocks;

import org.contextual.api.Command;
import org.contextual.api.services.CommandExecutorService;
import org.contextual.base.BaseCommandExecutorService;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by msalatino on 24/02/2017.
 */
public class MockCommandExecutorService extends BaseCommandExecutorService {
    @Override
    public Future executeTask(Command cmd) {
        System.out.println("Executing cmd: " + cmd);
        return new Future() {
            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                return false;
            }

            @Override
            public boolean isCancelled() {
                return false;
            }

            @Override
            public boolean isDone() {
                return true;
            }

            @Override
            public Object get() throws InterruptedException, ExecutionException {
                return null;
            }

            @Override
            public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
                return null;
            }
        };
    }
}
