package org.contextual.api.utils;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;

/**
 * Created by msalatino on 13/03/2017.
 */
public abstract class BaseObjectIdResolver<T> extends ObjectIdGenerator<T> {
    protected final Class<?> _scope;

    protected BaseObjectIdResolver(Class<?> scope) {
        this._scope = scope;
    }

    public final Class<?> getScope() {
        return this._scope;
    }

    public boolean canUseFor(ObjectIdGenerator<?> gen) {
        return gen.getClass() == this.getClass() && gen.getScope() == this._scope;
    }

    public abstract T generateId(Object var1);
}
