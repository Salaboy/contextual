package org.contextual.api.events;

import org.contextual.api.Command;
import org.contextual.api.Event;

/**
 * Created by msalatino on 21/02/2017.
 */
public class BeforeCommandExecutedEvent implements Event {
    private Command command;

    public BeforeCommandExecutedEvent(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
