package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.demo.gamexo.GameXOPlaying;


import org.springframework.stereotype.Service;

@Service
public class GameXOService {
   private List<GameXOPlaying>wait=new ArrayList<>();
   private Map<Integer,GameXOPlaying>playing=new HashMap<>();

   public boolean isWating(){
       return wait.size()!=0;
   }
   public void creatGame(int id_match,int id_user1){
        wait.add(new GameXOPlaying(id_match,id_user1));
   }
   public int connectGame(int id_user2){
       GameXOPlaying tmp=wait.get(0);
        tmp.addUser2(id_user2);
        playing.put(tmp.getIdmatch(),tmp);
        wait=new ArrayList<>();
        return tmp.getIdmatch();
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
       if(checkWinner(board, size, n, 1))return 1;
       if(checkWinner(board, size, n, 2))return 2;
       if(checkDraw(board, size))return -1;
       return 0;
   }
   public void printWait(){
       for(GameXOPlaying g:wait){
           System.out.println(g);
       }
   }
   public void printPlaying(){
    for(Map.Entry<Integer,GameXOPlaying> g:playing.entrySet()){
        System.out.println(g);
    }
}
}
