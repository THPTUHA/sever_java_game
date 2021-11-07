package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.demo.gamexo.GameXOPlaying;
import com.example.demo.gamexo.GameXORequest;
import com.example.demo.gamexo.GameXORes;
import com.example.demo.model.GamePlay;
import com.example.demo.repository.GamePlayReposity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameXOService {
    @Autowired
   private GamePlayReposity gamePlayReposity;
   
   private final int IdGameXO=1;
   private final int TYPE_1=1;
   private final int TYPE_2=2;
   private Map<Integer,GameXOPlaying>playing=new HashMap<>();

   private GamePlay getMatch(){
       return gamePlayReposity.getOneGamePlay();
   }

   private int creatGame(int id_user1){
       gamePlayReposity.save(new GamePlay(IdGameXO,id_user1));
       return gamePlayReposity.getOneMatch(id_user1).getId();
   }

   public GameXORes connectGame(GameXORequest gameXORequest){
       GamePlay macth=getMatch();
       final int id_user=gameXORequest.getId_user();
       if(macth==null){
           return new GameXORes(creatGame(gameXORequest.getId_user()), TYPE_1);
       }
       final int id_match=macth.getId();
       playing.put(id_match,new GameXOPlaying(id_match, macth.getStatus(), id_user));
       macth.setPlayer(id_user);
       gamePlayReposity.updatePlayGame(id_match, macth.getPlayer(), -1);
       return new GameXORes(macth.getId(), TYPE_2);
   }
   
   public GameXOPlaying matchPlaying(int id_match){
        return playing.get(id_match);
   }
   private boolean checkWinner(int[][] board,int size,int n,int type ){
       int cnt=0,i=0,j=0;
        for(i=0;i<size;++i)
         {
            cnt=0;
            for( j=0;j<size;++j){
                if(board[i][j]==type)cnt++;else cnt=0;
                if(cnt>=n)return true;
            }
         }
        for( i=0;i<size;++i)
         {
            cnt=0;
            for( j=0;j<size;++j){
                if(board[j][i]==type)cnt++;else cnt=0;
                if(cnt>=n)return true;
            }
         }
         int cross1=0,cross2=0;
         for( i=0;i<size;++i)
          {
            cross1=cross2=0;
            for( j=0;j<size;++j){
                if(j>=i&&board[j][j-i]==type)cross1++;else cross1=0;
                if(j>=i&&board[j-i][j]==type)cross2++;else cross2=0;
                if(cross1>=n||cross2>=n)return true;
            }
          }
        return false;
   }

   private boolean checkDraw(int[][] board,int size){
        int i=0,j=0;
        for(i=0;i<size;++i)
        {
            for( j=0;j<size;++j){
                if(board[i][j]==0)return false;
            }
        }
        return true;
   }
   public int winner(GameXOPlaying gameXOPlaying){
       int[][]board=gameXOPlaying.getBoard();
       int size=gameXOPlaying.getSizeBoard();
       int n=gameXOPlaying.getConditionWin();
       if(checkWinner(board, size, n, 1)){
           
       }
       if(checkWinner(board, size, n, 2))return 2;
       if(checkDraw(board, size))return -1;
       return 0;
   }
//    public void printWait(){
//        for(GameXOPlaying g:wait){
//            System.out.println(g);
//        }
//    }
   public void printPlaying(){
    for(Map.Entry<Integer,GameXOPlaying> g:playing.entrySet()){
        System.out.println(g);
    }
}
}
