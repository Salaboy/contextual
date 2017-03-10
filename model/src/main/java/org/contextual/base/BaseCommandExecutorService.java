package org.contextual.base;

import org.contextual.api.Command;
import org.contextual.api.Context;
import org.contextual.api.events.AfterCommandExecutedEvent;
import org.contextual.api.events.BeforeCommandExecutedEvent;
import org.contextual.api.listeners.ExecutorEventListener;
import org.contextual.api.services.CommandExecutorService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by msalatino on 23/02/2017.
 */
public abstract class BaseCommandExecutorService implements CommandExecutorService {

    private List<ExecutorEventListener> listeners = new ArrayList<>();

    @Override
    public Future execute(final Command cmd, Context context) {

        for (ExecutorEventListener l : listeners) {
            l.beforeCommandExecuted(new BeforeCommandExecutedEvent(cmd));
        }

        Future future = executeTask(cmd, context);

        for (ExecutorEventListener l : listeners) {
            l.afterCommandExecuted(new AfterCommandExecutedEvent(cmd));
        }

        return future;
    }

    @Override
    public void addExecutorEventListener(ExecutorEventListener executorEventListener) {
        listeners.add(executorEventListener);
    }

    @Override
    public void clearExecutorEventListeners() {
        listeners.clear();
    }

    public abstract Future executeTask(final Command cmd, final Context context);
}
