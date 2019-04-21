package SecureSocketChipV1.interfaces;

import SecureSocketChipV1.EventClasses.SSCClientConnectEvent;
import SecureSocketChipV1.EventClasses.SSCClientDisconnectEvent;
import SecureSocketChipV1.EventClasses.SSCCommandExecuteEvent;

public abstract class SSCEvent {

    public void onCommandExecute(SSCCommandExecuteEvent e){}

    public void onClientConnect(SSCClientConnectEvent e){}

    public void onClientDisconnect(SSCClientDisconnectEvent e){}
}
