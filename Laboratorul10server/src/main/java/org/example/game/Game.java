package org.example.game;

import javax.imageio.stream.IIOByteBuffer;
import java.util.ArrayList;
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


}
