package com.example.demo.game.gameConcateWord;

public class CWRequest {
    private int type;
    private int match_id;
    private int status;
    private String message;


    public CWRequest(int type, int match_id, int status, String message) {
        this.type = type;
        this.match_id = match_id;
        this.status = status;
        this.message = message;
    }

    public int getType() {
        return this.type;
    }

    public int getMatch_id() {
        return this.match_id;
    }

    public int getStatus() {
        return this.status;
    }

    public String getMessage() {
        return this.message;
    }

    @Override
    public String toString() {
        return "{" +
            " type='" + getType() + "'" +
            ", match_id='" + getMatch_id() + "'" +
            ", status='" + getStatus() + "'" +
            ", message='" + getMessage() + "'" +
            "}";
    }

}
