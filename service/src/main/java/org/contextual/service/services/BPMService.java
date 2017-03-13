package org.contextual.service.services;

import org.contextual.api.services.Endpoint;
import org.contextual.api.services.ServiceType;
import org.contextual.base.BaseServiceImpl;

/**
 * Created by msalatino on 08/03/2017.
 */
public class BPMService extends BaseServiceImpl {
    public static final ServiceType TYPE_INSTANCE = new BPMServiceType();

    public BPMService(String name, String description, Endpoint endpoint) {
        super(name, description, endpoint);
    }

    public static class BPMServiceType implements ServiceType {

        @Override
        public String getName() {
            return "BPMServiceType";
        }
    }
}
