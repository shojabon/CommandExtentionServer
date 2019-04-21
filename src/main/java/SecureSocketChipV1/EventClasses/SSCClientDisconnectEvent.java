package SecureSocketChipV1.EventClasses;

import SecureSocketChipV1.SSCV1;

public class SSCClientDisconnectEvent {

    SSCV1 chip;

    public SSCClientDisconnectEvent(SSCV1 chip){
        this.chip = chip;
    }

    public SSCV1 getChip() {
        return chip;
    }
}
