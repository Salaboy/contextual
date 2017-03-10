package org.contextual.service.tasks;

import org.contextual.api.Context;
import org.contextual.service.cmds.NewFileCommand;
import org.contextual.service.cmds.StartProcessCommand;
import org.contextual.service.services.BPMService;
import org.contextual.service.services.ContentService;

import java.util.concurrent.Callable;

/**
 * Created by msalatino on 24/02/2017.
 */
public class NewFileTask implements Callable<Long> {

    private NewFileCommand cmd;
    private Context context;

    public NewFileTask(NewFileCommand cmd, Context context) {
        this.cmd = cmd;
        this.context = context;
    }



    @Override
    public Long call() throws Exception {
        // DO HTTP Call based on the cmd parameters to create a file...
        System.out.println( "Executing task for cmd: " + cmd);
        System.out.println( ">>> with this context: " + context);
        //@TODO: routing mechanism here, delegate to injection
        System.out.println("Calling Service: " + context.getServicesByType(ContentService.TYPE_INSTANCE)
                .iterator().next().getEndpoint());
        return null;
    }
}
