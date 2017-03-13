package org.contextual.service.cmds;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.contextual.api.Model;
import org.contextual.api.ModelType;
import org.contextual.base.BaseCommandImpl;
import org.contextual.service.resources.internal.DocumentModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by msalatino on 22/02/2017.
 */
public class NewFileCommand extends BaseCommandImpl {
    private String fileName;
    private String fileExtension;
    private String fileVersion;

    @JsonCreator
    public NewFileCommand(@JsonProperty("resources") Collection<Model> models,
                          @JsonProperty("fileName") String fileName,
                          @JsonProperty("fileExtension") String fileExtension,
                          @JsonProperty("fileVersion") String fileVersion) {
        this.models = models;
        this.fileName = fileName;
        this.fileExtension = fileExtension;
        this.fileVersion = fileVersion;
    }


    public String getFileName() {
        return fileName;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public String getFileVersion() {
        return fileVersion;
    }

    @Override
    public Collection<ModelType> applicableTo() {
        List<ModelType> supportedTypes = new ArrayList<>();
        supportedTypes.add(DocumentModel.TYPE_INSTANCE);
        return supportedTypes;
    }

    @Override
    public String toString() {
        return "NewFileCommand{" +
                "fileName='" + fileName + '\'' +
                ", fileExtension='" + fileExtension + '\'' +
                ", fileVersion='" + fileVersion + '\'' +
                ", models=" + models +
                '}';
    }
}
