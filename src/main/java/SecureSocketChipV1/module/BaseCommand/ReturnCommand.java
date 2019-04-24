package SecureSocketChipV1.module.BaseCommand;

import SecureSocketChipV1.SSCV1;

import java.util.UUID;

public class ReturnCommand {

    SSCV1 main;

    public ReturnCommand(SSCV1 main, String command, String[] args, UUID uuid){
        this.main = main;
        main.registerReturnValue(uuid.toString(), args[0]);
    }


}
