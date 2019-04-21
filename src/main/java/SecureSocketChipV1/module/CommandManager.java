package SecureSocketChipV1.module;

import SecureSocketChipV1.EventClasses.SSCCommandExecuteEvent;
import SecureSocketChipV1.SSCV1;
import SecureSocketChipV1.interfaces.SSCEvent;
import SecureSocketChipV1.interfaces.SSCVCommand;

public class CommandManager {
    SSCV1 main;

    public CommandManager(SSCV1 main){
        this.main = main;
    }

    public void executeCommand(SSCV1 sscv1, String command, String[] args){
        if(main.getCommandHandler() == null) return;
        main.getBaseCommandHandler().onCommand(sscv1, command, args);
        for(SSCVCommand com : main.getCommandHandler()){
            com.onCommand(sscv1, command, args);
        }
        for(SSCEvent event : main.getEventHandler()){
            event.onCommandExecute(new SSCCommandExecuteEvent(command, args, sscv1));
        }
    }


}
