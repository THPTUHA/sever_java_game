package com.example.demo.game.gameConcateWord;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.print.DocFlavor.READER;


public class GameCWPlaying {
    private int match_id;
    private ArrayList<PlayerCW> player = new ArrayList<>();
    private int time =30;
    private int turn = 0;
    private char word;
    private int status;
    private boolean start = true;
    private ArrayList<Answer> content = new ArrayList<>();
    private Map<String,Boolean> words = new HashMap<>();

    private final int START = 1;
    private final int PLAY = 4;
    private final int END_GAME = 7;
    private final int LOSE = 13;
    private final int CANCEL = 2;
    private final int READY = 6;
    private final int PLAY_AGAIN = 3;

    private final int CORRECT = 1;
    private final int WRONG = 0;
    private final int UNCHECK =-1;

    public GameCWPlaying(int match_id, PlayerCW player_1,PlayerCW player_2,PlayerCW player_3,PlayerCW player_4, int status) {
        this.match_id = match_id;
        this.player.add(player_1);
        this.player.add(player_2);
        this.player.add(player_3);
        this.player.add(player_4);
        this.status = status;
        for(int i=0;i<4;++i)player.get(i).setType(i);
    }

    public void downTime(){
        System.out.println("OK");
       if(this.status ==PLAY){
        this.time -=1;
        System.out.println(this.time);
       }
    }
    public boolean checkSimple(Answer answer,int type){
        String word = answer.getWord().toLowerCase();
        if(word.length()<=0||word.charAt(0)!=this.word||word.length()>=20||words.containsKey(word)){
            answer.setStatus(WRONG);
            content.add(answer);
            words.put(word, true);
            return false;
        }
        words.put(word, true);
        return true;
    }

    public boolean checkEndGame(){
        int cnt = 0;
        for(PlayerCW p:player){
            if(p.getStatus()== LOSE)cnt++;
        }
        if(cnt>=player.size()){
            this.status = END_GAME;
            for(PlayerCW p:player){
                if(p.getStatus()!=CANCEL)
                p.setStatus(END_GAME);
            }
            return true;
        }
        return false;
    }

    public void setWord(char word) {
        this.word = word;
    }

    public void initWord() {
        this.word = randomWord();
    }

    public Answer getAnswer(){
        return content.get(content.size()-1);
    }
    
    public void addAnswer(Answer ans){
        this.content.add(ans);
    }

    public void removePlayer(int type){
        this.player.remove(type);
    }
    private char randomWord(){
        Random random = new Random();
        return (char)(random.nextInt(26)+'a');
    }
    public PlayerCW pickPlayerByType(int type){
        for(PlayerCW p :player)if(p.getType() == type)return p;
        return null;
    }

    public boolean isReady(int status){
        for(PlayerCW p:player){
            if(p.getStatus()!= status &&p.getStatus()!=CANCEL)return false;
        }
        return true;
    }
    
    public int getMatch_id() {
        return this.match_id;
    }

    public ArrayList<PlayerCW> getPlayer() {
        return this.player;
    }

    public int getTime() {
        return this.time;
    }

    public int getStatus() {
        return this.status;
    }

    public int getTurn() {
        return turn;
    }

    public char getWord() {
        return word;
    }
    public void setTurn() {
        int n = player.size();
        for(int i =0; i<n;++i){
            if(player.get(i).getType()==this.turn){
                System.out.println("TYPE>>>:"+player.get(i).getType());
                for(int j = 1; j<n; ++j){
                   if(player.get((i+j)%n).getStatus()==PLAY||player.get((i+j)%n).getStatus()==READY||player.get((i+j)%n).getStatus()==START){
                        this.turn = player.get((i+j)%n).getType();
                        System.out.println("TURN "+this.turn);
                        return;
                   }
                }
            }
        }
    }
    public void setStatus(int status) {
        for(PlayerCW p:player)
        {
            if(p.getStatus()!=CANCEL)p.setStatus(status);
        }
        this.status = status;
    }

    public void setStatusGeneral(int status) {
        this.status = status;
    }
    public void setUnCheck() {
        for(PlayerCW p:player){
            if(p.getStatus()==PLAY){
                System.out.print("DAngchoi "+p.getName());
                p.setStatus(UNCHECK);
            }
        }
        this.status = UNCHECK;
    }

    public void setPlay() {
        for(PlayerCW p:player){
            if(p.getStatus()!=LOSE && p.getStatus()!=CANCEL){
                System.out.println(p.getName()+"//"+p.getStatus());
                p.setStatus(PLAY);
            }
        }
        this.status = PLAY;
    }

    public boolean isCancel(){
        System.out.println("Cancel.....");
        for(PlayerCW p:player)if(p.getStatus()!=CANCEL)return false;
        return true;
    }

    public boolean isPlayAgain(){
        System.out.println("Again.....");
        for(PlayerCW p:player)if(p.getStatus()!=PLAY_AGAIN&&p.getStatus()!=CANCEL)return false;
        return true;
    }
    public void reset(){
        for(PlayerCW p:this.player){
            if(p.getStatus() == START) this.turn = p.getType(); 
        }
        this.content.clear();
        this.words.clear();
    }
}
