package com.example.demo.gamexo;

import com.example.demo.model.GameXO;
import com.example.demo.model.User;

public class GameXOPlaying {
    private GameXO game;
    private int id_match;
    private GameXOPlayer player1;
    private GameXOPlayer player2;
    private int turn = 1;
    private int status = 0;
    private int number_user_play_again=0;

    public GameXOPlaying(int id_match, User user1, User user2, int status) {
        this.game = new GameXO();
        this.id_match = id_match;
        this.player1 = new GameXOPlayer(user1, 1);
        this.player2 = new GameXOPlayer(user2, 2);
        this.status = status;
    }

    public int getIdmatch() {
        return this.id_match;
    }

    public GameXOPlayer getPlayer(int type) {
        if (type == 1)
            return this.player1;
        return this.player2;
    }

    public GameXOPlayer getPlayer1() {
        return this.player1;
    }

    public GameXOPlayer getPlayer2() {
        return this.player2;
    }

    public int[][] getBoard() {
        return this.game.getBoard();
    }

    public void setBoard() {
        this.game.resetBoard();
    }

    public int getTurn() {
        return this.turn;
    }

    public void play(int x, int y) {
        game.setBoard(x, y, this.turn);
    }

    public void setTurn() {
        this.turn = 3 - this.turn;
    }

    public void setStatus(int status) {
        this.status=status;
    }

    public int getStatus() {
       return this.status;
    }

    public int getNumber_user_play_again() {
        return this.number_user_play_again;
     }
    
    public void setNumber_user_play_again(int n) {
        this.number_user_play_again += n;
     }

    public GameXOPlayer getPlayerById(int id){
        if(this.player1.getId()==id)return this.player1;
        if(this.player2.getId()==id)return this.player2;
        return null;
    }
    private boolean checkWinner(int[][] board, int size, int n, int type) {
        int cnt = 0, i = 0, j = 0;
        for (i = 0; i < size; ++i) {
            cnt = 0;
            for (j = 0; j < size; ++j) {
                if (board[i][j] == type)
                    cnt++;
                else
                    cnt = 0;
                if (cnt >= n)
                    return true;
            }
        }
        for (i = 0; i < size; ++i) {
            cnt = 0;
            for (j = 0; j < size; ++j) {
                if (board[j][i] == type)
                    cnt++;
                else
                    cnt = 0;
                if (cnt >= n)
                    return true;
            }
        }
        int cross = 0;
        for (i = 1 - size; i < size; ++i) {
            cross = 0;
            for (j = 0; j < size; ++j) {
                if (j + i >= 0 && j + i < size && board[j + i][j] == type)
                    cross++;
                else
                    cross = 0;
                if (cross >= n)
                    return true;
            }
        }

        for (i = 0; i < 2 * size - 1; ++i) {
            cross = 0;
            for (j = 0; j < size; ++j) {
                if (i - j >= 0 && i - j < size && board[i - j][j] == type)
                    cross++;
                else
                    cross = 0;
                if (cross >= n)
                    return true;
            }
        }
        return false;
    }

    private boolean checkDraw(int[][] board, int size) {
        int i = 0, j = 0;
        for (i = 0; i < size; ++i) {
            for (j = 0; j < size; ++j) {
                if (board[i][j] == 0)
                    return false;
            }
        }
        return true;
    }

    public int winner() {
        int[][] board = game.getBoard();
        int size = game.getSize_board();
        int n = game.getCondition_win();
        if (checkWinner(board, size, n, 1))
            return 1;
        if (checkWinner(board, size, n, 2))
            return 2;
        if (checkDraw(board, size))
            return -1;
        return 0;
    }

    public void undateAfterGame(int winner){
        if(winner==1){
            player1.setExp(1);player1.setGold(1);
            player2.setExp(-1);player2.setGold(-1);
        }else if(winner==2){
            player1.setExp(-1);player1.setGold(-1);
            player2.setExp(1);player2.setGold(1);
        }
    }

    public void randomPlay(int type){
        int[][] board = game.getBoard();
        int size = game.getSize_board();
        int  i = 0, j = 0;
        for (i = 0; i < size; ++i) {
            for (j = 0; j < size; ++j) {
                if(board[i][j]==0){
                    game.setBoard(i, j, type);
                    return;
                }
            }
        }
    }
    @Override
    public String toString() {
        return game.toString() + "\nturn:" + this.turn;
    }
}
