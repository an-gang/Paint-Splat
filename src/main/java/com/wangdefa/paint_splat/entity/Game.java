package com.wangdefa.paint_splat.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Game {
    private Date startTime;
    private int step;
    private ArrayList<String> players;
    private ArrayList<Integer> scores;
    private ArrayList<Paint> paints;
    private ArrayList<double[]> boardPositions;
    private long time = 0;
    private final static long timeSpan = 600000;
    private final static int animationSpeed = 50;


    public Game() {
        step = 1;
        players = new ArrayList<>();
        paints = new ArrayList<>();
        boardPositions = new ArrayList<>();

        boardPositions.add(new double[]{0, 0});
        Random random = new Random();
        long timeUse = 0;
        while (timeUse < timeSpan * 1.5) {
            boardPositions.add(new double[]{random.nextInt(51), random.nextInt(51)});
            timeUse += calculateDistance(boardPositions.get(boardPositions.size() - 1), boardPositions.get(boardPositions.size() - 2)) / step * animationSpeed;
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

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
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

    public ArrayList<String> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<String> players) {
        this.players = players;
    }
}
