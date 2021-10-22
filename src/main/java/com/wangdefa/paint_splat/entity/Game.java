package com.wangdefa.paint_splat.entity;

import java.util.Date;
import java.util.HashMap;

public class Game {
    private String id;
    private Date startTime;
    private long timeSpan;
    private int step;
    private String[] players;
    private HashMap<Integer, double[]> boardPositions;
    private HashMap<String, Paint> paints;

    Game() {
        init();
    }

    private void init() {
        players = new String[4];
        boardPositions = new HashMap<>();
        timeSpan = 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public long getTimeSpan() {
        return timeSpan;
    }

    public void setTimeSpan(long timeSpan) {
        this.timeSpan = timeSpan;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public String[] getPlayers() {
        return players;
    }

    public void setPlayers(String[] players) {
        this.players = players;
    }

    public HashMap<Integer, double[]> getBoardPositions() {
        return boardPositions;
    }

    public void setBoardPositions(HashMap<Integer, double[]> boardPositions) {
        this.boardPositions = boardPositions;
    }

    public HashMap<String, Paint> getPaints() {
        return paints;
    }

    public void setPaints(HashMap<String, Paint> paints) {
        this.paints = paints;
    }
}
