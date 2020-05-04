package org.example.threads;

import org.example.game.Board;
import org.example.game.Game;
import org.example.game.Player;
import org.example.server.GameServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class ClientThread extends Thread {
    private Socket socket = null ;
    private String player=null;
    private int game=0;
    GameServer gameServer;
    public ClientThread (Socket socket, GameServer gameServer) { this.socket = socket ; this.gameServer=gameServer; }

    /**
     * se comunica cu clientul prin tcp intr-un while. Se opreste executia threadului atunci cand este primit exit sau stop
     * daca se primeste create game creaza un joc daca nu este deja unul creat
     * daca se primeste join game adauga un player nou la un joc, daca sunt mai putin de 2 (jocul e doar in 2, alb si negru)
     * daca se primeste show board, se afiseaza tabla de joc
     * daca se primeste submit move, se adauga o piesa de culoarea jucatorului pe pozitia respectiva, daca este posibil
     * se tine cont de tura carui jucator este
     * este implementat un sistem de ture, fiecare jucator poate muta doar pe rand
     * in caz de mutare necorespunzatoare, se intoarce un raspuns
*/    public void run () {
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
                    return;
                }
                else if (request.compareTo("stop")==0){
                    raspuns = "Server stopped ";
                    out.println(raspuns);
                    out.flush();
                    //varianta de oprire a serverului
                    /*in.close();
                    out.close();
                    socket.close();
                    System.exit(0);*/
                    //varianta de oprire a threadului
                    return;
                }
                else if (request.compareTo("help")==0){
                    raspuns = "You may \'create game\' \t\'create game ai\' \t\'join game\' \t\'submit move n m\'\t\'show board\'\t\'exit\'\t\'stop\'\t";
                }
                else if (processGame().equals("Game may continue ")){
                    if (request.compareTo("create game")==0){
                        if (this.player!=null)
                            raspuns="You are already in a game";
                        else if (this.gameServer.getGame()==null){
                            this.game=1;
                            raspuns = "The game was created, join game to play it";
                            this.gameServer.setGame(new Game("This game"));
                        }
                        else
                            raspuns = "Game was already created. Please join it";
                    }
                    else if (request.compareTo("create game ai")==0){
                            if (this.player!=null)
                                raspuns="You are already in a game";
                            else if (this.gameServer.getGame()==null){
                                this.game=2;
                                raspuns = "The game was created, join game to play it";
                                this.gameServer.setGame(new Game("This game"));
                            }
                            else
                                raspuns = "Game was already created. Please join it";
                    }
                    else if (request.compareTo("join game")==0){
                        if (this.player!=null)
                            raspuns = "you have already joined a game. Please \"submit move n m\", where n, m<10, or  \"show board\"";
                        else if (this.gameServer.getGame()==null)
                            raspuns = "The game was not created. Please create a game first. ";
                        else {
                            if (this.game==1){
                                if (this.gameServer.getGame().getPlayers().size()==1){
                                    this.player="black";
                                    this.gameServer.getGame().joinGame(new Player("black"));
                                    raspuns = "You have joined the game as black. \t Your possible moves are \"submit move n m\", where n," +
                                            "m<12 \t \"show board\", it will shoe the board";
                                }
                                else if (this.gameServer.getGame().getPlayers().size()==0){
                                    this.player="white";
                                    this.gameServer.getGame().joinGame(new Player("white"));
                                    raspuns = "You have joined the game as white. \t Your possible moves are \"submit move n m\", where n," +
                                            "m<10 \t \"show board\", it will shoe the board";
                                } else
                                    raspuns = "This game is only for 2 players, you have no place here.";
                            }
                            else {
                                if (this.gameServer.getGame().getPlayers().size()==0){
                                    this.player="white";
                                    this.gameServer.getGame().joinGame(new Player("white"));
                                    raspuns = "You have joined the game as white and you play against AI. \t Your possible moves are \"submit move n m\", where n," +
                                            "m<10 \t \"show board\", it will shoe the board";
                                } else
                                    raspuns = "This game is only for 1 player and an AI, you have no place here.";
                            }
                        }

                    }
                    else if (request.matches("submit move . .")){
                        raspuns = submitMove(request);
                    }
                    else if (request.compareTo("show board")==0){
                        raspuns = this.gameServer.getGame().getBoard().toString();
                    }
                    else {
                        raspuns = "Server received the request  " + request + " ... " + " This is an invalid request. \t Write \'help\'";
                    }
                } else {
                    if (request.compareTo("show board")==0){
                        raspuns = this.gameServer.getGame().getBoard().toString();
                    } else
                        raspuns = processGame()+"\t You may only look at the board with \'show board\' or help or exit or stop";
                }
                System.out.println("[SERVER]: raspuns: "+raspuns);
                out.println(raspuns);
                out.flush();
            }
        } catch (IOException e) {
            System.err.println("Communication error... " + e);
        }
        finally {
            try {
                socket.close(); // or use try-with-resources
            } catch (IOException e) { System.err.println (e); }
        }
    }

    /**
     * verifica requestul pentru a vedea daca miscarea la care s-a dat submit este corecta
     * @param request
     * @return un mesaj, fie de eroare, fie ca mutarea a fost facuta cu succes
     */
    private String submitMove(String request){
        if (this.player==null)
            return "You are not in a game. Please join a game";
        boolean flag= this.gameServer.getGame().getBoard().getRound().equals(this.player);
        if (!flag)
            return "The player who should move now is " + this.gameServer.getGame().getBoard().getRound();
        int n, m;
        try{
            n=Integer.parseInt(String.valueOf(request.charAt(12)));
            m=Integer.parseInt(String.valueOf(request.charAt(14)));
        } catch (NumberFormatException e) {
            return "Number format exception";
        }

        if (this.gameServer.getGame().getBoard().isPositionEmpty(n, m)){
            if (this.player.equals("white"))
                this.gameServer.getGame().getBoard().setPositionWhite(n, m);
            else
                this.gameServer.getGame().getBoard().setPositionBlack(n, m);
            if (this.game==2)
                this.gameServer.getGame().ai(n, m);
            return "Move was successful\t " + processGame();
        }else
            return "The position [n, m] is not empty. Please submit another move ";

    }

    /**
     * metoda care verifica starea jocului
     * @return starea jocului
     */
    private String processGame() {
        if (this.gameServer.getGame()==null)
            return "Game may continue ";
        return this.gameServer.getGame().getBoard().getGameState();
    }
}