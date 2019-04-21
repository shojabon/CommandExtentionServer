package CommandExtentionServer.DataClasses;

public class CommandSender {
    SenderType type;

    public CommandSender(SenderType type){
        this.type = type;
    }
}


enum SenderType{
    CLIENT,
    SERVER
}
