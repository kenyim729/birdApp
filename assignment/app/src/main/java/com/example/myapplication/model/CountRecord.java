package com.example.myapplication.model;

import java.util.Date;

public class CountRecord {

    private int game;
    private int round;
    private int player1;
    private int player2;
    private int player3;
    private int player4;
    private String createUser;
    private String createTime;

    public CountRecord(int game, int round, int player1, int player2, int player3, int player4, String createUser, String createTime) {
        this.game = game;
        this.round = round;
        this.player1 = player1;
        this.player2 = player2;
        this.player3 = player3;
        this.player4 = player4;
        this.createUser = createUser;
        this.createTime = createTime;
    }

    public int getGame() {
        return game;
    }

    public void setGame(int game) {
        this.game = game;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public int getPlayer1() {
        return player1;
    }

    public void setPlayer1(int player1) {
        this.player1 = player1;
    }

    public int getPlayer2() {
        return player2;
    }

    public void setPlayer2(int player2) {
        this.player2 = player2;
    }

    public int getPlayer3() {
        return player3;
    }

    public void setPlayer3(int player3) {
        this.player3 = player3;
    }

    public int getPlayer4() {
        return player4;
    }

    public void setPlayer4(int player4) {
        this.player4 = player4;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Record{" +
                "game=" + game  +
                "round=" + round +
                ", player1=" + player1 +
                ", player2=" + player2 +
                ", player3=" + player3 +
                ", player4=" + player4 +
                ", createUser='" + createUser + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
