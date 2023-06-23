package com.example.app;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.Socket;


public class Scene2 {

    @FXML
    private TextField messageID;
    @FXML
    private TextField PortID;
    @FXML
    private TextField HostID;
    @FXML
    public ListView  view;
    PrintWriter pw;

    public Scene2() {
    }

    @FXML
    protected void onconnect() throws Exception {
         String host=HostID.getText();
         int port = Integer.parseInt(PortID.getText());
         // Socket
        Socket s = new Socket(host,port);
        InputStream is = s.getInputStream();//octet
        InputStreamReader isr = new InputStreamReader(is);//caractere
        BufferedReader br = new BufferedReader(isr);//chaine de caractere
        OutputStream os = s.getOutputStream();
        String Ip = s.getRemoteSocketAddress().toString();
        pw = new PrintWriter(os,true);
        new Thread(()-> {
            while (true){
                try {
                    String reponse = br.readLine();
                    Platform.runLater(()->{
                        view.getItems().add(reponse);
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    @FXML
    public void onsend(){
        String message = messageID.getText();
        pw.println(message);
    }
}