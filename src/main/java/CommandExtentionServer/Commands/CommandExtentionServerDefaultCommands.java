package CommandExtentionServer.Commands;

import CommandExtentionServer.CommandExtentionServer;
import SecureSocketChipV1.SSCV1;
import SecureSocketChipV1.interfaces.SSCVCommand;

import java.util.UUID;

public class CommandExtentionServerDefaultCommands implements SSCVCommand {

    CommandExtentionServer main;

    public CommandExtentionServerDefaultCommands(CommandExtentionServer server){
        main = server;
    }


    @Override
    public void onCommand(SSCV1 sscv1, String command, String[] args, UUID uuid) {
        if(args.length == 0){
            if(command.equalsIgnoreCase("list")){
                System.out.println("There are " + main.getClientList().size() + " Clients Connected");
                for(String name : main.getClientList().keySet()){
                    System.out.println(name);
                }
            }
        }
    }
}
