package org.contextual.service.cmds;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.contextual.api.Model;
import org.contextual.api.ModelType;
import org.contextual.base.BaseCommandImpl;
import org.contextual.service.resources.internal.ProcessModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by msalatino on 22/02/2017.
 */
public class StartProcessCommand extends BaseCommandImpl {
    private String processName;
    private String processVersion;

    @JsonCreator
    public StartProcessCommand(@JsonProperty("resources") Collection<Model> models,
                               @JsonProperty("processName") String processName,
                               @JsonProperty("processVersion") String processVersion) {
        this.models = models;
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
    public Collection<ModelType> applicableTo() {
        List<ModelType> supportedTypes = new ArrayList<>();
        supportedTypes.add(ProcessModel.TYPE_INSTANCE);
        return supportedTypes;
    }

    @Override
    public String toString() {
        return "StartProcessCommand{" +
                "processName='" + processName + '\'' +
                ", processVersion='" + processVersion + '\'' +
                ", models=" + models +
                '}';
    }
}
