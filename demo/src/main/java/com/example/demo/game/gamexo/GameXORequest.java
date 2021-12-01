package com.example.demo.game.gamexo;

public class GameXORequest {
    private int user_id;
    private int match_id;
    private int coordinateX;
    private int coordinateY;
    private int type;
    private int status=0;
    private String user_name;
    private String message="";
    private boolean random;

    public int getMatch_id() {
        return match_id;
    }
    public int getType(){return this.type;}
    public int getCoordinateX(){return this.coordinateX;}
    public int getCoordinateY(){return this.coordinateY;}
    public int getUser_id() {
        return user_id;
    }
    public int getStatus(){return this.status;}
    public String getUser_name(){return this.user_name;}
    public String getMessage(){return this.message;}
    public boolean getRandom(){return this.random;}
    @Override 
    public String toString(){
        return "user_id:"+user_id+"\nmatch_id:"+match_id+"\nX:"+coordinateX+"\nY:"+coordinateY+"\ntype:"+type
        +"\nmessage:"+this.message+"\nstatus: "+ this.status+"\nrandom:"+this.random;
    }
}
