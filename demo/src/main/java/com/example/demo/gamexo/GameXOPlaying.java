package com.example.demo.gamexo;

import com.example.demo.model.GameXO;

public class GameXOPlaying {
    private GameXO game;
    private int turn=1;
    public GameXOPlaying(int id_match,int id_user1,int id_user2){
        this.game=new GameXO(id_match,id_user1,id_user2);
    }

    public int getSizeBoard(){
        return game.getSize_board();
    }
    public int getConditionWin(){
        return game.getCondition_win();
    }
    public int[][] getBoard(){
        return game.getBoard();
    }
    public int getIdmatch(){
        return this.game.getId_match();
    }
    public int getTurn(){return this.turn;}
    public void play(int x,int y){
        game.setBoard(x, y, this.turn);
    }
    public void setTurn(){this.turn = 3 - this.turn;}
    @Override
    public String toString(){
        return game.toString()+ "\nturn:"+this.turn;
    }
}
