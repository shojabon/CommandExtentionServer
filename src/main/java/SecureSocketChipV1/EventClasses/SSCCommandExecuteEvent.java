package SecureSocketChipV1.EventClasses;

import SecureSocketChipV1.SSCV1;

public class SSCCommandExecuteEvent {
    String command;
    String[] args;
    SSCV1 chip;

    public SSCCommandExecuteEvent(String command, String[] args, SSCV1 chip){
        this.command = command;
        this.args = args;
        this.chip = chip;
    }

    public String getCommand() {
        return command;
    }

    public String[] getArgs() {
        return args;
    }

    public SSCV1 getChip() {
        return chip;
    }
}
