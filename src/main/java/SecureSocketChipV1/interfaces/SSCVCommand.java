package SecureSocketChipV1.interfaces;

import SecureSocketChipV1.SSCV1;

import java.util.UUID;

public interface SSCVCommand {
    void onCommand(SSCV1 sscv1, String command, String[] args, UUID uuid);
}
