package com.example.demo.gamexo;

public class GameXORes {
    private int id_match;
    private int[][] board;
    private int type;
    private int winner;
    private int status;
    private GameXOPlayer player1;
    private GameXOPlayer player2;

    public GameXORes(int id_match, int type, int status) {
        this.id_match = id_match;
        this.type = type;
        this.status = status;
    }

    public GameXORes(GameXOPlaying gameXOPlaying) {
        this.id_match = gameXOPlaying.getIdmatch();
        this.player1 = gameXOPlaying.getPlayer1();
        this.player2 = gameXOPlaying.getPlayer2();
    }

    public GameXORes(int id_match, int[][] board, int winner) {
        this.id_match = id_match;
        this.board = board;
        this.winner = winner;
    }

    public int getWinner() {
        return this.winner;
    }

    public int getId_match() {
        return this.id_match;
    }

    public int getType() {
        return this.type;
    }

    public int getStatus() {
        return this.status;
    }

    public int[][] getBoard() {
        return this.board;
    }

    public GameXOPlayer getPlayer1() {
        return this.player1;
    }

    public GameXOPlayer getPlayer2() {
        return this.player2;
    }
}
