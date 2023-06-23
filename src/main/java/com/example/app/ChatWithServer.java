package com.example.app;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatWithServer extends Thread {

    private int ClientNbre;
    private List<Communication> clientsconnectes = new ArrayList<Communication>();
    public static void main(String[] args) {
        new ChatWithServer () .start();

    }
    @Override
    public void run(){
        try {
            ServerSocket ss = new ServerSocket(11);
            System.out.println("Le serveur essaie de démarrer ...");
            while (true){
                Socket s= ss.accept();// accepter le client
                ++ClientNbre;

                Communication NewCommunication = new Communication(s,ClientNbre);// communications
                clientsconnectes.add(NewCommunication);
                NewCommunication.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // class intern
    public class Communication extends Thread {
        private int ClientNumber;
        private Socket s;

        Communication(Socket s, int ClientNumber) {

            this.s = s;
            this.ClientNumber = ClientNumber;
        }

        @Override
        public void run() {
            try {
                InputStream is = s.getInputStream();//octet
                InputStreamReader isr = new InputStreamReader(is);//caractere
                BufferedReader br = new BufferedReader(isr);//chaine de caractere
                OutputStream os = s.getOutputStream();
                String Ip = s.getRemoteSocketAddress().toString();
                System.out.println("Le Client numéro " + ClientNumber + "et son IP " + Ip);
                PrintWriter pw = new PrintWriter(os, true);
                pw.println("Vous etes le client " + ClientNumber);
                pw.println("Envoyer le message que vous voulez ..... ");

                while (true) {
                    String UserRequest = br.readLine();
                    if(UserRequest.contains("=>")){
                        String[] usermessage = UserRequest.split("=>");
                        if(usermessage.length ==2){
                            String msg = usermessage[1];
                            int numeroClient = Integer.parseInt(usermessage[0]);
                            Send(msg,s,numeroClient);
                        }
                    }
                    else {
                        Send(UserRequest,s,-1);
                    }


                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        void Send(String UserRequest, Socket socket,int nbre) throws IOException {
            for (Communication client : clientsconnectes) {
                if(client.ClientNumber == nbre || nbre == -1 )
                    if (client.s != socket) {
                        PrintWriter pw = new PrintWriter(client.s.getOutputStream(), true);
                        pw.println(UserRequest);

                    }
            }
        }
    }
}
