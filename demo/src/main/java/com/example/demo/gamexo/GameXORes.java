package com.example.demo.gamexo;

public class GameXORes {
    private int id_match;
    private int[][] board;
    private int type;
    private int winner;
    private int status;
    private GameXOPlayer player1;
    private GameXOPlayer player2;
    private String user_name;
    private String message;
    private boolean loading;

    public GameXORes(int id_match, int type, int status) {
        this.id_match = id_match;
        this.type = type;
        this.status = status;
    }

    public GameXORes(GameXOPlaying gameXOPlaying,boolean loading) {
        this.id_match = gameXOPlaying.getIdmatch();
        this.player1 = gameXOPlaying.getPlayer1();
        this.player2 = gameXOPlaying.getPlayer2();
        this.board = gameXOPlaying.getBoard();
        this.status =gameXOPlaying.getStatus();
        this.loading = loading;
    }

    public GameXORes(GameXOPlaying gameXOPlaying,boolean loading,int id) {
        this.id_match = gameXOPlaying.getIdmatch();
        this.player1 = gameXOPlaying.getPlayer1();
        this.player2 = gameXOPlaying.getPlayer2();
        this.board = gameXOPlaying.getBoard();
        this.status =gameXOPlaying.getStatus();
        this.loading = loading;
        this.type = gameXOPlaying.getPlayerById(id).getType();
        System.out.println("type game:"+this.type);
    }

    public GameXORes(int id_match, int[][] board,int turn, int winner,int status) {
        this.id_match = id_match;
        this.board = board;
        this.winner = winner;
        this.status = status;
        this.type = turn;
    }

    public GameXORes(int id_match, int[][] board, int winner, GameXOPlayer player1, GameXOPlayer player2,int status){
        this.id_match = id_match;
        this.board = board;
        this.winner = winner;
        this.player1=player1;
        this.player2=player2;
        this.status=status;
    }

    public GameXORes(GameXOPlayer player1,int status) {
        this.player1=player1;
        this.status=status;
    }

    public GameXORes(GameXOPlayer player,String message,int status) {
        this.player1=player;
        this.message=message;
        this.status = status;
    }

    public GameXORes(int status) {
        this.status=status;
    }

    public GameXORes(int type,int status) {
        this.type = type;
        this.status=status;
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

    public String getUser_name() {
        return this.user_name;
    }

    public String getMessage() {
        return this.message;
    }

    public boolean getLoading() {
        return this.loading;
    }
}
