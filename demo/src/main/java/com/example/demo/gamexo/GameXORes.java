package com.example.demo.gamexo;

public class GameXORes {
    private int id_match;
    private int[][] board;
    private int type;
    private int winner;

    public GameXORes(int id_match,int type){
        this.id_match=id_match;
        this.type=type;
    }
    public GameXORes(int id_match,int[][]board,int winner){
        this.id_match=id_match;
        this.board=board;
        this.winner=winner;
    }
    public int getWinner(){return this.winner;}
    public int getId_match(){return this.id_match;}
    public int getType(){return this.type;}
    public int[][] getBoard(){return this.board;}
}
