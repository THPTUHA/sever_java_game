package com.example.demo.gamexo;

public class GameXORes {
    private int id_match;
    private int[][] board;
    private int type;
    private int status;
    private int turn;
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
        this.board = gameXOPlaying.getBoard();
        this.status =gameXOPlaying.getStatus();
        this.turn = gameXOPlaying.getTurn();
    }

    public GameXORes(GameXOPlaying gameXOPlaying, int id , int status) {
        this.id_match = gameXOPlaying.getIdmatch();
        this.player1 = gameXOPlaying.getPlayer1();
        this.player2 = gameXOPlaying.getPlayer2();
        this.board = gameXOPlaying.getBoard();
        this.status =  status;
        this.type = gameXOPlaying.getPlayerById(id).getType();
        this.turn = gameXOPlaying.getTurn();
        System.out.println("type game:"+this.type);
    }

    public GameXORes(int status) {
        this.status=status;
    }

    public GameXORes(int type,int status) {
        this.type = type;
        this.status=status;
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
    
    public int getTurn() {
        return this.turn;
    }
}
