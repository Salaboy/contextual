package org.contextual.service.results;

/**
 * Created by msalatino on 05/02/2017.
 */

import com.fasterxml.jackson.annotation.JsonCreator;
import org.contextual.api.Command;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

public class GetAvailableCommandsResult extends ResourceSupport {

    private List<Class> commands;

    @JsonCreator
    public GetAvailableCommandsResult(List<Class> commands) {
        this.commands = commands;

    }

    public List<Class> getCommands() {
        return commands;
    }
}
