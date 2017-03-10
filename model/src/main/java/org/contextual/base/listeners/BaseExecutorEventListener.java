package org.contextual.base.listeners;


import org.contextual.api.events.AfterCommandExecutedEvent;
import org.contextual.api.events.BeforeCommandExecutedEvent;
import org.contextual.api.listeners.ExecutorEventListener;

/**
 * Created by msalatino on 30/01/2017.
 */
public abstract class BaseExecutorEventListener implements ExecutorEventListener {

    public abstract void beforeCommandExecuted(BeforeCommandExecutedEvent bcee);

    public abstract void afterCommandExecuted(AfterCommandExecutedEvent acee);
}
