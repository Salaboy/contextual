package org.contextual.base;

import org.contextual.api.Command;
import org.contextual.api.events.AfterCommandExecutedEvent;
import org.contextual.api.events.BeforeCommandExecutedEvent;
import org.contextual.api.listeners.ExecutorEventListener;
import org.contextual.api.services.ExecutorService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by msalatino on 23/02/2017.
 */
public class BaseExecutorService implements ExecutorService {

    private List<ExecutorEventListener> listeners = new ArrayList<>();
    @Override
    public void execute(Command cmd) {

        for(ExecutorEventListener l : listeners) {
            l.beforeCommandExecuted(new BeforeCommandExecutedEvent(cmd));
        }

        System.out.println("Executing CMD: " + cmd);

        for(ExecutorEventListener l : listeners) {
            l.afterCommandExecuted(new AfterCommandExecutedEvent(cmd));
        }

    }
}
