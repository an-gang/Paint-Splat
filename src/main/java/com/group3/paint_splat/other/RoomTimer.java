package com.group3.paint_splat.other;

import com.group3.paint_splat.entity.Game;

import java.util.Date;
import java.util.TimerTask;

public class RoomTimer extends TimerTask {
    private Game game;

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        Date currentTime = new Date();
        game.setTimeAfterCreate(currentTime.getTime() - game.getCreateTime().getTime());
        if (game.getTimeAfterCreate() / 1000 >= 60) {
            GameTimer.startGame(game);
        }
    }
}
