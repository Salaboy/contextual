package org.contextual.api;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Created by msalatino on 21/02/2017.
 */

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include= JsonTypeInfo.As.PROPERTY, property="@class")
public interface Model {

    String getId();

    String getName();

    void setName(String name);

    ModelType getModelType();

}
