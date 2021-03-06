package org.contextual.api.services;

import org.contextual.api.Command;
import org.contextual.api.Context;
import org.contextual.api.listeners.ExecutorEventListener;

import java.util.concurrent.Future;

/**
 * Created by msalatino on 23/02/2017.
 */
public interface CommandExecutorService {

    Future execute(Command cmd, Context context);

    void addExecutorEventListener(ExecutorEventListener executorEventListener);

    void clearExecutorEventListeners();

}
