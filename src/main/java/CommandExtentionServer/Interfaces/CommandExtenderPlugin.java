package CommandExtentionServer.Interfaces;

import CommandExtentionServer.DataClasses.CommandSender;

public abstract class CommandExtenderPlugin {

    public void onEnable(){}

    public void onDisable(){}

    public void onCommand(CommandSender sender, String command, String[] args){}


}
