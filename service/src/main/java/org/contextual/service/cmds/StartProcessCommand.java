package org.contextual.service.cmds;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.contextual.api.Command;
import org.contextual.api.ResourceType;
import org.contextual.service.resources.ProcessResource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by msalatino on 22/02/2017.
 */
public class StartProcessCommand implements Command {
    private String processName;
    private String processVersion;

    @JsonCreator
    public StartProcessCommand(@JsonProperty("processName") String processName, @JsonProperty("processVersion") String processVersion) {
        this.processName = processName;
        this.processVersion = processVersion;
    }

    public String getProcessName() {
        return processName;
    }

    public String getProcessVersion() {
        return processVersion;
    }

    @Override
    public Collection<ResourceType> applicableTo() {
        List<ResourceType> supportedTypes = new ArrayList<>();
        supportedTypes.add(ProcessResource.TYPE_INSTANCE);
        return supportedTypes;
    }

    @Override
    public String toString() {
        return "StartProcessCommand{" +
                "processName='" + processName + '\'' +
                ", processVersion='" + processVersion + '\'' +
                '}';
    }
}
