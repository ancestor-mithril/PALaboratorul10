package org.example;


import org.example.game.Board;
import org.example.server.GameServer;

import java.awt.*;
import java.io.IOException;

public class App {
    public static void main ( String [] args ) {
        try {
            GameServer server = new GameServer ();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        Board board=new Board();
//        board.showBoard();
//        System.out.println();
//        board.setPositionBlack(3, 1);
//        board.showBoard();
//        System.out.println(board.toString());
    }
}
