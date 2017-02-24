package org.contextual.service.tasks;

import org.contextual.service.cmds.StartProcessCommand;

import java.util.concurrent.Callable;

/**
 * Created by msalatino on 24/02/2017.
 */
public class StartProcessTask implements Callable<Long> {

    private StartProcessCommand cmd;

    public StartProcessTask(StartProcessCommand cmd) {
        this.cmd = cmd;
    }



    @Override
    public Long call() throws Exception {
        // DO HTTP Call based on the cmd parameters to start correct process...
        System.out.println( "Executing task for cmd: " + cmd);
        return null;
    }
}
