package org.contextual.api.services;

import org.contextual.api.Command;

/**
 * Created by msalatino on 23/02/2017.
 */
public interface ExecutorService {

    void execute(Command cmd);

}
