package com.wangdefa.paint_splat.Other;

import com.wangdefa.paint_splat.entity.Game;

import java.util.Date;
import java.util.TimerTask;

public class GameTimer extends TimerTask {
    private Game game;

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        game.getStartTime();

        Date cur = new Date();




        game.setTimeSpan(4500);

    }
}
