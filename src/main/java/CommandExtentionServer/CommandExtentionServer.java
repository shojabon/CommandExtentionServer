package CommandExtentionServer;

import SecureSocketChipV1.SSCV1;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CommandExtentionServer{

    ServerSocket socket;
    boolean connectionOpen = false;

    boolean accecptCommand = false;

    SSCV1 localClient;

    List<SSCV1> clientList = new ArrayList<>();

    int port;

    public CommandExtentionServer(int port){
        this.port = port;
        createFolderIfNotExist(new File("plugins"));
        try {
            socket = new ServerSocket(port);
            openConnection();
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
            localClient = new SSCV1(new Socket("127.0.0.1", port));
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




    public void openConnection(){
        connectionOpen = true;
        Thread t = new Thread(() -> {
            while (connectionOpen){
                try {
                    clientList.add(new SSCV1(socket.accept()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }


    public void closeConnection(){
        connectionOpen = false;
    }

    boolean createFolderIfNotExist(File file){
        if(file.exists()) return false;
        return file.mkdir();
    }

}
