package org.example.threads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread {
    private Socket socket = null ;
    public ClientThread (Socket socket) { this.socket = socket ; }
    public void run () {
        try {
            // Get the request from the input stream: client → server
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            while(true){
                String request = in.readLine();
                // Send the response to the oputput stream: server → client
                System.out.println("[SERVER]:"+" client request:" +request);
                PrintWriter out = new PrintWriter(socket.getOutputStream());
                String raspuns=null;
                if (request.compareTo("exit")==0){
                    raspuns = "Client stopped ";
                    System.out.println("[SERVER]:"+raspuns);
                    break;
                }
                if (request.compareTo("stop")==0){
                    raspuns = "Server stopped ";
                    out.println(raspuns);
                    out.flush();
                    in.close();
                    out.close();
                    socket.close();
                    System.exit(0);
                }
                else {
                    raspuns = "Server received the request  " + request + " ... ";
                }
                out.println(raspuns);
                out.flush();
            }
        } catch (IOException e) {
            System.err.println("Communication error... " + e);
        } finally {
            try {

                socket.close(); // or use try-with-resources
            } catch (IOException e) { System.err.println (e); }
        }
    }
}