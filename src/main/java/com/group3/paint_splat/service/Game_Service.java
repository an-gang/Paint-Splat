package com.group3.paint_splat.service;


import com.group3.paint_splat.entity.Game;
import com.group3.paint_splat.entity.Paint;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class Game_Service implements Game_Service_Interface {
    private static HashMap<String, Game> rooms = new HashMap<>();

    //返回房间号列表
    @Override
    public Set<String> getRooms() {
        return rooms.keySet();
    }

    //创建房间，即新建game对象和id。并且，启动附加的roomTimer作为开始计时器，创建房间60秒后强制开始避免内存垃圾
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

    //把playerId放进要加入的房间，已在房间中则返回房间号，房间已满则返回"full"
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

    //通过playerId查找玩家在哪个房间里，返回房间号（即RoomId）
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

    //返回游戏房间的所有后台书记，供前台用轮询的方式实现状态同步
    @Override
    public Game getGame(String playerId) {
        return rooms.get(checkRoomId(playerId));
    }

    //返回游戏开始后的毫秒数
    @Override
    public long getTimeAfterStart(String playerId) {
        return rooms.get(checkRoomId(playerId)).getTimeAfterStart();
    }

    //当用户主动退出游戏时执行，移除玩家并清理内存
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

    //设置游戏状态为开始，并启用gameTimer用于实现游戏开始后的计时及变速以及游戏结束后的内存清理
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

    //调用Game实体类的shoot方法判断并返回是否射击成功
    @Override
    public boolean shoot(String playerId, double[] position) {
        Game game = rooms.get(checkRoomId(playerId));
        return game.shoot(playerId, position);
    }

    //仅用于测试，可删。当被调用的时候会打印所有connections between roomId and game object
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

    //工具方法，生成6位房间号
    private static String generateID() {
        Random random = new Random();
        String id = Integer.toString(random.nextInt(1000000));
        while (id.length() < 6) {
            id = "0" + id;
        }
        return id;
    }
}
