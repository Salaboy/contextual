package org.contextual.service.impl;

import org.contextual.api.Command;
import org.contextual.api.Context;
import org.contextual.base.BaseCommandExecutorService;
import org.contextual.service.cmds.NewFileCommand;
import org.contextual.service.cmds.StartProcessCommand;
import org.contextual.service.tasks.NewFileTask;
import org.contextual.service.tasks.StartProcessTask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by msalatino on 24/02/2017.
 */
public class ExtendedCommandExecutorService extends BaseCommandExecutorService {

    @Override
    public Future executeTask(Command cmd, Context context) {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        // @TODO: implement a nice selection
        if(cmd instanceof StartProcessCommand) {
            return executor.submit(new StartProcessTask((StartProcessCommand)cmd, context));
        }else if(cmd instanceof NewFileCommand) {
            return executor.submit(new NewFileTask((NewFileCommand)cmd, context));
        }

        return null;
    }
}
