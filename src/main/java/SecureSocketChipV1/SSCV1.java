package SecureSocketChipV1;

import SecureSocketChipV1.interfaces.SSCEvent;
import SecureSocketChipV1.interfaces.SSCVCommand;
import SecureSocketChipV1.module.BaseCommand.SendKeyCommand;
import SecureSocketChipV1.module.CommandManager;
import SecureSocketChipV1.module.CommunicationsManager;
import SecureSocketChipV1.module.EncryptionManager;
import SecureSocketChipV1.module.ProtocolManager;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SSCV1 implements SSCVCommand{

    Socket socket;
    CommunicationsManager communicationsManager;
    EncryptionManager encryptionManager;

    ProtocolManager protocolManager;

    CommandManager commandManager;
    List<SSCVCommand> commandHandler = new ArrayList<>();
    List<SSCEvent> eventHandler = new ArrayList<>();

    SSCVCommand baseCommandHandler;

    public SSCV1(Socket socket){
        this.socket = socket;
        this.communicationsManager = new CommunicationsManager(this);
        this.communicationsManager.openCommunication();
        this.commandManager = new CommandManager(this);
        this.encryptionManager = new EncryptionManager(this);
        this.baseCommandHandler = this;
        this.protocolManager = new ProtocolManager(this);
    }

    @Override
    public void onCommand(SSCV1 sscv1, String command, String[] args) {
        if(args.length == 0){
            if(command.equalsIgnoreCase("PING")){
                getCom().sendMessage("PONG");
                return;
            }
        }
        if(args.length == 1){
            if(command.equalsIgnoreCase("SK")){
                new SendKeyCommand(this, command, args);
                return;
            }
        }
    }


    public CommandManager getCommandManager() {
        return commandManager;
    }

    public List<SSCVCommand> getCommandHandler() {
        return commandHandler;
    }

    public List<SSCEvent> getEventHandler() {
        return eventHandler;
    }

    public void registerCommandHandler(SSCEvent event){
        eventHandler.add(event);
    }

    public void addCommandHandler(SSCVCommand commands){
        this.commandHandler.add(commands);
    }

    public Socket getSocket() {
        return socket;
    }

    public CommunicationsManager getCom() {
        return communicationsManager;
    }

    public EncryptionManager getEncryptionManager() {
        return encryptionManager;
    }

    public SSCVCommand getBaseCommandHandler() {
        return baseCommandHandler;
    }

    public ProtocolManager getProtocolManager() {
        return protocolManager;
    }
}
