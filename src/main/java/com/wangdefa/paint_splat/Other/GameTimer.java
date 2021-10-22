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
        Date currentTime = new Date();
        game.setTimeSpan(currentTime.getTime() - game.getStartTime().getTime());
    }
}
