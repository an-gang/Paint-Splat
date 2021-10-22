package com.wangdefa.paint_splat.service;


import com.wangdefa.paint_splat.Other.GameTimer;
import com.wangdefa.paint_splat.entity.Game;
import org.springframework.stereotype.Service;

import java.util.Timer;
import java.text.DateFormat;

import java.text.SimpleDateFormat;

import java.util.Date;

@Service
public class Game_Service implements Game_Service_Interface {
    @Override
    public Game test() {

        System.out.println("service");


        return null;
    }

    //tool function---------------------------------------------------------------------------------
    public double CalculateDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    public void startTime(Game game) {
        Timer timer = new Timer();
        GameTimer gameTimer = new GameTimer();
        gameTimer.setGame(game);
        timer.schedule(gameTimer, 0, 1000);
    }


}
