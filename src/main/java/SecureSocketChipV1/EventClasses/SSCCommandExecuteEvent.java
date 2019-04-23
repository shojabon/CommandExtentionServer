package SecureSocketChipV1.EventClasses;

import SecureSocketChipV1.SSCV1;

import java.util.UUID;

public class SSCCommandExecuteEvent {
    String command;
    String[] args;
    UUID uuid;
    SSCV1 chip;

    public SSCCommandExecuteEvent(String command, String[] args, SSCV1 chip, UUID uuid){
        this.command = command;
        this.args = args;
        this.uuid = uuid;
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

    public UUID getUuid() {
        return uuid;
    }
}
