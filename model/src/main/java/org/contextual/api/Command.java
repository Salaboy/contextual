package org.contextual.api;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Collection;

/**
 * Created by msalatino on 21/02/2017.
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include= JsonTypeInfo.As.PROPERTY, property="@class")
public interface Command {

    Collection<ResourceType> applicableTo();

    Collection<Resource> getResources();
}
