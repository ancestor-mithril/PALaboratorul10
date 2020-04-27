package org.example;


import org.example.client.GameClient;

import java.io.IOException;

public class App {
    public static void main ( String [] args ){
        try{
            GameClient gameClient1=new GameClient("client1");
            //GameClient gameClient2=new GameClient("client2");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
