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
public class DocumentResource implements Resource {
    public static final DocumentResourceType TYPE_INSTANCE = new DocumentResourceType();
    private String id;
    private String name;
    private String documentPath;
    private String documentVersion;

    @JsonCreator
    public DocumentResource(@JsonProperty("name") String name,
                            @JsonProperty("documentPath") String documentPath,
                            @JsonProperty("documentVersion") String documentVersion) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.documentPath = documentPath;
        this.documentVersion = documentVersion;
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

    public String getDocumentPath() {
        return documentPath;
    }

    public String getDocumentVersion() {
        return documentVersion;
    }

    public void setDocumentVersion(String documentVersion) {
        this.documentVersion = documentVersion;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }

    @Override
    public ResourceType getResourceType() {
        return TYPE_INSTANCE;
    }

    @JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include= JsonTypeInfo.As.PROPERTY, property="@class")
    public static class DocumentResourceType implements ResourceType{

        @Override
        public String getName() {
            return "DocumentResourceType";
        }
    }
}
