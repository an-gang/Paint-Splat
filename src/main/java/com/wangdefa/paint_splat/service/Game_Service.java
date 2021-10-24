package com.wangdefa.paint_splat.service;


import com.wangdefa.paint_splat.Other.GameTimer;
import com.wangdefa.paint_splat.entity.Game;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;

@Service
public class Game_Service implements Game_Service_Interface {
//    private static HashMap<String, Integer> players = new HashMap<>();
//    private static ArrayList<Game> rooms = new ArrayList<>();

    private static Game testGame = new Game();

    @Override
    public Game service() {

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
