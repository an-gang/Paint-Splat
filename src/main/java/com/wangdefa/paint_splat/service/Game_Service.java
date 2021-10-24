package com.wangdefa.paint_splat.service;


import com.wangdefa.paint_splat.Other.GameTimer;
import com.wangdefa.paint_splat.Other.ID_generator;
import com.wangdefa.paint_splat.entity.Game;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class Game_Service implements Game_Service_Interface {
    private static HashMap<String, Game> rooms = new HashMap<>();


    @Override
    public Set<String> getRooms() {
        return rooms.keySet();
    }

    @Override
    public String createRoom(String playerId) {
        String existedRoomId = checkRoomId(playerId);
        if (existedRoomId == null) {
            boolean isExist;
            String newId;
            //判断房价号不可重复
            do {
                isExist = false;
                newId = ID_generator.generateID();
                for (String roomId : rooms.keySet()) {
                    if (roomId.equals(newId)) {
                        isExist = true;
                    }
                }
            } while (isExist);
            Game game = new Game();
            rooms.put(newId, game);
            game.getPlayers().add(playerId);
            game.getScores().add(0);
            return newId;
        } else {
            return existedRoomId;
        }
    }

    @Override
    public String joinRoom(String playerId, String roomId) {
        String existedRoomId = checkRoomId(playerId);
        if (existedRoomId == null) {
            Game game = rooms.get(roomId);
            if (game.getPlayers().size() < 4) {
                game.getPlayers().add(playerId);
                game.getScores().add(0);
                startTiming(game);
                return "success";
            } else {
                return "full";
            }
        } else {
            return existedRoomId;
        }
    }

    @Override
    public String checkRoomId(String playerId) {
        String roomId = null;
        Iterator<Map.Entry<String, Game>> iterator = rooms.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Game> entry = iterator.next();
            for (String player : entry.getValue().getPlayers()) {
                if (playerId.equals(player)) {
                    roomId = entry.getKey();
                    break;
                }
            }
        }
        return roomId;
    }

    @Override
    public Game getGame(String roomId) {
        return rooms.get(roomId);
    }

    @Override
    public long getTime(String roomId) {
        return rooms.get(roomId).getTime();
    }

    @Override
    public void quitRoom(String playerId, String roomId) {
        Iterator<String> iterator = rooms.get(roomId).getPlayers().iterator();
        while (iterator.hasNext()){
            if(iterator.next().equals(playerId)){
                iterator.remove();
                break;
            }
        }
    }

    @Override
    public int countPlayer(String roomId) {
        return rooms.get(roomId).getPlayers().size();
    }

    @Override
    public void printConnections() {
        Iterator<Map.Entry<String, Game>> iterator = rooms.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Game> entry = iterator.next();
            for (String player : entry.getValue().getPlayers()) {
                System.out.println(entry.getKey() + "----" + player);
            }
        }
    }

    private void startTiming(Game game) {
        game.setStartTime(new Date());
        Timer timer = new Timer();
        GameTimer gameTimer = new GameTimer();
        gameTimer.setGame(game);
        timer.schedule(gameTimer, 3, 1000);
    }
}
