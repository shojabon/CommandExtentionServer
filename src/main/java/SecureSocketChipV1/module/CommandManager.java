package SecureSocketChipV1.module;

import SecureSocketChipV1.EventClasses.SSCCommandExecuteEvent;
import SecureSocketChipV1.SSCV1;
import SecureSocketChipV1.interfaces.SSCEvent;
import SecureSocketChipV1.interfaces.SSCVCommand;

import java.util.Arrays;
import java.util.List;
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
                List<String> list = Arrays.asList(args);
                list.remove(0);
                String[] array = list.toArray(new String[0]);
                args = array;
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
            UUID reUUID = UUID.fromString(uuid);
        }catch (Exception e){
            return null;
        }
        return null;
    }


}
