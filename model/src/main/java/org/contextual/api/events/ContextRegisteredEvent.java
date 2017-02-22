package org.contextual.api.events;

import org.contextual.api.Event;

/**
 * Created by msalatino on 21/02/2017.
 */
public class ContextRegisteredEvent implements Event{
    private String contextName;

    public ContextRegisteredEvent(String contextName) {
        this.contextName = contextName;
    }

    public String getContextName() {
        return contextName;
    }
}
