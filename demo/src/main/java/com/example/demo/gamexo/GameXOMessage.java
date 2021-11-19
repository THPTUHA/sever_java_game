package com.example.demo.gamexo;

public class GameXOMessage {
    private GameXOPlayer player;
    private String message ;
    private int status;
    public GameXOMessage(GameXOPlayer player, String message, int status){
        this.player = player;
        this.message = message;
        this.status = status;
    }

    public int getStatus() {
        return this.status;
    }

    public GameXOPlayer getPlayer() {
        return this.player;
    }

    public String getMessage() {
        return this.message;
    }
}
