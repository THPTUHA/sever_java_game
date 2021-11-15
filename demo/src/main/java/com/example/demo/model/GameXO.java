package com.example.demo.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GameXO {
    @Value("${GameXO.id}")
    private int id;

    private final int size_board = 3;
    private final int condition_win = 3;
    private final int user_num = 2;
    private int[][] board;

    public GameXO(){
        this.board=new int[this.size_board][this.size_board];
    }
    public int getSize_board(){return this.size_board;}
    public int getId(){return this.id;}
    public int getCondition_win(){return this.condition_win;}
    public int getUser_num(){return this.user_num;}
    public int[][] getBoard(){return this.board;}
    public void setBoard(int x,int y,int type){board[x][y] = type;}
    public void resetBoard(){
        this.board=new int[this.size_board][this.size_board];
    }
    // @Override 
    // public String toString(){
    //     return "idMatch: "+this.id_match+ "\nidUser1:"+getId_user1() +"\nidUser2:"+getId_user2();
    // }
}
