package com.example.demo.gamexo;

public class Play {
    private GameXOPlayer player1;
    private GameXOPlayer player2;
    private int[][] board;
    private int status;
    private int turn;

    public Play(GameXOPlaying gameXOPlaying){
        this.player1 = gameXOPlaying.getPlayer1();
        this.player2 = gameXOPlaying.getPlayer2();
        this.board = gameXOPlaying.getBoard();
        this.status = gameXOPlaying.getStatus();
        this.turn = gameXOPlaying.getTurn();
    }
    public int getStatus() {
        return this.status;
    }

    public GameXOPlayer getPlayer1() {
        return this.player1;
    }

    public GameXOPlayer getPlayer2() {
        return this.player2;
    }

    public int[][] getBoard() {
        return this.board;
    }

    public int getTurn() {
        return this.turn;
    }
}
