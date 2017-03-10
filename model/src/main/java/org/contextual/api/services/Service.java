package org.contextual.api.services;

/**
 * Created by msalatino on 08/03/2017.
 */

public interface Service {
    String getId();
    String getName();
    String getDescription();
    Endpoint getEndpoint();
}
