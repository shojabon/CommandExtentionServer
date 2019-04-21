package SecureSocketChipV1.module;

import SecureSocketChipV1.SSCV1;

import java.io.IOException;

public class ProtocolManager {

    SSCV1 main;

    public ProtocolManager(SSCV1 main){
        this.main = main;
    }

    public void disconnect(){
        if(main.getSocket().isClosed()) return;
        main.getCom().closeCommunication();
        try {
            main.getSocket().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exchangeKeysProtocol(){
        try {
            main.getCom().sendMessage("SK " + main.getEncryptionManager().exportPublic());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
