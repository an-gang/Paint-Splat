package com.group3.paint_splat.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Game {
    private Date startTime;
    private double step;
    private ArrayList<String> players;
    private ArrayList<Integer> scores;
    private ArrayList<Paint> paints;
    private ArrayList<double[]> boardPositions;
    private long time = 0;
    private final static long timeSpan = 600000;


    public Game() {
        step = 0.01;
        players = new ArrayList<>();
        scores = new ArrayList<>();
        paints = new ArrayList<>();
        boardPositions = new ArrayList<>();
        init();

    }

    private void init() {
        boardPositions.add(new double[]{0, 0});
        Random random = new Random();
        long timeUse = 0;
        while (timeUse < timeSpan) {
            boardPositions.add(new double[]{random.nextInt(51), random.nextInt(51)});
            timeUse += calculateDistance(boardPositions.get(boardPositions.size() - 1), boardPositions.get(boardPositions.size() - 2)) / step;
        }
    }

    private double calculateDistance(double[] point1, double[] point2) {
//        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
        return Math.sqrt((point1[0] - point2[0]) * (point1[0] - point2[0]) + (point1[1] - point2[1]) * (point1[1] - point2[1]));
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public static long getTimeSpan() {
        return timeSpan;
    }
}
