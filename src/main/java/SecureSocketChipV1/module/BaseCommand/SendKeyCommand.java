package SecureSocketChipV1.module.BaseCommand;

import SecureSocketChipV1.Enums.SSCV1Mode;
import SecureSocketChipV1.SSCV1;

public class SendKeyCommand {
    SSCV1 main;
    public SendKeyCommand(SSCV1 main, String command, String[] args){
        this.main = main;
        try {
            if(main.getCom().isCommunicationEncrypted()) return;
            main.getEncryptionManager().setServerPublic(main.getEncryptionManager().importPublic(args[0]));
            if(main.getMode() == SSCV1Mode.SERVER)  main.getCom().sendMessage("SK " + main.getEncryptionManager().exportPublic());
            main.getCom().setCommunicationEncrypted(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
