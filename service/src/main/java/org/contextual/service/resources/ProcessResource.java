package org.contextual.service.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.contextual.api.Resource;
import org.contextual.api.ResourceType;

import java.util.UUID;

/**
 * Created by msalatino on 21/02/2017.
 */

public class ProcessResource implements Resource {
    public static final ProcessResourceType TYPE_INSTANCE = new ProcessResourceType();
    private String id;
    private String name;
    private String processName;
    private String processVersion;

    @JsonCreator
    public ProcessResource(@JsonProperty("name") String name,
                           @JsonProperty("processName") String processName,
                           @JsonProperty("processVersion") String processVersion) {
        this.id = UUID.randomUUID().toString();
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
    public ResourceType getResourceType() {
        return TYPE_INSTANCE;
    }

    public String getProcessName() {
        return processName;
    }

    public String getProcessVersion() {
        return processVersion;
    }

    @JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include= JsonTypeInfo.As.PROPERTY, property="@class")
    public static class ProcessResourceType implements ResourceType{
        @Override
        public String getName() {
            return "ProcessResourceType";
        }
    }
}
