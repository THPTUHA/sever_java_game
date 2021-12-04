package com.example.demo.game.gamexo;

public class Play {
    private GameXOPlayer player1;
    private GameXOPlayer player2;
    private int[][] board;
    private int status;
    private int turn;
    private long time;

    public Play(GameXOPlaying gameXOPlaying){
        this.player1 = gameXOPlaying.getPlayer1();
        this.player2 = gameXOPlaying.getPlayer2();
        this.board = gameXOPlaying.getBoard();
        this.status = gameXOPlaying.getStatus();
        this.turn = gameXOPlaying.getTurn();
        this.time = gameXOPlaying.getTime_run();
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

    public long getTime() {
        return time;
    }
}
