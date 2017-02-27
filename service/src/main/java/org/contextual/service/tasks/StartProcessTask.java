package org.contextual.service.tasks;

import org.contextual.api.Context;
import org.contextual.service.cmds.StartProcessCommand;

import java.util.concurrent.Callable;

/**
 * Created by msalatino on 24/02/2017.
 */
public class StartProcessTask implements Callable<Long> {

    private StartProcessCommand cmd;
    private Context context;

    public StartProcessTask(StartProcessCommand cmd, Context context) {
        this.cmd = cmd;
        this.context = context;
    }



    @Override
    public Long call() throws Exception {
        // DO HTTP Call based on the cmd parameters to start correct process...
        System.out.println( "Executing task for cmd: " + cmd);
        System.out.println( ">>> with this Context" + context);
        return null;
    }
}
