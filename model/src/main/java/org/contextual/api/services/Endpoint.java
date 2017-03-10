package org.contextual.api.services;

/**
 * Created by msalatino on 08/03/2017.
 */
public interface Endpoint {
    String getHostname();
    Integer getPort();
    String getContext();
}
