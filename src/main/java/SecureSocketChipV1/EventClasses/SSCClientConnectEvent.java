package SecureSocketChipV1.EventClasses;

import SecureSocketChipV1.SSCV1;

public class SSCClientConnectEvent {

    SSCV1 chip;

    public SSCClientConnectEvent(SSCV1 chip){
        this.chip = chip;
    }

    public SSCV1 getChip() {
        return chip;
    }
}
