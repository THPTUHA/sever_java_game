package com.example.demo.gamexo;

public class EndGame {
    private GameXOPlayer player1;
    private GameXOPlayer player2;
    private int[][] board;
    private int status;

    public GameXOPlayer getPlayer1() {
        return this.player1;
    }

    public GameXOPlayer getPlayer2() {
        return this.player2;
    }

    public int[][] getBoard() {
        return this.board;
    }

    public int getStatus() {
        return this.status;
    }
}
