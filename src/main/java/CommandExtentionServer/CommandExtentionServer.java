package CommandExtentionServer;

import CommandExtentionServer.Commands.CommandExtentionServerDefaultCommands;
import SecureSocketChipV1.Enums.SSCV1Mode;
import SecureSocketChipV1.EventClasses.SSCClientConnectEvent;
import SecureSocketChipV1.EventClasses.SSCClientDisconnectEvent;
import SecureSocketChipV1.EventClasses.SSCCommandExecuteEvent;
import SecureSocketChipV1.SSCV1;
import SecureSocketChipV1.interfaces.SSCEvent;
import SecureSocketChipV1.interfaces.SSCVCommand;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class CommandExtentionServer extends SSCEvent {

    ServerSocket socket;
    boolean connectionOpen = false;

    boolean accecptCommand = false;

    String localCommandClientIp;

    SSCV1 localClient;

    HashMap<String, SSCV1> clientList = new HashMap<>();

    int port;

    public CommandExtentionServer(int port){
        this.port = port;
        log("Starting Server...");
        createFolderIfNotExist(new File("plugins"));
        try {
            log("Starting Local Command Client");
            socket = new ServerSocket(port);
            openConnection();
            log("Server Started");
            openCommandConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void openCommandConnection(){
        if(!connectionOpen) return;
        if(accecptCommand) return;
        accecptCommand = true;
        try {
            SSCV1 commandClient = new SSCV1(new Socket("127.0.0.1", port), SSCV1Mode.SERVER, null, null);
            localClient = commandClient;
            localCommandClientIp = commandClient.getSocket().getRemoteSocketAddress().toString();
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            while(accecptCommand){
                String input = br.readLine();
                localClient.getCom().sendMessage(input);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeCommandConnection(){
        accecptCommand = false;
    }

    public void log(String message){
        System.out.println("[INFO] " + message);
    }

    public void openConnection(){
        connectionOpen = true;
        Thread t = new Thread(() -> {
            while (connectionOpen){
                try {
                    Socket cSock = socket.accept();
                    SSCV1 clientChip = new SSCV1(cSock, SSCV1Mode.SERVER,null,this);
                    //clientChip.addCommandHandler(new CommandExtentionServerDefaultCommands(this));
                    clientList.put(cSock.getRemoteSocketAddress().toString(),clientChip);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    @Override
    public void onCommandExecute(SSCCommandExecuteEvent e) {
        StringBuilder a  = new StringBuilder();
        for(String b : e.getArgs()){
            a.append(" ").append(b);
        }
        log(e.getChip().getSocket().getRemoteSocketAddress() + " Executed " + e.getCommand() + a.toString());
        if(e.getUuid() != null){
            log(e.getUuid() + " C");
        }
    }

    @Override
    public void onClientConnect(SSCClientConnectEvent e) {
        log(e.getChip().getSocket().getRemoteSocketAddress().toString() + " Connected to server");
    }

    @Override
    public void onClientDisconnect(SSCClientDisconnectEvent e) {
        log(e.getChip().getSocket().getRemoteSocketAddress().toString() + " Disconnected from server");
        clientList.remove(e.getChip().getSocket().getRemoteSocketAddress().toString());
    }



    public void closeConnection(){
        connectionOpen = false;
    }

    boolean createFolderIfNotExist(File file){
        if(file.exists()) return false;
        return file.mkdir();
    }

    public HashMap<String, SSCV1> getClientList() {
        return clientList;
    }

}
