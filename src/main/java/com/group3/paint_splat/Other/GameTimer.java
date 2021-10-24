package com.group3.paint_splat.Other;

import com.group3.paint_splat.entity.Game;
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
        game.setTime(currentTime.getTime() - game.getStartTime().getTime());
    }
}
