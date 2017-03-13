package org.contextual.api.utils;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.contextual.api.Domain;

/**
 * Created by msalatino on 13/03/2017.
 */
public class MyCustomObjectIdResolver extends BaseObjectIdResolver<String> {

    public MyCustomObjectIdResolver() {
        this(Object.class);
    }

    private MyCustomObjectIdResolver(Class<?> scope) {
        super(Object.class);
    }

    public ObjectIdGenerator<java.lang.String> forScope(Class<?> scope) {
        return this;
    }

    public ObjectIdGenerator<java.lang.String> newForSerialization(Object context) {
        return this;
    }

    public java.lang.String generateId(Object forPojo) {
        return ((Domain)forPojo).getId();
    }

    public IdKey key(Object key) {
        return key == null?null:new IdKey(this.getClass(), (Class)null, key);
    }

    public boolean canUseFor(ObjectIdGenerator<?> gen) {
        return gen instanceof ObjectIdGenerators.StringIdGenerator;
    }


}
