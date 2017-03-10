package org.contextual.base;

import org.contextual.api.services.Endpoint;

/**
 * Created by msalatino on 08/03/2017.
 */
public class BaseEndpointImpl implements Endpoint {
    private String hostname;
    private Integer port;
    private String context;

    public BaseEndpointImpl(String hostname, Integer port, String context) {
        this.hostname = hostname;
        this.port = port;
        this.context = context;
    }

    @Override
    public String getHostname() {
        return hostname;
    }

    @Override
    public Integer getPort() {
        return port;
    }

    @Override
    public String getContext() {
        return context;
    }
}
