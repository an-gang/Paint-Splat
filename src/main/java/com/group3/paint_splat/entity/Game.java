package com.group3.paint_splat.entity;

import com.group3.paint_splat.other.RoomTimer;

import java.util.*;

public class Game {
    private Date createTime;
    private long timeAfterCreate;
    private Date startTime;
    private long timeAfterStart;
    private double step;
    private ArrayList<String> players;
    private ArrayList<Integer> scores;
    private ArrayList<Paint> paints;
    private ArrayList<double[]> boardPositions;
    private boolean isStart;
    private final static long timeSpan = 60000;

    public Game() {
        createTime = new Date();
        timeAfterCreate = 0;
        step = 0.01;
        players = new ArrayList<>();
        scores = new ArrayList<>();
        paints = new ArrayList<>();
        boardPositions = new ArrayList<>();
        timeAfterStart = 0;
        isStart = false;
        init();
    }

    private void init() {
        boardPositions.add(new double[]{0, 0});
        Random random = new Random();
        long timeUse = 0;
        while (timeUse < timeSpan * 2) {
            boardPositions.add(new double[]{random.nextInt(51), random.nextInt(51)});
            timeUse += calculateDistance(boardPositions.get(boardPositions.size() - 1), boardPositions.get(boardPositions.size() - 2)) / step;
        }
        Timer timer = new Timer();
        RoomTimer roomTimer = new RoomTimer();
        roomTimer.setGame(this);
        timer.schedule(roomTimer, 0, 1000);
    }

    private double calculateDistance(double[] point1, double[] point2) {
        return Math.sqrt((point1[0] - point2[0]) * (point1[0] - point2[0]) + (point1[1] - point2[1]) * (point1[1] - point2[1]));
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public long getTimeAfterCreate() {
        return timeAfterCreate;
    }

    public void setTimeAfterCreate(long timeAfterCreate) {
        this.timeAfterCreate = timeAfterCreate;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public long getTimeAfterStart() {
        return timeAfterStart;
    }

    public void setTimeAfterStart(long timeAfterStart) {
        this.timeAfterStart = timeAfterStart;
    }

    public double getStep() {
        return step;
    }

    public void setStep(double step) {
        this.step = step;
    }

    public ArrayList<String> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<String> players) {
        this.players = players;
    }

    public ArrayList<Integer> getScores() {
        return scores;
    }

    public void setScores(ArrayList<Integer> scores) {
        this.scores = scores;
    }

    public ArrayList<Paint> getPaints() {
        return paints;
    }

    public void setPaints(ArrayList<Paint> paints) {
        this.paints = paints;
    }

    public ArrayList<double[]> getBoardPositions() {
        return boardPositions;
    }

    public void setBoardPositions(ArrayList<double[]> boardPositions) {
        this.boardPositions = boardPositions;
    }

    public boolean isStart() {
        return isStart;
    }

    public void setStart(boolean start) {
        isStart = start;
    }

    public static long getTimeSpan() {
        return timeSpan;
    }
}
