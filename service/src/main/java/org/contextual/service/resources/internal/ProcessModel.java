package org.contextual.service.resources.internal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.contextual.api.Model;
import org.contextual.api.ModelType;
import org.contextual.api.utils.IdGenerator;

import java.util.UUID;

/**
 * Created by msalatino on 21/02/2017.
 */

public class ProcessModel implements Model {
    public static final ProcessModelType TYPE_INSTANCE = new ProcessModelType();
    private String id;
    private String name;
    private String processName;
    private String processVersion;

    @JsonCreator
    public ProcessModel(@JsonProperty("name") String name,
                        @JsonProperty("processName") String processName,
                        @JsonProperty("processVersion") String processVersion) {
        this.id = IdGenerator.generateIdForEntity("ProcessModel");
        this.name = name;
        this.processName = processName;
        this.processVersion = processVersion;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public ModelType getModelType() {
        return TYPE_INSTANCE;
    }

    public String getProcessName() {
        return processName;
    }

    public String getProcessVersion() {
        return processVersion;
    }

    @JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include= JsonTypeInfo.As.PROPERTY, property="@class")
    public static class ProcessModelType implements ModelType {
        @Override
        public String getName() {
            return "ProcessModelType";
        }
    }
}
