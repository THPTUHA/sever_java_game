package com.example.demo.gamexo;

public class GameXORequest {
    private int id_user;
    private int id_match;
    private int coordinateX;
    private int coordinateY;
    private int type;
    public int getId_match(){return this.id_match;}
    public int getType(){return this.type;}
    public int getCoordinateX(){return this.coordinateX;}
    public int getCoordinateY(){return this.coordinateY;}
    public int getId_user(){return this.id_user;}
    @Override 
    public String toString(){
        return "id_user:"+id_user+"\nid_match:"+id_match+"\nX:"+coordinateX+"\nY:"+coordinateY+"\ntype:"+type;
    }
}
