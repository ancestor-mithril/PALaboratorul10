package org.example.game;

import javax.imageio.stream.IIOByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Game {
    String name;
    Board board;
    List<Player> players;

    /**
     * initializeaza un joc
     * @param name
     */
    public Game(String name){
        this.name=name;
        board=new Board();
        players=new ArrayList<>();
    }

    /**
     * adauga un player in joc
     * @param player
     */
    public void joinGame(Player player) {
        players.add(player);
    }

    /**
     * getter
     * @return
     */
    public Board getBoard() {
        return board;
    }

    /**
     * getter
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * getter
     * @return
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * acesta este un ai care joaca doar pe negru, avand caracter blocant. El incearca sa blocheze jucatorul alb prin punerea
     * piesei negre exact in jurul ultimei piese puse de jucatorul in alb
     * in cazul in care nu are loc sa puna piesa blocant, o va pune random
     * @param n
     * @param m
     */
    public void ai(int n, int m){
        if (!block(n, m))
            while(true){
                int i= (int) Math.floor(Math.random()*board.getLength());
                int j= (int) Math.floor(Math.random()*board.getLength());
                if (board.isPositionEmpty(i, j)){
                    board.setPositionBlack(i, j);
                    return;
                }
            }
    }

    /**
     * cauta pozitiile din jurul ultimei piese puse de jucatorul in alb si pune o piesa neagra
     * @param n
     * @param m
     * @return
     */
    public boolean block(int n, int m){
        List<Integer> line= Arrays.asList(-1, 0, 1);
        List<Integer> col=Arrays.asList(-1, 0, 1);
        Collections.shuffle(line);
        Collections.shuffle(col);
        for (int i: line)
            for (int j : col)
                if (ok(n+i, m+j)){
                    if (board.isPositionEmpty(n+i, m+j)){
                        board.setPositionBlack(n+i, m+j);
                        return true;
                    }
                }
        return false;
    }

    /**
     * verifica corectitudinea pozitiei introduse
     * @param i
     * @param j
     * @return
     */
    public boolean ok(int i, int j){
        return i >= 0 && j >= 0 && i < board.getLength() && j < board.getLength();
    }
}
