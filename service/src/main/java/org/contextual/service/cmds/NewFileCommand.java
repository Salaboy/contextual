package org.contextual.service.cmds;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.contextual.api.Command;
import org.contextual.api.ResourceType;
import org.contextual.service.resources.DocumentResource;
import org.contextual.service.resources.ProcessResource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by msalatino on 22/02/2017.
 */
public class NewFileCommand implements Command {
    private String fileName;
    private String fileExtension;
    private String fileVersion;

    @JsonCreator
    public NewFileCommand(@JsonProperty("fileName") String fileName, @JsonProperty("fileExtension") String fileExtension,
                          @JsonProperty("fileVersion") String fileVersion) {
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
    public Collection<ResourceType> applicableTo() {
        List<ResourceType> supportedTypes = new ArrayList<>();
        supportedTypes.add(DocumentResource.TYPE_INSTANCE);
        return supportedTypes;
    }

    @Override
    public String toString() {
        return "NewFileCommand{" +
                "fileName='" + fileName + '\'' +
                ", fileExtension='" + fileExtension + '\'' +
                ", fileVersion='" + fileVersion + '\'' +
                '}';
    }
}
