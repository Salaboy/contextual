package org.contextual.service.resources.internal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.contextual.api.Model;
import org.contextual.api.ModelType;
import org.contextual.api.utils.IdGenerator;


/**
 * Created by msalatino on 21/02/2017.
 */
public class DocumentModel implements Model {
    public static final DocumentModelType TYPE_INSTANCE = new DocumentModelType();
    private String id;
    private String name;
    private String documentPath;
    private String documentVersion;

    @JsonCreator
    public DocumentModel(@JsonProperty("name") String name,
                         @JsonProperty("documentPath") String documentPath,
                         @JsonProperty("documentVersion") String documentVersion) {
        this.id = IdGenerator.generateIdForEntity("DocumentModel");
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
    public ModelType getModelType() {
        return TYPE_INSTANCE;
    }

    @JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include= JsonTypeInfo.As.PROPERTY, property="@class")
    public static class DocumentModelType implements ModelType {

        @Override
        public String getName() {
            return "DocumentModelType";
        }
    }
}
