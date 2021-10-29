package com.group3.paint_splat.service;


import com.group3.paint_splat.entity.Game;
import com.group3.paint_splat.entity.Paint;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class Game_Service implements Game_Service_Interface {
    private static HashMap<String, Game> rooms = new HashMap<>();

    // return roomID list
    @Override
    public Set<String> getRooms() {
        return rooms.keySet();
    }

    // Create Room: create a new game object and id. and, start the additional roomTimer as a start timer to force the room to start 60 seconds after creation to avoid memory garbage
    @Override
    public String createRoom(String playerId) {
        String existedRoomId = checkRoomId(playerId);
        if (existedRoomId == null) {
            boolean isExist;
            String newId;
            //判断房价号不可重复
            do {
                isExist = false;
                newId = generateID();
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

            game.setRoomTimer(new Timer());
            Timer roomTimer = game.getRoomTimer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    Date currentTime = new Date();
                    game.setTimeAfterCreate(currentTime.getTime() - game.getCreateTime().getTime());
                    if (game.isStart()) {
                        roomTimer.cancel();
                    } else if (game.getTimeAfterCreate() / 1000 >= 60) {
                        startGame(playerId);
                        roomTimer.cancel();
                    }
                }
            };
            roomTimer.schedule(timerTask, 0, 1000);

            return newId;
        } else {
            return existedRoomId;
        }
    }

    //Put the playerID into the room the player wants to join, and return the room number if the room is already in, or return "full" if the room is full
    @Override
    public String joinRoom(String playerId, String roomId) {
        String existedRoomId = checkRoomId(playerId);
        if (existedRoomId == null) {
            Game game = rooms.get(roomId);
            if (game.getPlayers().size() < 4) {
                game.getPlayers().add(playerId);
                game.getScores().add(0);
                return "success";
            } else {
                return "full";
            }
        } else {
            return existedRoomId;
        }
    }

    //Find which room the player is in by playerId and return the room number (RoomId)
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

    //Return all the backend clerks of the game room for the frontend to synchronize the status by interval
    @Override
    public Game getGame(String playerId) {
        return rooms.get(checkRoomId(playerId));
    }

    //Returns the number of milliseconds since the start of the game
    @Override
    public long getTimeAfterStart(String playerId) {
        return rooms.get(checkRoomId(playerId)).getTimeAfterStart();
    }

    //Executes when the user actively quits the game, removes the player and clears memory
    @Override
    public void quitRoom(String playerId) {
        String roomId = checkRoomId(playerId);
        Game game = rooms.get(roomId);
        if (game != null) {
            Iterator<String> iterator = game.getPlayers().iterator();
            while (iterator.hasNext()) {
                int index = iterator.next().indexOf(playerId);
                if (index != -1) {
                    //移除此玩家射击的油漆
                    game.getPaints().removeIf(next -> next.getPlayer().equals(playerId));
                    //移除此玩家分数
                    game.getScores().remove(index);
                    //移除此玩家
                    iterator.remove();
                    break;
                }
            }
            //判断房间是否为空，空则清理此房间对象
            if (game.getPlayers().size() == 0) {
                if (game.getRoomTimer() != null) {
                    game.getRoomTimer().cancel();
                }
                if (game.getGameTimer() != null) {
                    game.getGameTimer().cancel();
                }
                rooms.remove(roomId);
            }
        }
    }

    //Set the game state to start, and enable gameTimer for timing and speed change after the game starts and memory cleanup after the game ends
    @Override
    public void startGame(String playerId) {
        String roomId = checkRoomId(playerId);
        Game game = rooms.get(roomId);
        if (!game.isStart()) {
            game.setStart(true);
            game.setStartTime(new Date());
            game.setGameTimer(new Timer());
            Timer gameTimer = game.getGameTimer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    Date currentTime = new Date();
                    game.setTimeAfterStart(currentTime.getTime() - game.getStartTime().getTime() - 3000);
                    if (game.getTimeAfterStart() / 1000 > 60) {
                        gameTimer.cancel();
                        rooms.remove(roomId);
                    } else if (game.getTimeAfterStart() / 1000 > 40) {
                        game.setStep(0.03);
                    } else if (game.getTimeAfterStart() / 1000 > 20) {
                        game.setStep(0.02);
                    }
                }
            };
            gameTimer.schedule(timerTask, 3000, 1000);
        }
    }

    //Call the shoot method of the Game entity class to determine and return whether the shot was successful or not
    @Override
    public boolean shoot(String playerId, double[] position) {
        Game game = rooms.get(checkRoomId(playerId));
        return game.shoot(playerId, position);
    }

    //Used for testing only, can be deleted. When called it prints all connections between roomId and game object
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

    //tool methods, generate 6 bits roomID
    private static String generateID() {
        Random random = new Random();
        String id = Integer.toString(random.nextInt(1000000));
        while (id.length() < 6) {
            id = "0" + id;
        }
        return id;
    }
}
