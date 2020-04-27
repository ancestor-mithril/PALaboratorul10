package org.example.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class GameClient {
    public static void main (String[] args) throws IOException {
        String serverAddress = "127.0.0.1"; // The server's IP address
        int PORT = 8100; // The server's port
        try (
                Socket socket = new Socket(serverAddress, PORT);
                PrintWriter out =
                        new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader (
                        new InputStreamReader(socket.getInputStream())) ) {
            String request = null;
            BufferedReader reader= new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                System.out.println(socket.isClosed());
                if (socket.isClosed())
                    break;
                // Send a request to the server
                request=reader.readLine();
                if (request==null){
                    in.close();
                    out.close();
                    socket.close();
                    break;
                }
                out.println(request);
                if (request.compareTo("exit")==0){
                    in.close();
                    out.close();
                    socket.close();
                    System.exit(0);
                }

                // Wait the response from the server ("Hello World!")
                String response;
                if ((response= in.readLine ())==null)
                    break;

                System.out.println("[CLIENT]: "+response);
                if (request.compareTo("stop")==0){
                    in.close();
                    out.close();
                    socket.close();
                    System.exit(0);
                }
            }

        } catch (UnknownHostException e) {
            System.err.println("No server listening... " + e);
        }
    }
}