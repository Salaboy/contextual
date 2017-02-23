package org.contextual.api.listeners;

import org.contextual.api.EventListener;
import org.contextual.api.events.AfterCommandExecutedEvent;
import org.contextual.api.events.BeforeCommandExecutedEvent;
import org.contextual.api.events.ContextDestroyedEvent;
import org.contextual.api.events.ContextRegisteredEvent;

/**
 * Created by msalatino on 21/02/2017.
 */
public interface ExecutorEventListener extends EventListener {

    void beforeCommandExecuted(BeforeCommandExecutedEvent bcee);

    void afterCommandExecuted(AfterCommandExecutedEvent acee);
}
