package org.contextual.service.services;

import org.contextual.api.services.Endpoint;
import org.contextual.api.services.Service;
import org.contextual.api.services.ServiceType;
import org.contextual.base.BaseServiceImpl;

import java.util.UUID;

/**
 * Created by msalatino on 08/03/2017.
 */
public class ContentService extends BaseServiceImpl {

    public static final ServiceType TYPE_INSTANCE = new ContentServiceType();

    public ContentService(String name, String description, Endpoint endpoint) {
        super(name, description, endpoint);
    }

    public static class ContentServiceType implements ServiceType {

        @Override
        public String getName() {
            return "ContentServiceType";
        }
    }
}
