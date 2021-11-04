package com.example.demo.model;

public class GameXO {
    private int id;
    private int id_match;
    private int id_user1;
    private int id_user2;
    private int size_board = 3;
    private int condition_win = 3;
    private int[][] board;

    public GameXO(int id_match,int id_user1){
        this.id_match=id_match;
        this.id_user1=id_user1;
        this.board=new int[this.size_board][this.size_board];
    }
    public int getSize_board(){return this.size_board;}
    public int getId(){return this.id;}
    public int getCondition_win(){return this.condition_win;}
    public int getId_match(){return this.id_match;}
    public int getId_user1(){return this.id_user1;}
    public int getId_user2(){return this.id_user2;}
    public int[][] getBoard(){return this.board;}
    public void setId_user2(int id_user2){this.id_user2=id_user2;}
    public void setBoard(int x,int y,int type){board[x][y] = type;}
    @Override 
    public String toString(){
        return "idMatch: "+this.id_match+ "\nidUser1:"+this.id_user1+"\nidUser2:"+this.id_user2;
    }
}
