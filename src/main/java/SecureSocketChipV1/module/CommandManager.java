package SecureSocketChipV1.module;

import SecureSocketChipV1.EventClasses.SSCCommandExecuteEvent;
import SecureSocketChipV1.SSCV1;
import SecureSocketChipV1.interfaces.SSCEvent;
import SecureSocketChipV1.interfaces.SSCVCommand;

import java.util.Arrays;
import java.util.UUID;

public class CommandManager {
    SSCV1 main;

    public CommandManager(SSCV1 main){
        this.main = main;
    }

    public void executeCommand(SSCV1 sscv1, String command, String[] args){
        if(main.getCommandHandler() == null) return;
        UUID uu = null;
        if(args.length >= 1){
            if(parseUUID(args[0]) != null){
                uu = parseUUID(args[0]);
                args = Arrays.copyOfRange(args, 1, args.length);
            }
        }
        main.getBaseCommandHandler().onCommand(sscv1, command, args, uu);
        for(SSCVCommand com : main.getCommandHandler()){
            com.onCommand(sscv1, command, args, uu);
        }
        for(SSCEvent event : main.getEventHandler()){
            event.onCommandExecute(new SSCCommandExecuteEvent(command, args, sscv1, uu));
        }
    }

    private UUID parseUUID(String uuid){
        try {
            return UUID.fromString(uuid);
        }catch (Exception e){
            return null;
        }
    }


}
