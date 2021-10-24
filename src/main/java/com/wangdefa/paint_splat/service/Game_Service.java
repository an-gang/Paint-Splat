package com.wangdefa.paint_splat.service;


import com.wangdefa.paint_splat.Other.GameTimer;
import com.wangdefa.paint_splat.Other.ID_generator;
import com.wangdefa.paint_splat.entity.Game;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;

@Service
public class Game_Service implements Game_Service_Interface {
    private static HashMap<String, Integer> players = new HashMap<>();
    private static ArrayList<Game> rooms = new ArrayList<>();

    public Game_Service() {
        boolean isExist;
        do {
            isExist = false;
            String id = ID_generator.generateID();
            for (Game room : rooms) {
                if (room.getId().equals(id)) {
                    isExist = true;
                }
            }
            if (!isExist) {
                rooms.add(new Game(id));
            }
        } while (isExist);
    }

    @Override
    public Game service() {

        System.out.println("service");


        return null;
    }

    private void startTime(Game game) {
        Timer timer = new Timer();
        GameTimer gameTimer = new GameTimer();
        gameTimer.setGame(game);
        timer.schedule(gameTimer, 0, 1000);
    }


}
