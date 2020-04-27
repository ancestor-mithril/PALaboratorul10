package org.example;


import org.example.client.GameClient;

import java.io.IOException;

public class App {
    /**
     * metoda main, putem instantia doar un singur client, pentru mai multi clienti: trebuie deschise mai multe console si rulat din cmd cu comanda:
     * java org.example.App nume
     * ar putea di trimis prin parametru numele clientului
     * @param args
     */
    public static void main ( String [] args ){
        try{
            String name=null;
            if (args.length==0)
                name="Nameless";
            else
                name=args[0];
            GameClient gameClient1=new GameClient(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
