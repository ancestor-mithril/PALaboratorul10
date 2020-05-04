package org.example.game;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private int length;
    private List<List<Integer>> matrix = new ArrayList<>();
    char matrice[][]=new char[10][10];
    private String round="white";
    private String gameState="Game may continue ";

    public String getGameState() {
        return gameState;
    }

    /**
     * in constructor initializam matricea
     */
    public Board() {
        this.length=10;
        for (int i=0; i<this.length; i++){
            matrix.add(new ArrayList<Integer>());
            for (int j=0; j<this.length; j++)
                matrix.get(i).add(0);
        }
    }

    /**
     * getter
     * @return
     */
    public int getLength() {
        return length;
    }

    /**
     * getter
     * @return
     */
    public List<List<Integer>> getMatrix() {
        return matrix;
    }
    /**
     * verifica daca o pozitie din matrice este goala
     * @param n
     * @param m
     * @return
     */
    public boolean isPositionEmpty(int n, int m){
        return matrix.get(n).get(m)==0;
    }
    /**
     * verifica daca o pozitie din matrice este negru
     * @param n
     * @param m
     * @return
     */
    public boolean isPositionBlack(int n, int m){
        return matrix.get(n).get(m)==2;
    }

    /**
     * verifica daca o pozitie din matrice este alb
     * @param n
     * @param m
     * @return
     */
    public boolean isPositionWhite(int n, int m){
        return matrix.get(n).get(m)==1;
    }
    /**
     * modifica o pozitie in matrice in alb
     * @param n
     * @param m
     */
    public void setPositionWhite(int n, int m){
        if (gameIsOver())
            return;
        List<List<Integer>> aux = new ArrayList<>();
        for (int i=0; i<n; i++)
            aux.add(matrix.get(i));
        aux.add(new ArrayList<Integer>());
        for (int i=0; i<m; i++)
            aux.get(n).add(matrix.get(n).get(i));
        aux.get(n).add(1);
        for (int i=m+1; i<length; i++)
            aux.get(n).add(matrix.get(n).get(i));
        for (int i=n+1; i<length; i++)
            aux.add(matrix.get(i));
        matrix=aux;
        if (!gameIsOver())
            round="black";
        else
            gameState="white has won";
    }

    /**
     * modifica o pozitie in matrice in negru
     * @param n
     * @param m
     */
    public void setPositionBlack(int n, int m){
        if (gameIsOver())
            return;
        List<List<Integer>> aux = new ArrayList<>();
        for (int i=0; i<n; i++)
            aux.add(matrix.get(i));
        aux.add(new ArrayList<Integer>());
        for (int i=0; i<m; i++)
            aux.get(n).add(matrix.get(n).get(i));
        aux.get(n).add(2);
        for (int i=m+1; i<length; i++)
            aux.get(n).add(matrix.get(n).get(i));
        for (int i=n+1; i<length; i++)
            aux.add(matrix.get(i));
        matrix=aux;
        if (!gameIsOver())
            round="white";
        else
            gameState="black has won";
    }

    /**
     * o metoda de afisat e server tabla de joc, pentru a vedea modificarile
     */
    public void showBoard(){
        for (int i=0; i<length; i++) {
            for (int j=0; j<length; j++) {
                System.out.print(matrix.get(i).get(j));
                System.out.print(' ');
            }
            System.out.println();
        }
    }

    /**
     * metoda verifica daca exista 5 piese negre sau albe in acelasi rand, coloana sau diagonala
     * @return true daca jocul s-a incheiat, fals daca nu
     */
    public boolean gameIsOver(){
        boolean flag=false;
        for (int i=0; i<length; i++)
            for (int j=0; j<length; j++) {
                if (isPositionBlack(i, j)){
                    if (i+4<length){
                        flag=true;
                        for (int k=0; k<5; k++)
                            if (!isPositionBlack(i+k,j )) {
                                flag = false;
                                break;
                            }
                        if (flag)
                            return true;
                    }
                    if (j+4<length){
                        flag=true;
                        for (int k=0; k<5; k++)
                            if (!isPositionBlack(i,j+k)) {
                                flag = false;
                                break;
                            }
                        if (flag)
                            return true;
                    }
                    if (j+4<length&&i+4<length){
                        flag=true;
                        for (int k=0; k<5; k++)
                            if (!isPositionBlack(i+k,j+k)) {
                                flag = false;
                                break;
                            }
                        if (flag)
                            return true;
                    }
                    if (j-4>=0&&i+4<length){
                        flag=true;
                        for (int k=0; k<5; k++)
                            if (!isPositionBlack(i+k,j-k)) {
                                flag = false;
                                break;
                            }
                        if (flag)
                            return true;
                    }
                } else if (isPositionWhite(i, j)){
                    if (i+4<length){
                        flag=true;
                        for (int k=0; k<5; k++)
                            if (!isPositionWhite(i+k,j )) {
                                flag = false;
                                break;
                            }
                        if (flag)
                            return true;
                    }
                    if (j+4<length){
                        flag=true;
                        for (int k=0; k<5; k++)
                            if (!isPositionWhite(i,j+k)) {
                                flag = false;
                                break;
                            }
                        if (flag)
                            return true;
                    }
                    if (j+4<length&&i+4<length){
                        flag=true;
                        for (int k=0; k<5; k++)
                            if (!isPositionWhite(i+k,j+k)) {
                                flag = false;
                                break;
                            }
                        if (flag)
                            return true;
                    }
                    if (j-4>=0&&i+4<length){
                        flag=true;
                        for (int k=0; k<5; k++)
                            if (!isPositionWhite(i+k,j-k)) {
                                flag = false;
                                break;
                            }
                        if (flag)
                            return true;
                    }
                }
            }
        return false;
    }

    /**
     * o metoda toString
     * @return
     */
    @Override
    public String toString() {
        String string= "\t";
        for (int i=0; i<length; i++)
            string+=matrix.get(i)+"\t";
        return string;
    }

    /**
     *
     * @return intoarce runda, "black" sau "white"
     */
    public String getRound() {
        return round;
    }
}
