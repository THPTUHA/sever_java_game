package com.example.demo.gamexo;

public class ReponsePlayer {
    private GameXOPlayer player1;
    private GameXOPlayer player2;
    private int status;
    public ReponsePlayer(GameXOPlaying gameXOPlaying){
        this.player1 = gameXOPlaying.getPlayer1();
        this.player2 = gameXOPlaying.getPlayer2();
        this.status = gameXOPlaying.getStatus();
    }
    public GameXOPlayer getPlayer1(){
        return this.player1;
    }
    public GameXOPlayer getPlayer2(){
        return this.player2;
    }
    public int getStatus(){
        return this.status;
    }
}
