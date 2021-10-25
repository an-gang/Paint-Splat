package com.group3.paint_splat.service;


import com.group3.paint_splat.entity.Game;
import com.group3.paint_splat.entity.Paint;
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

            Timer timer = new Timer();
            TimerTask roomTimer = new TimerTask() {
                @Override
                public void run() {
                    Date currentTime = new Date();
                    game.setTimeAfterCreate(currentTime.getTime() - game.getCreateTime().getTime());
                    if (game.isStart()) {
                        timer.cancel();
                        System.out.println("manual cancel");
                    } else if (game.getTimeAfterCreate() / 1000 >= 60) {
                        startGame(playerId);
                        timer.cancel();
                        System.out.println("auto cancel");
                    }
                }
            };
            timer.schedule(roomTimer, 0, 1000);

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
    public Game getGame(String playerId) {
        return rooms.get(checkRoomId(playerId));
    }

    @Override
    public long getTime(String playerId) {
        return rooms.get(checkRoomId(playerId)).getTimeAfterStart();
    }

    @Override
    public void quitRoom(String playerId) {
        String roomId = checkRoomId(playerId);
        Game game = rooms.get(roomId);
        if (game != null) {
            Iterator<String> iterator = game.getPlayers().iterator();
            while (iterator.hasNext()) {
                if (iterator.next().equals(playerId)) {
                    iterator.remove();
                    break;
                }
            }
            if (game.getPlayers().size() == 0) {
                rooms.remove(roomId);
            }
        }
    }

    @Override
    public void startGame(String playerId) {
        String roomId = checkRoomId(playerId);
        Game game = rooms.get(roomId);
        if (!game.isStart()) {
            game.setStart(true);
            game.setStartTime(new Date());
            Timer timer = new Timer();
            TimerTask gameTimer = new TimerTask() {
                @Override
                public void run() {
                    Date currentTime = new Date();
                    game.setTimeAfterStart(currentTime.getTime() - game.getStartTime().getTime() - 3000);
                    if (game.getTimeAfterStart() / 1000 > 60) {
                        timer.cancel();
                        rooms.remove(roomId);
                    } else if (game.getTimeAfterStart() / 1000 > 40) {
                        game.setStep(0.03);
                    } else if (game.getTimeAfterStart() / 1000 > 20) {
                        game.setStep(0.02);
                    }
                }
            };
            timer.schedule(gameTimer, 3000, 1000);
        }
    }

    @Override
    public boolean shoot(String playerId, double[] position) {
        Game game = rooms.get(checkRoomId(playerId));
        game.getPaints().add(new Paint(playerId, position));
        return true;
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

    private static String generateID() {
        Random random = new Random();
        String id = Integer.toString(random.nextInt(1000000));
        while (id.length() < 6) {
            id = "0" + id;
        }
        return id;
    }
}
