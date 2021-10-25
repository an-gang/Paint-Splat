package com.group3.paint_splat.other;

import com.group3.paint_splat.entity.Game;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class GameTimer extends TimerTask {
    private Game game;

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        Date currentTime = new Date();
        game.setTimeAfterStart(currentTime.getTime() - game.getStartTime().getTime()-3000);
    }
    public static void startGame(Game game){
        if (!game.isStart()) {
            game.setStart(true);
            game.setStartTime(new Date());
            Timer timer = new Timer();
            GameTimer gameTimer = new GameTimer();
            gameTimer.setGame(game);
            timer.schedule(gameTimer, 3000, 1000);
        }
    }
}
