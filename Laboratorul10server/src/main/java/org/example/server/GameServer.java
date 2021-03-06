package org.example.server;


import org.example.game.Game;
import org.example.threads.ClientThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer {
    Game game=null;
    // Define the port on which the server is listening
    public static final int PORT = 8100;

    /**
     * se deschide cate un nou thread pt fiecare server
     * @throws IOException
     */
    public GameServer() throws IOException {
        ServerSocket serverSocket = null ;
        try {
            serverSocket = new ServerSocket(PORT);
            while (true) {
                System.out.println ("Waiting for a client ...");
                Socket socket = serverSocket.accept();
                // Execute the client's request in a new thread
                new ClientThread(socket, this).start();
            }
        } catch (IOException e) {
            System.err. println ("Ooops... " + e);
        } finally {
            serverSocket.close();
        }
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void stopGame() {
        game=null;
    }
}