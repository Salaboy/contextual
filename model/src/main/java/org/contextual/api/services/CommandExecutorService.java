package org.contextual.api.services;

import org.contextual.api.Command;

import java.util.concurrent.Future;

/**
 * Created by msalatino on 23/02/2017.
 */
public interface CommandExecutorService {

    Future execute(Command cmd);

}
