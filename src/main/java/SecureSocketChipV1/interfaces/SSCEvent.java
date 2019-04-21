package SecureSocketChipV1.interfaces;

import SecureSocketChipV1.EventClasses.SSCCommandExecuteEvent;

public abstract class SSCEvent {

    public void onCommandExecute(SSCCommandExecuteEvent e){}
}
