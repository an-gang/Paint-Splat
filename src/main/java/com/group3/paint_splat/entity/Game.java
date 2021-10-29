package com.group3.paint_splat.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.*;

@JsonIgnoreProperties({"createTime", "startTime", "roomTimer", "gameTimer"})
public class Game {
    private Date createTime;
    private Date startTime;
    private long timeAfterCreate;
    private long timeAfterStart;
    private double step;
    private ArrayList<String> players;
    private ArrayList<Integer> scores;
    private ArrayList<Paint> paints;
    private ArrayList<double[]> boardPositions;
    private boolean isStart;
    //如果房间对象被销毁，两个timer也会被自动销毁，避免内存垃圾。相应逻辑写在service层quitRoom()方法里
    private Timer roomTimer;//用于实现游戏自动强制开始。生命周期：房间创建后启用，游戏开始后清除
    private Timer gameTimer;//用于实现游戏开始后的计时及变速以及游戏结束后的内存清理。生命周期：游戏开始后启用，游戏结束后清除
    private final static long timeSpan = 60000;

    //构造方法给属性设置初值并调用init()方法
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
    //生成整局游戏的随机点存进boardPositions
    private void init() {
        boardPositions.add(new double[]{0, 0});
        Random random = new Random();
        long timeUse = 0;
        while (timeUse < timeSpan * 2) {
            boardPositions.add(new double[]{random.nextInt(51), random.nextInt(51)});
            timeUse += calculateDistance(boardPositions.get(boardPositions.size() - 1), boardPositions.get(boardPositions.size() - 2)) / step;
        }
    }

    //工具方法，用于计算两点之间的值
    private double calculateDistance(double[] point1, double[] point2) {
        return Math.sqrt((point1[0] - point2[0]) * (point1[0] - point2[0]) + (point1[1] - point2[1]) * (point1[1] - point2[1]));
    }

    //当有玩家射击时，逻辑层会调用此方法遍历已有的油漆位置以判断是否射击成功
    public boolean shoot(String playerId, double[] position) {
        Iterator<Paint> iterator = paints.iterator();
        boolean isOverlapped = false;
        while (iterator.hasNext()) {
            Paint next = iterator.next();
            if (calculateDistance(next.getPosition(), position) < 12) {
                isOverlapped = true;
            }
        }
        if (isOverlapped) {
            return false;
        } else {
            paints.add(new Paint(playerId, position));
            scores.set(players.indexOf(playerId), scores.get(players.indexOf(playerId)) + 1);
            return true;
        }
    }




    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setTimeAfterCreate(long timeAfterCreate) {
        this.timeAfterCreate = timeAfterCreate;
    }

    public void setTimeAfterStart(long timeAfterStart) {
        this.timeAfterStart = timeAfterStart;
    }

    public void setStep(double step) {
        this.step = step;
    }

    public void setStart(boolean start) {
        isStart = start;
    }

    public void setRoomTimer(Timer roomTimer) {
        this.roomTimer = roomTimer;
    }

    public void setGameTimer(Timer gameTimer) {
        this.gameTimer = gameTimer;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public long getTimeAfterCreate() {
        return timeAfterCreate;
    }

    public long getTimeAfterStart() {
        return timeAfterStart;
    }

    public double getStep() {
        return step;
    }

    public ArrayList<String> getPlayers() {
        return players;
    }

    public ArrayList<Integer> getScores() {
        return scores;
    }

    public ArrayList<Paint> getPaints() {
        return paints;
    }

    public ArrayList<double[]> getBoardPositions() {
        return boardPositions;
    }

    public boolean isStart() {
        return isStart;
    }

    public Timer getRoomTimer() {
        return roomTimer;
    }

    public Timer getGameTimer() {
        return gameTimer;
    }

}
