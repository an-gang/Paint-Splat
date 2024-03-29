package com.group3.paint_splat.service;

import com.group3.paint_splat.entity.Game;

import java.util.Set;

public interface Game_Service_Interface {
    Set<String> getRooms();

    String createRoom(String playerId);

    String joinRoom(String playerId, String roomId);

    String checkRoomId(String playerId);

    Game getGame(String playerId);

    long getTimeAfterStart(String id);

    void quitRoom(String playerId);

    void startGame(String playerId);

    boolean shoot(String playerId, double[] doubles);

    void printConnections();


}
