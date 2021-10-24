package com.wangdefa.paint_splat.service;

import com.wangdefa.paint_splat.entity.Game;

import java.util.HashMap;
import java.util.Set;

public interface Game_Service_Interface {
    Set<String> getRooms();

    String createRoom(String playerId);

    String joinRoom(String playerId, String roomId);

    String checkRoomId(String playerId);

    Game getGame(String roomId);

    long getTime(String roomId);

    void quitRoom(String playerId, String roomId);

    int countPlayer(String roomId);


    void printConnections();
}
