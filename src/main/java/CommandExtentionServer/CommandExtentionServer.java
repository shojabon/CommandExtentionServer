package CommandExtentionServer;

import SecureSocketChipV1.SSCV1;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommandExtentionServer{


    SSCV1 chip ;
    ServerSocket socket;
    boolean connectionOpen = false;

    boolean accecptCommand = false;

    List<SSCV1> clientList = new ArrayList<>();

    public CommandExtentionServer(int port){
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
        accecptCommand = true;
        Scanner scanner = new Scanner(System.in);
        while(accecptCommand){

            String input = scanner.nextLine();

            System.out.println(input);
        }
        scanner.close();
    }

    public void closeCommand(){
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
